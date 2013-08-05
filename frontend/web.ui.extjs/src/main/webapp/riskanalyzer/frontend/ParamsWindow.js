Ext.define('riskanalyzer.frontend.ParamsWindow', {

	extend: 'Ext.window.Window',

	alias: 'widget.paramswindow',
	plain: true,

	initComponent: function() {
		var me = this;
		me.addEvents('paramsvalid');

		me.form = Ext.create('widget.form', {
			bodyPadding: 10,
			border: false,
			unstyled: true,
			defaults: {
				anchor: '100%',
				labelWidth: 120,
				allowBlank: false
			},
			items: [{
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
				value: 'http://risk-analyzer-backend.appspot.com/soap/endpoint'
			}]
		});

		Ext.apply(me, {
			width: 380,
			modal: true,
			title: 'Frequency Distribution Parameters',
			iconCls: 'feed',
			layout: 'fit',
			items: me.form,
			buttons: [{
				xtype: 'button',
				text: 'Calculate',
				scope: me,
				handler: me.onCalculateClick
			}, {
				xtype: 'button',
				text: 'Cancel',
				scope: me,
				handler: me.destroy
			}]
		});

		this.callParent(arguments);
	},

	onCalculateClick: function() {
		var me = this;
		if (me.form.getForm().isValid()) {
			var fieldValues = me.form.getForm().getFieldValues();

			me.form.setLoading({
				msg: 'Calculating...'
			});

			Ext.Ajax.request({
				url: 'service/frequency-distribution',
				timeout: 1800000,
				jsonData: fieldValues,
				success: me.onCalculateSuccess,
				failure: me.onCalculateFailure,
				scope: me
			});
		}
	},

	onCalculateSuccess: function(response) {
		var me = this;
		me.form.setLoading(false);
		var json = Ext.JSON.decode(response.responseText);
		var win = Ext.create('widget.reportwindow');
		win.setFrequencyDistributionData(json.frequencyDistributionData);
		win.setOutputParamsData(json.outputParamsData);
		win.setOutputParamsFormData(json.outputParamsFormData);
		win.show();
		me.destroy();
	},

	onCalculateFailure: function() {
		this.form.setLoading(false);
		Ext.MessageBox.show({
			title: 'Application Error',
			msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.ERROR
		});
	}

});