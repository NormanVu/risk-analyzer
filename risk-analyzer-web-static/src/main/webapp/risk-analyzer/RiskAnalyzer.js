/**
 * The main RiskAnalyzer application.
 * 
 * @author Daniel Pacak
 */
Ext.define('RiskAnalyzer.App', {
	extend : 'Ext.container.Viewport',

	initComponent : function() {
		Ext.apply(this, {
			layout : 'border',
			padding : 5,
			items : [ {
				xtype : 'box',
				id : 'header',
				region : 'north',
				html : '<h1>Risk Analyzer - 0.0.1-SNAPSHOT</h1>',
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
