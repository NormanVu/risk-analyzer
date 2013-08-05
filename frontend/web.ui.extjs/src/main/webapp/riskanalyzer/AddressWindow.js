/**
 * @class RiskAnalyzer.AddressWindow
 * @extends Ext.window.Window
 *
 * Shows a dialog for finding address.
 * 
 * @constructor
 * Create a new Address Window
 * @param {Object} config The config object
 */

Ext.define('RiskAnalyzer.AddressWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.addresswindow',
  plain: true,

  initComponent: function() {

    this.geocoder = new google.maps.Geocoder();

    this.form = Ext.create('widget.form', {
        bodyPadding: 10,
        border: false,
        unstyled: true,
        items: [
          {
             xtype: 'textfield',
             anchor: '100%',
             fieldLabel: 'Address',
             itemId: 'address',
             allowBlank: false
          }
        ]
    });

    Ext.apply(this, {
      width: 350,
      modal: true,
      title: 'Find Address',
      iconCls: 'feed',
      layout: 'fit',
      items: this.form,
      buttons: [
        {
          xtype: 'button',
          text: 'Find',
          scope: this,
          handler: this.onFindClick
        },
        {
          xtype: 'button',
          text: 'Cancel',
          scope: this,
          handler: this.destroy
        }
      ]
    });

    this.callParent(arguments);
  },

  /**
    * React to the Find button being clicked.
    * @private
    */
  onFindClick: function(){
    if (this.form.getForm().isValid()) {
      var address = this.form.getComponent('address').getValue();
      //alert("address: " + address);
      
      this.geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          alert("result: " + results[0].geometry.location);
          //map.setCenter(results[0].geometry.location);
          //var marker = new google.maps.Marker({
            //  map: map,
              //position: results[0].geometry.location
          //});
        } else {
          alert("Geocode was not successful for the following reason: " + status);
        }
      });
    }


      //this.fireEvent('paramsvalid', this);
      //this.destroy();

  }

});
