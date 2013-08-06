Ext.define('riskanalyzer.frontend.NetworkPanel', {

	extend: 'Ext.tree.Panel',

	alias: 'widget.networkpanel',

	initComponent: function() {
		var me = this;
		me.treeStore = Ext.create('Ext.data.TreeStore', {
			proxy: {
				type: 'memory'
			}
		});

		Ext.apply(me, {
			store: me.treeStore,
			rootVisible: true
		});

		me.callParent(arguments);
	},

	afterRender: function() {
		this.update();
	},

	update: function() {
		var me = this; 
	
		Ext.Ajax.request({
			url: 'service/network',
			scope: me,
			success: me.onUpdateSuccess,
			failure: me.onUpdateFailure
		});

	},

	onUpdateSuccess: function(response) {
		var network = Ext.JSON.decode(response.responseText);

		var facilitiesNode = {
			text: 'Facilities',
			cls: 'folder',
			expanded: true,
			children: []
		};
		
		var channelsNode = {
			text: 'Channels',
			cls: 'folder',
			expanded: true,
			children: []
		};

		var rootNode = {
			text: 'Supply Chain',
			cls: 'folder',
			expanded: true,
			children: [facilitiesNode, channelsNode]
		};

		for (var i = 0; i < network.nodes.length; i++) {
			var facility = network.nodes[i];
			facilitiesNode.children.push({
				id: 'n_' + facility.id,
				text: facility.name,
				leaf: true
			});
		}

		for (var i = 0; i < network.edges.length; i++) {
			var channel = network.edges[i];
			channelsNode.children.push({
				id: 'e_' + channel.id,
				text: channel.source.name + ' > ' + channel.target.name,
				leaf: true
			});
		}

		this.treeStore.setRootNode(rootNode);
	},

	onUpdateFailure: function() {
		alert('There was an error!');
	},

	onDestroy: function() {
		this.callParent(arguments);
	}

});