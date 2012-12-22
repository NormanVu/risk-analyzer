/**
 * @class RiskAnalyzer.ParamsWindow
 * @extends Ext.window.Window
 *
 * Shows a dialog for editing simulation parameters.
 * 
 * @constructor
 * Create a new Params Window
 * @param {Object} config The config object
 */

Ext.define('RiskAnalyzer.ParamsWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.paramswindow',
  plain: true,

  initComponent: function() {
    this.addEvents(
      'paramsvalid'
    );

    this.form = Ext.create('widget.form', {
        bodyPadding: 10,
        border: false,
        unstyled: true,
        defaults : {
        	anchor : '100%',
        	labelWidth : 120,
        	allowBlank : false
        },
        items: [
          {
             xtype: 'numberfield',
             name: 'numberOfIterations',
             fieldLabel: 'Number of Iterations',
             minValue: 1,
             maxValue: 10000,
             value: 1
          }, {
             xtype: 'numberfield',
             fieldLabel: 'Time Horizon',
             name: 'timeHorizon',
             minValue: 1,
             maxValue: 365,
             value: 1
           }, {
             xtype: 'numberfield',
             name: 'confidenceLevel',
             fieldLabel: 'Confidence Level',
             hideTrigger: true,
             minValue: 0,
             maxValue: 1,
             value: 0.95
           }, {
             xtype: 'textfield',
             name: 'endpointUrl',
             fieldLabel: 'Endpoint URL',
             value: 'http://backend.appspot.com/soap/endpoint'
           }
        ]
    });

    Ext.apply(this, {
      width: 380,
      modal: true,
      title: 'Frequency Distribution Parameters',
      iconCls: 'feed',
      layout: 'fit',
      items: this.form,
      buttons: [{
        xtype: 'button',
        text: 'Run',
        scope: this,
        handler: this.onSubmitClick
      },{
        xtype: 'button',
        text: 'Cancel',
        scope: this,
        handler: this.destroy
      }]
    });

    this.callParent(arguments);
  },

  onSubmitClick: function() {
    if (this.form.getForm().isValid()) {
    	var fieldValues = this.form.getForm().getFieldValues();


      this.form.setLoading({
        msg: 'Running simulation...'
      });

      Ext.Ajax.request({
        url: 'service/frequency-distribution',
        timeout: 1800000,
        //params: fieldValues,
        jsonData : fieldValues,
        success: this.onSubmitSimulationSuccess,
        failure: this.onSubmitSimulationFailure,
        scope: this
      });
    }
  },

  onSubmitSimulationSuccess: function(response) {
    this.form.setLoading(false);
    var json = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.reportwindow');
    win.setFrequencyDistributionData(json.frequencyDistributionData);
    win.setOutputParamsData(json.outputParamsData);
    win.setOutputParamsFormData(json.outputParamsFormData);
    win.show();
    this.destroy();
  },

  onSubmitSimulationFailure: function() {
    this.form.setLoading(false);
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  }

});
