Ext.define('RiskAnalyzer.EdgeWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.edgewindow',

  plain: true,

  initComponent: function() {
    this.addEvents(
      /**
       * @event nodecreated
       * @param {RiskAnalyzer.EdgeWindow} this
       */
      'edgecreated'
    );

    this.sourceStore = Ext.create('Ext.data.Store', {
      fields: ['id', 'name']
    });

    this.targetStore = Ext.create('Ext.data.Store', {
      fields: ['id', 'name']
    });

        this.form = Ext.create('widget.form', {
            bodyPadding: '12 10 10',
            border: false,
            unstyled: true,
            items: [{
                xtype: 'hiddenfield',
                name: 'edge_id'
              },
              {
                anchor: '100%',
                name: 'edge_source',
                fieldLabel: 'Source',
                labelWidth: 120,
                xtype: 'combo',
                store: this.sourceStore,
                queryMode: 'local',
                valueField: 'id',
                displayField: 'name',
                allowBlank: false,
                editable: false
            },
            {
                anchor: '100%',
                name: 'edge_target',
                fieldLabel: 'Target',
                labelWidth: 120,
                xtype: 'combo',
                store: this.targetStore,
                queryMode: 'local',
                valueField: 'id',
                displayField: 'name',
                allowBlank: false,
                editable: false
            },
            {
            	xtype: 'numberfield',
            	anchor: '100%',
            	name: 'edge_purchasing_volume',
            	fieldLabel: 'Purchasing Volume',
            	minValue: 0,
            	maxValue: 1,
            	hideTrigger: true,
            	labelWidth: 120,
            	allowBlank: false
            	
            }
            ]
        });
        
        
        Ext.apply(this, {
            width: 350,
            title: 'Distribution Channel',
            modal: true,
            iconCls: 'feed',
            layout: 'fit',
            items: this.form,
            buttons: [{
                xtype: 'button',
                text: 'Save',
                scope: this,
                handler: this.onAddClick
            }, {
                xtype: 'button',
                text: 'Cancel',
                scope: this,
                handler: this.destroy
            }]
        });
        this.callParent(arguments);
    },

    setFieldValues: function(values) {
        this.form.getForm().setValues(values);
    },

    /**
     * React to the add button being clicked.
     * @private
     */
    onAddClick: function() {
      if (this.form.getForm().isValid()) {
        var fieldValues = this.form.getForm().getFieldValues();
        this.form.setLoading({msg: 'Saving edge...'});
        Ext.Ajax.request({
            url: 'service/edge',
            method : 'POST',
            params: fieldValues,
            success: this.addEdgeSuccess,
            failure: this.addEdgeFailure,
            scope: this
        });
      }
    },

    /**
     * React to the edge creation passing
     * @private
     * @param {Object} response The response object
     */
    addEdgeSuccess: function(response){
        this.form.setLoading(false);

        this.fireEvent('edgecreated', this);
        this.destroy();
    },

    /**
     * React to the edge creation failing
     * @private
     */
    addEdgeFailure: function(){
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
