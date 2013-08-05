Ext.define('riskanalyzer.frontend.DistributionChannelWindow', {

	extend: 'Ext.window.Window',

	alias: 'widget.edgewindow',

	plain: true,

	initComponent: function() {
		var me = this;
		me.addEvents('edgecreated');

		me.sourceStore = Ext.create('Ext.data.Store', {
			fields: ['id', 'name']
		});

		me.targetStore = Ext.create('Ext.data.Store', {
			fields: ['id', 'name']
		});

		me.form = Ext.create('widget.form', {
			bodyPadding: '12 10 10',
			border: false,
			unstyled: true,
			defaults: {
				anchor: '100%',
				labelWidth: 120
			},
			items: [{
				xtype: 'hiddenfield',
				name: 'id'
			}, {
				name: 'sourceId',
				fieldLabel: 'Source',
				xtype: 'combo',
				store: me.sourceStore,
				queryMode: 'local',
				valueField: 'id',
				displayField: 'name',
				allowBlank: false,
				editable: false
			}, {
				name: 'targetId',
				fieldLabel: 'Target',
				xtype: 'combo',
				store: me.targetStore,
				queryMode: 'local',
				valueField: 'id',
				displayField: 'name',
				allowBlank: false,
				editable: false
			}, {
				xtype: 'numberfield',
				name: 'purchasingVolume',
				fieldLabel: 'Purchasing Volume',
				minValue: 0,
				maxValue: 1,
				hideTrigger: true,
				allowBlank: false
			}]
		});

		Ext.apply(me, {
			width: 350,
			title: 'Distribution Channel',
			modal: true,
			iconCls: 'feed',
			layout: 'fit',
			items: me.form,
			buttons: [{
				xtype: 'button',
				text: 'Save',
				scope: me,
				handler: me.onSaveClick
			}, {
				xtype: 'button',
				text: 'Cancel',
				scope: me,
				handler: me.destroy
			}]
		});
		me.callParent(arguments);
	},

	setFieldValues: function(values) {
		this.form.getForm().setValues(values);
	},

	onSaveClick: function() {
		var me = this;
		if (me.form.getForm().isValid()) {
			var fieldValues = me.form.getForm().getFieldValues();
			me.form.setLoading({
				msg: 'Saving distribution channel...'
			});
			Ext.Ajax.request({
				url: 'service/distribution-channel',
				jsonData: fieldValues,
				success: me.onSaveSuccess,
				failure: me.onSaveFailure,
				scope: me
			});
		}
	},

	onSaveSuccess: function(response) {
		this.form.setLoading(false);

		this.fireEvent('edgecreated', this);
		this.destroy();
	},

	onSaveFailure: function(){
		this.form.setLoading(false);
		Ext.MessageBox.show({
			title: 'Application Error',
			msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.ERROR
		});
	},

	setSource: function(nodes) {
		this.sourceStore.add(nodes);
	},

	setTarget: function(nodes) {
		this.targetStore.add(nodes);
	}

});