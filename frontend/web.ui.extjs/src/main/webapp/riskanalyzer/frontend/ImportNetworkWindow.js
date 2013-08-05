Ext.define('riskanalyzer.frontend.ImportNetworkWindow', {

	extend: 'Ext.window.Window',

	alias: 'widget.importwindow',
	plain: true,

	initComponent: function() {
		var me =  this;
		me.addEvents('networkimported');

		me.form = Ext.create('widget.form', {
			bodyPadding: '12 10 10',
			border: false,
			unstyled: true,
			items: [{
				anchor: '100%',
				xtype: 'filefield',
				allowBlank: false,
				id: 'form-file',
				fieldLabel: 'Network XML',
				name: 'networkXml',
				buttonText: 'Browse...'
            }]
		});

		Ext.apply(me, {
			width: 350,
			title: 'Import Network',
			modal: true,
			iconCls: 'feed',
			layout: 'fit',
			items: me.form,
			buttons: [{
				xtype: 'button',
				text: 'Import',
				scope: me,
				handler: me.onImportClick
			}, {
				xtype: 'button',
				text: 'Cancel',
				scope: me,
				handler: me.destroy
			}]
		});
		me.callParent(arguments);
	},

	onImportClick: function() {
		var me = this;
		if(me.form.getForm().isValid()){
			me.form.submit({
				url: 'service/network',
				waitMsg: 'Importing network...',
				scope: me,
				success: me.onImportSuccess
			});
		}
	},

	onImportSuccess: function(fp, o) {
		this.fireEvent('networkimported', this);
		this.destroy();
	}

});