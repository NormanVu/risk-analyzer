Ext.define('RiskAnalyzer.HelpWindow', {
	extend : 'Ext.window.Window',

	alias : 'widget.helpwindow',
	plain : true,

	initComponent: function() {
		Ext.apply(this, {
			width : 400,
			height : 270,
			modal : true,
			title : 'About',
			iconCls : 'feed',
			layout : 'fit',
			html : '<br/><center><strong>Risk Analyzer Frontend - ${project.version}</strong></center>' +
				"<br/><center>Copyright 2010-2012 <a href='mailto:pacak.daniel@gmail.com'>Daniel Pacak</a>, Inc. All rights reserved.</center>",
			buttons : [{
				xtype : 'button',
				text : 'Close',
				scope : this,
				handler : this.destroy
			}]
		});

		this.callParent(arguments);
	}

});
