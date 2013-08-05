Ext.define('riskanalyzer.frontend.FrequencyDistributionWindow', {

	extend: 'Ext.window.Window',
	
	requires: 'riskanalyzer.frontend.EmailWindow',

	alias: 'widget.reportwindow',
	plain: true,

	initComponent: function() {

    this.frequencyDistributionStore = Ext.create('Ext.data.Store', {
      fields: ['x', 'y']
    });
    this.outputParamsStore = Ext.create('Ext.data.Store', {
      fields: ['param', 'value']
    });

    this.contentPanel = Ext.create('Ext.panel.Panel', {
      layout: { type: 'border'},
      border: false,
      items: [
        this.createFrequencyDistributionChart(),
        this.createParamsPanel()
      ]
    });

    Ext.apply(this, {
      width: 820,
      height: 500,
      title: 'Frequency Distribution',
      modal: true,
      collapsible: true,
      animCollapse: true,
      maximizable: true,
      iconCls: 'feed', // TODO CHANGE THIS ICON
      layout: {type: 'fit'},
      items: [this.contentPanel],
      dockedItems: this.createToolbar(),
      bbar: Ext.create('Ext.ux.StatusBar', {
          id: 'win-statusbar',
          items: ['-', 'Number of Iterations: 100', '-', 'Time Horizon: 10', '-', 'Confidence Level: 0.95']
          })
    });

    this.callParent(arguments);
  },

  setFrequencyDistributionData: function(data) {
    this.frequencyDistributionStore.add(data);
  },

  setOutputParamsData: function(data) {
    this.outputParamsStore.add(data);
  },

  setOutputParamsFormData: function(data) {
    this.outputParamsForm.getForm().setValues(data);
  },

  createToolbar: function() {
    this.toolbar = Ext.create('widget.toolbar', {
      items: [
        {text: 'Export', iconCls: 'feed', handler: this.onExportClick, scope: this},
        {text: 'E-mail', iconCls: 'feed', handler: this.onEmailClick, scope: this}
      ]
    });
    return this.toolbar;
  },

  createFrequencyDistributionChart: function() {
    var chart = Ext.create('Ext.chart.Chart', {
      region: 'center',
      style: 'background: #fff',
      store: this.frequencyDistributionStore,
      axes: [{
        type: 'Numeric',
        position: 'left',
        fields: ['y'],
        grid: true,
        label: {
          font: '9px Arial'
        }
      }, {
        type: 'Numeric',
        position: 'bottom',
        fields: ['x'],
        label: {
          font: '9px Arial'
        }
      }],
      series: [{
        type: 'column',
        axis: 'left',
        xField: 'x',
        yField: 'y',
        style: {
          fill: '#456d9f'
        }
      }]
    });

    return chart;
  },

  createRadarChart: function() {
    var radarChart = Ext.create('Ext.chart.Chart', {
      insetPadding: 40,
      flex: 1,
      animate: true,
      store: this.outputParamsStore,
      axes: [{
        steps: 5,
        type: 'Radial',
        position: 'radial',
        maximum: 100
      }],
      series: [{
        type: 'radar',
        xField: 'param',
        yField: 'value',
        showInLegend: false,
        showMarkers: true,
        markerConfig: {
          radius: 4,
          size: 4
        },
        style: {
          fill: 'rgb(194,214,240)',
          opacity: 0.5,
          'stroke-width': 0.5
        }
      }]
    });

    return radarChart;
  },

  createParamsForm: function() {
    this.outputParamsForm = Ext.create('widget.form', {
      bodyPadding: 10,
      border: false,
      unstyled: true,
      items: [{xtype: 'fieldset',
        padding: 10,
        items: [{
          xtype: 'textfield',
          anchor: '100%',
          name: 'Mean',
          fieldLabel: 'Mean',
          disabled: true
        },{
          xtype: 'textfield',
          anchor: '100%',
          name: 'VaR',
          fieldLabel: 'VaR',
          disabled: true
        },{
          xtype: 'textfield',
          anchor: '100%',
          name: 'ES',
          fieldLabel: 'ES',
          disabled: true
        },{
          xtype: 'textfield',
          anchor: '100%',
          name: 'Variance',
          fieldLabel: 'Variance',
          disabled: true
        }]
      }]
    });

    return this.outputParamsForm;
  },

  createParamsPanel: function() {
	  var panel = Ext.create('Ext.panel.Panel', {
		  width: 280,
		  region: 'east',
		  border: false,
		  layout: {
			  type: 'vbox',
			  align: 'stretch'
		  },
		  items: [
		    this.createParamsForm(),
		    this.createRadarChart()
		  ]
	  });
	  return panel;
  },
  
  onExportClick: function() {
	    //alert("show network display");
	    try {
	      Ext.destroy(Ext.get('reportIframe'));
	    } catch(e) {
	      alert("error " + e);
	    }

	    Ext.core.DomHelper.append(document.body, {
	      tag: 'iframe',
	      id:'reportIframe',
	      css: 'display:none;visibility:hidden;height:0px;',
	      src: 'ExportReport.do',
	      frameBorder: 0,
	      width: 0,
	      height: 0
	    });

	  },

onEmailClick: function() {
	    var win = Ext.create('widget.emailwindow');

	    win.show();

}


});
