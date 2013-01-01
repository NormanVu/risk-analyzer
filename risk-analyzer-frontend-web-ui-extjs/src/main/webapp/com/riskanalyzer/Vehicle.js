Ext.define('com.riskanalyzer.Vehicle', {
	config : {
		Manufacturer : 'Aston Martin',
		Model : 'Vanquish'
	},

	constructor : function(config) {
		this.initConfig(config);
	},

	getDetails : function() {
		alert('I am an ' + this.Manufacturer + ' ' + this.Model);
	}
});
