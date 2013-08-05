Ext.define('riskanalyzer.frontend.FrontendApp', {

	extend : 'Ext.container.Viewport',

	requires : 'riskanalyzer.frontend.MainPanel',

	initComponent : function() {
		Ext.apply(this, {
			layout : 'border',
			padding : 5,
			items : [ {
				xtype : 'box',
				id : 'header',
				region : 'north',
				html : '<h1>Risk Analyzer Frontend - ${project.version}</h1>',
				height : 30
			},

			this.createMainPanel() ]
		});

		this.callParent(arguments);
	},

	createMainPanel : function() {
		this.mainPanel = Ext.create('widget.mainpanel', {
			region : 'center'
		});
		return this.mainPanel;
	}

});