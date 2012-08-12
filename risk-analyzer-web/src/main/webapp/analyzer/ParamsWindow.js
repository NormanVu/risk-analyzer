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
      /**
        * @event paramsvalid
        * @param {RiskAnalyzer.ParamsWindow} this
        */
      'paramsvalid'
    );

    this.form = Ext.create('widget.form', {
        bodyPadding: 10,
        border: false,
        unstyled: true,
        items: [
          {
             xtype: 'numberfield',
             anchor: '100%',
             fieldLabel: 'Number of Iterations',
             minValue: 1,
             maxValue: 10000,
             value: 1,
             labelWidth: 120,
             itemId: 'number_of_iterations',
             allowBlank: false
          },
          {
             xtype: 'numberfield',
             anchor: '100%',
             fieldLabel: 'Time Horizon',
             minValue: 1,
             maxValue: 365,
             value: 1,
             labelWidth: 120,
             itemId: 'time_horizon',
             allowBlank: false
           },
           {
             xtype: 'numberfield',
             anchor: '100%',
             fieldLabel: 'Confidence Level',
             hideTrigger: true,
             minValue: 0,
             maxValue: 1,
             value: 0.95,
             labelWidth: 120,
             itemId: 'confidence_level',
             allowBlank: false
           },
           {
             xtype: 'textfield',
             anchor: '100%',
             fieldLabel: 'Endpoint URL',
             labelWidth: 120,
             value: 'http://risk-analyzer.appspot.com/soap/endpoint',
             itemId: 'endpoint_url',
             allowBlank: false
           }
        ]
    });

    Ext.apply(this, {
      width: 380,
      modal: true,
      title: 'Simulation Parameters',
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

  /**
    * React to the Run button being clicked.
    * @private
    */
  onRunClick: function(){
    if (this.form.getForm().isValid()) {
        var numberOfIterations = this.form.getComponent('number_of_iterations').getValue();
        var timeHorizon = this.form.getComponent('time_horizon').getValue();
        var confidenceLevel = this.form.getComponent('confidence_level').getValue();
        
        this.form.setLoading({
            msg: 'Running simulation...'
        });

        Ext.Ajax.request({
            url: 'service/RunSimulation.do',
            params: {
                number_of_iterations: numberOfIterations,
                time_horizon: timeHorizon,
                confidence_level: confidenceLevel
            },
            success: this.onRunSimulationSuccess,
            failure: this.onRunSimulationFailure,
            scope: this
        });

    }
  },

  onRunSimulationSuccess: function(response) {
    this.form.setLoading(false);
    this.fireEvent('paramsvalid', this);
    this.destroy();
  },

  onRunSimulationFailure: function() {
    this.form.setLoading(false);
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onSubmitClick: function() {
    if (this.form.getForm().isValid()) {
      var numberOfIterations = this.form.getComponent('number_of_iterations').getValue();
      var timeHorizon = this.form.getComponent('time_horizon').getValue();
      var confidenceLevel = this.form.getComponent('confidence_level').getValue();
      var endpointUrl = this.form.getComponent('endpoint_url').getValue();


      this.form.setLoading({
        msg: 'Running simulation...'
      });

      Ext.Ajax.request({
        url: 'service/SubmitSimulation.do',
        timeout: 1800000,
        params: {
          number_of_iterations: numberOfIterations,
          time_horizon: timeHorizon,
          confidence_level: confidenceLevel,
          endpoint_url: endpointUrl
        },
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
