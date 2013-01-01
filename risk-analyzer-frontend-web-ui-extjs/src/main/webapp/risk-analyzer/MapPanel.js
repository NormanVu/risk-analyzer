Ext.define('RiskAnalyzer.MapPanel', {
  extend: 'Ext.panel.Panel',

  alias: 'widget.mappanel',

  initComponent: function() {
    this.addEvents(
      /**
       * @event nodecreated
       * @param {RiskAnalyzer.MapPanel} this
       * @param {int} nodeId
       */
      'nodeclick',
      'edgeclick'
    );

    this.callParent(arguments);
  },

  afterRender : function(){
	  try {
    this.callParent();
    var centerLatLng = new google.maps.LatLng(47.4984056, 19.0407578);

    var mapOptions = {
      zoom: 5,
      center: centerLatLng,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      draggableCursor: 'crosshair'
    };

    this.map = new google.maps.Map(this.body.dom, mapOptions);

    google.maps.event.addListener(this.map, 'click', function(event) {
      Ext.MessageBox.show({
        title: 'Risk Analyzer Team',
        msg: 'In the next release the New Node window will be poped up with prefilled location ' + event.latLng + '.',
        buttons: Ext.MessageBox.OK,
        icon: Ext.MessageBox.INFO
      });
    });

    // Array of google.maps.Marker objects
    this.markerArray = new Array();
    // Array of google.maps.Polyline objects
    this.polylineArray = new Array();

    this.update();
	  } catch (error) {
		alert('Error : ' + error);  
	  }
  },

  update: function() {
    Ext.Ajax.request({
      url: 'service/network/map',
      scope: this,
      success: function(response) {
        this.deleteEdges();
        this.deleteMarkers();
        var text = response.responseText;
        var o = Ext.JSON.decode(text);
        var nodes = o.nodes;
        for (var i = 0; i < nodes.length; i++) {
          var node = nodes[i];
          this.placeNode(node.id, new google.maps.LatLng(node.latitude, node.longitude), node.name, node.kind);
        }
        var edges = o.edges;
        for (var i = 0; i < edges.length; i++) {
          var edge = edges[i];
          this.placeEdge(edge.id, new google.maps.LatLng(edge.source.latitude, edge.source.longitude),
              new google.maps.LatLng(edge.target.latitude, edge.target.longitude));
        }
        this.autoCenter();
      }
    });
  },

  /**
   * Places an edge on the map.
   * @param src google.maps.LatLng
   * @param target google.maps.LatLng
   */
  placeEdge: function(id, src, target) {
    var polyline = new google.maps.Polyline({
      path: [src, target],
      map: this.map,
      strokeOpacity: 1.0,
      strokeWeight: 2
    });
    
    var scope = this;

    this.polylineArray.push(polyline);
    google.maps.event.addListener(polyline, 'click', function() {
      scope.fireEdgeClickEvent(id);
    });
  },

  /**
   * Places a mark on the map.
   * @param location google.maps.LatLng
   * @param title String
   * @param kind String (company, supplier)
   */
  placeNode: function(id, location, title, kind) {
    var marker = new google.maps.Marker({
      position: location,
      map: this.map,
      title: title,
      icon: 'css/images/' + kind + '.png'
    });
    
    var scope = this;

    this.markerArray.push(marker);
    google.maps.event.addListener(marker, 'click', function() {
      scope.fireNodeClickEvent(id);
    });
  },

  fireNodeClickEvent: function(nodeId) {
    this.fireEvent('nodeclick', this, nodeId);
  },

  fireEdgeClickEvent: function(edgeId) {
    this.fireEvent('edgeclick', this, edgeId);
  },

  deleteMarkers: function() {
    while (this.markerArray.length > 0) {
      var marker = this.markerArray.pop();
      marker.setMap(null);
      marker = null;
    }
  },

  deleteEdges: function() {
    while (this.polylineArray.length > 0) {
      var polyline = this.polylineArray.pop();
      polyline.setMap(null);
      polyline = null;
    }
  },
  
  // @param boolean
  displayMarkers: function(display) {
	  for (var i = 0; i < this.markerArray.length; i++) {
		  var marker = this.markerArray[i];
		  if (display) {
			  marker.setMap(this.map);
		  } else {
			  marker.setMap(null);
		  }
	  }
  },
  
  // @param boolean
  displayEdges: function(display) {
    for (var i = 0; i < this.polylineArray.length; i++) {
      var polyline = this.polylineArray[i];
      polyline.setMap(display ? this.map : null);
    }
  },

  autoCenter: function() {
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < this.markerArray.length; i++) {
      var marker = this.markerArray[i];
      bounds.extend(marker.getPosition());
        
    }
    //alert("map bounds: " + bounds);
    //this.map.setZoom(this.map.getBoundsZoomLevel(bounds));
    this.map.fitBounds(bounds);
    //this.map.setCenter(bounds.getCenter());
  },

  onDestroy: function() {
    this.callParent(arguments);
  }

});
