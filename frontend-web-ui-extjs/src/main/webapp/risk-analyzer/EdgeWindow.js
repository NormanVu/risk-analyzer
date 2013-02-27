Ext.define('RiskAnalyzer.EdgeWindow', {
	extend: 'Ext.window.Window',

	alias: 'widget.edgewindow',

	plain: true,

	initComponent: function() {
		this.addEvents('edgecreated');

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
            defaults : {
            	anchor : '100%',
            	labelWidth : 120
            },
            items: [{
                xtype: 'hiddenfield',
                name: 'id'
              },
              {
                name: 'sourceId',
                fieldLabel: 'Source',
                xtype: 'combo',
                store: this.sourceStore,
                queryMode: 'local',
                valueField: 'id',
                displayField: 'name',
                allowBlank: false,
                editable: false
            },
            {
                name: 'targetId',
                fieldLabel: 'Target',
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
            	name: 'purchasingVolume',
            	fieldLabel: 'Purchasing Volume',
            	minValue: 0,
            	maxValue: 1,
            	hideTrigger: true,
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
                handler: this.onSaveClick
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
    onSaveClick: function() {
      if (this.form.getForm().isValid()) {
        var fieldValues = this.form.getForm().getFieldValues();
        this.form.setLoading({msg: 'Saving distribution channel...'});
        Ext.Ajax.request({
            url: 'service/distribution-channel',
            jsonData : fieldValues,
            success : this.onSaveSuccess,
            failure : this.onSaveFailure,
            scope: this
        });
      }
    },

    onSaveSuccess : function(response){
        this.form.setLoading(false);

        this.fireEvent('edgecreated', this);
        this.destroy();
    },

    onSaveFailure : function(){
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
