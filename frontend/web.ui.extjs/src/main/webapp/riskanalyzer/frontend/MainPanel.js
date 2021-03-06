Ext.define('riskanalyzer.frontend.MainPanel', {

	extend: 'Ext.panel.Panel',

	requires: ['riskanalyzer.frontend.NetworkPanel',
	           'riskanalyzer.frontend.MapPanel',
	           'riskanalyzer.frontend.FacilityWindow',
	           'riskanalyzer.frontend.DistributionChannelWindow',
	           'riskanalyzer.frontend.ParamsWindow',
	           'riskanalyzer.frontend.FrequencyDistributionWindow',
	           'riskanalyzer.frontend.HelpWindow',
	           'riskanalyzer.frontend.ImportNetworkWindow'],

	alias: 'widget.mainpanel',

	initComponent: function() {
		var me = this;
		Ext.apply(me, {
			layout: 'border',
			items: [
				me.createNetworkPanel(),
				me.createMapPanel()
			],
			dockedItems: me.createToolbar()
		});

		me.callParent(arguments);
	},

	createToolbar: function() {
		var me = this;
		this.toolbar = Ext.create('widget.toolbar', {
			items: [{
				xtype: 'buttongroup',
				title: 'Distribution Network',
				columns: 3,
				items: [
					{text: 'Refresh', iconCls: 'feed', handler: me.onRefreshClick, scope: me},
					{text: 'Import', iconCls: 'feed-add', handler: me.onImportClick, scope: me},
					{text: 'Export', iconCls: 'feed-remove', handler: me.onExportClick, scope: me}
				]
			}, {
				xtype: 'buttongroup',
				itemId: 'nodeButtons',
				title: 'Facility',
				columns: 2,
				items: [
					{id: 'newFacilityButton', text: 'New', iconCls: 'feed-add', handler: this.onAddNodeClick, scope: this},
					{text: 'Delete', iconCls: 'feed-remove', handler: this.onDeleteNodeClick, itemId: 'deleteNodeBtn', disabled: true, scope: this}
				]
			}, {
				xtype: 'buttongroup',
				itemId: 'edgeButtons',
				title: 'Distribution Channel',
				columns: 2,
				items: [
					{text: 'New', iconCls: 'feed-add', handler: this.onAddEdgeClick, scope: this},
					{text: 'Delete', iconCls: 'feed-remove', handler: this.onDeleteEdgeClick, itemId: 'deleteEdgeBtn', disabled: true, scope: this}
				]
			}, {
				xtype: 'buttongroup',
				title: 'Analysis',
				columns: 1,
				items: [
					{text: 'Frequency Distribution', iconCls: 'feed', handler: this.onFrequencyDistributionClick, scope: this}
					]
			}, '->', {
				xtype: 'buttongroup',
				title: 'Map',
				columns: 3,
				items: [
					{text: 'Auto Center', iconCls: 'feed', handler: this.onAutoCenterClick, scope: this},
					{text: 'Display', iconCls: 'feed', menu: [
						{text: 'Facility', checked: true, checkHandler: this.onDisplayNodesCheck, scope: this},
						{text: 'Distribution Channel', checked: true, checkHandler: this.onDisplayEdgeCheck, scope: this}]}
				]
			}, {
				xtype: 'buttongroup',
				title: 'Info',
				columns: 1,
				items: [
					{text: 'About', iconCls: 'feed', handler: this.onHelpClick, scope: this}
				]
			}]
		});

		return me.toolbar;
	},

  createNetworkPanel: function() {
    this.networkPanel = Ext.create('widget.networkpanel', {
      region: 'west',
      padding: '5 0 5 5',
      width: 250,
      listeners: {
        itemclick: this.onTreeItemClick,
        itemdblclick: this.onTreeItemDblClick,
        scope: this
      }
    });
    return this.networkPanel;
  },

  createMapPanel: function() {
    this.mapPanel = Ext.create('widget.mappanel', {
      listeners: {
        nodeclick: this.onMapNodeClick,
        edgeclick: this.onMapEdgeClick,
        scope: this
      },
      region: 'center',
      padding: 5
    });
    return this.mapPanel;
  },

  onMapNodeClick: function(component, nodeId) {
    this.readNode(nodeId);
  },

  onMapEdgeClick: function(component, edgeId) {
    this.readEdge(edgeId);
  },

  onTreeItemClick: function(view, record, item, index, e, eOpts) {
    var rawId = record.get('id');
    if (rawId) {
      var id = parseInt(rawId.substring(2));
      if (rawId.charAt(0) == 'n') {
        this.onNetworkNodeClicked(id);
      } else if (rawId.charAt(0) == 'e') {
        this.onNetworkEdgeClicked(id);
      }
    } else {
      // TODO JUST DISABLE DELETE BUTTONS
      this.toolbar.getComponent('nodeButtons').getComponent('deleteNodeBtn').setDisabled(true);
      this.toolbar.getComponent('edgeButtons').getComponent('deleteEdgeBtn').setDisabled(true);
    }
  },

  onTreeItemDblClick: function(view, record, item, index, e, eOpts) {
    //alert("tree double clicked!");
    var rawId = record.get('id');
    if (rawId) {
      var id = rawId.substring(2);
      if (rawId.charAt(0) == 'n') {
        this.readNode(id);
      } else if (rawId.charAt(0) == 'e') {
        this.readEdge(id);
      }
    }
  },

  readNode : function(facilityId) {
    Ext.Ajax.request({
        url : 'service/facility/' + facilityId,
        success : this.onReadNodeSuccess,
        failure : this.onReadNodeFailure,
        scope : this
      });
  },

  readEdge : function(distributionChannelId) {
    Ext.Ajax.request({
      url : 'service/distribution-channel/' + distributionChannelId,
      success : this.onReadEdgeSuccess,
      failure : this.onReadEdgeFailure,
      scope: this
    });
  },

  onReadEdgeSuccess: function(response) {
    var json = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.edgewindow', {
      listeners: {
        edgecreated: this.onEdgeCreated,
        scope: this
      }
    });
    win.setSource(json.facilities);
    win.setTarget(json.facilities);
    win.setFieldValues(json);
    win.show();
  },

  onReadEdgeFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onReadNodeSuccess: function(response) {
    var values = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.nodewindow', {
      listeners: {
        scope: this,
        nodecreated: this.onNodeCreated
      }
    });
    win.setFieldValues(values);
    win.show();
  },

  onReadNodeFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onNetworkNodeClicked: function(id) {
    //alert("Node has been clicked: " + id);
    this.toolbar.getComponent('nodeButtons').getComponent('deleteNodeBtn').setDisabled(false);
    this.toolbar.getComponent('edgeButtons').getComponent('deleteEdgeBtn').setDisabled(true);

  },

  onNetworkEdgeClicked: function(id) {
    //alert("Edge has been clicked: " + id);
    this.toolbar.getComponent('edgeButtons').getComponent('deleteEdgeBtn').setDisabled(false);
    this.toolbar.getComponent('nodeButtons').getComponent('deleteNodeBtn').setDisabled(true);
  },

  onRefreshClick: function() {
    this.networkPanel.update();
    this.mapPanel.update();
    this.toolbar.getComponent('nodeButtons').getComponent('deleteNodeBtn').setDisabled(true);
    this.toolbar.getComponent('edgeButtons').getComponent('deleteEdgeBtn').setDisabled(true);
  },

  onImportClick: function() {
    var win = Ext.create('widget.importwindow', {
      listeners: {
        scope: this,
        networkimported: this.onNetworkImported
      }
    });

    win.show();
  },

  onNetworkImported: function(win) {
    this.networkPanel.update();
    this.mapPanel.update();
  },

  onExportClick: function() {
    //alert("show network display");
    try {
      Ext.destroy(Ext.get('testIframe'));
    } catch(e) {
      alert("error " + e);
    }

    Ext.core.DomHelper.append(document.body, {
      tag: 'iframe',
      id:'testIframe',
      css: 'display:none;visibility:hidden;height:0px;',
      src: 'service/network.xml',
      frameBorder: 0,
      width: 0,
      height: 0
    });

  },

  onAddNodeClick: function() {
    var win = Ext.create('widget.nodewindow', {
        listeners: {
          scope: this,
          nodecreated: this.onNodeCreated
        }
    });

    win.show();
  },

  onNodeCreated: function(win) {
    this.networkPanel.update();
    this.mapPanel.update();
  },

  onAddEdgeClick: function() {
    // Before showing Edge Dialog fetch available nodes
    Ext.Ajax.request({
      url: 'service/facility',
      success: this.onAddEdgeSuccess,
      failure: this.onAddEdgeFailure,
      scope: this
    });
  },

  onAddEdgeSuccess: function(response) {
    var nodes = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.edgewindow', {
      listeners: {
        edgecreated: this.onEdgeCreated,
        scope: this
      }
    });

    win.setSource(nodes);
    win.setTarget(nodes);
    win.show();
  },

  onAddEdgeFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onEdgeCreated: function(win) {
    this.networkPanel.update();
    this.mapPanel.update();
  },

  onFrequencyDistributionClick: function() {
    var win = Ext.create('widget.paramswindow', {
        listeners: {
          scope: this,
          paramsvalid: this.onParamsValid
        }
    });

    win.show();

  },

  onParamsValid: function(paramsWin) {
    var reportWin = Ext.create('widget.reportwindow');
    reportWin.show();
  },

  onHelpClick: function() {
    var win = Ext.create('widget.helpwindow');

    win.show();
  },

  // handle on delete node click
  onDeleteNodeClick: function() {
    Ext.MessageBox.confirm('Delete Facility', 'Are you sure you want to do that?', this.deleteNode, this);
  },

  deleteNode: function(btn) {
    if ('yes' === btn ) {
      var selectionModel = this.networkPanel.getSelectionModel();
      var modelArray = selectionModel.getSelection();
      var model = modelArray[0]; // we should only have one selection
      var rawId = model.get('id'); // raw node id
      var id = rawId.substring(2);

      Ext.Ajax.request({
          url: 'service/facility/' + id,
          method : 'DELETE',
          success: this.onDeleteNodeSuccess,
          failure: this.onDeleteNodeFailure,
          scope: this
      });
    }
  },

  onDeleteEdgeClick: function() {
    Ext.MessageBox.confirm('Delete Channel', 'Are you sure you want to do that?', this.deleteEdge, this);
  },

  deleteEdge: function(btn) {
    if ('yes' === btn ) {
      var selectionModel = this.networkPanel.getSelectionModel();
      var modelArray = selectionModel.getSelection();
      var model = modelArray[0]; // we should only have one selection
      var rawId = model.get('id'); // raw node id
      var id = rawId.substring(2);

      Ext.Ajax.request({
          url: 'service/distribution-channel/' + id,
          method : 'DELETE',
          success : this.onDeleteEdgeSuccess,
          failure : this.onDeleteEdgeFailure,
          scope : this
      });
    }
  },

  onDeleteEdgeSuccess: function(response) {
    this.onRefreshClick();
  },

  onDeleteEdgeFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onDeleteNodeSuccess: function(response) {
    var text = response.responseText;
    //alert("node has been deleted");
    this.onRefreshClick();
  },

  onDeleteNodeFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  // TODO REMOVE THIS FUNCTION - IT'S ONLY FOR TESTING
  onRemoveMarkersClick: function() {
    this.mapPanel.deleteMarkers();
    this.mapPanel.deleteEdges();
  },

  onAutoCenterClick: function() {
    //alert('center map');
    this.mapPanel.autoCenter();

  },

  onDisplayNodesCheck: function(item, checked) {
	  //alert("on display node check: " + checked);
	  this.mapPanel.displayMarkers(checked);
  },

  onDisplayEdgeCheck: function(item, checked) {
	  //alert("on display edge check: " + checked);
	  this.mapPanel.displayEdges(checked);
  }

});