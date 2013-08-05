Ext.define('riskanalyzer.frontend.FrontendApp', {

	extend : 'Ext.container.Viewport',

	requires : 'riskanalyzer.frontend.MainPanel',

	initComponent : function() {
		var me = this;
		Ext.apply(me, {
			layout : 'border',
			padding : 5,
			items : [ {
				xtype : 'box',
				id : 'header',
				region : 'north',
				html : '<h1>Risk Analyzer Frontend - ${project.version}</h1>',
				height : 30
			},

			me.createMainPanel() ]
		});

		me.callParent(arguments);
	},

	createMainPanel: function() {
		var me = this;
		me.mainPanel = Ext.create('widget.mainpanel', {
			region: 'center'
		});
		return me.mainPanel;
	}

});