Ext.define('RiskAnalyzer.NetworkPanel', {
  extend: 'Ext.tree.Panel',

  alias: 'widget.networkpanel',

  initComponent: function() {
    this.treeStore = Ext.create('Ext.data.TreeStore', {
      proxy: {
        type: 'ajax',
        url: 'NetworkTree.do'
      }
    });
    Ext.apply(this, {
      store: this.treeStore,
      rootVisible: false
    });

    this.callParent(arguments);
  },

  update: function() {
    this.treeStore.load();
  },

  onDestroy: function() {
    this.callParent(arguments);
  }

});
