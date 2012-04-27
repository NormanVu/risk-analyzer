Ext.define('RiskAnalyzer.ImportWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.importwindow',
  plain: true,

  initComponent: function() {
    this.addEvents(
        /**
         * @event networkimported
         * @param {FeedViewer.ImportWindow} this
         */
        'networkimported'
    );

    this.form = Ext.create('widget.form', {
            bodyPadding: '12 10 10',
            border: false,
            unstyled: true,
            items: [
            {
            	anchor: '100%',
                xtype: 'filefield',
                allowBlank: false,
                id: 'form-file',
                //emptyText: 'Select an XML file',
                fieldLabel: 'Network XML',
                name: 'xml-path',
                buttonText: 'Browse...'
            }
            
            ]
        });
        
        
        Ext.apply(this, {
            width: 350,
            title: 'Import Network',
            modal: true,
            iconCls: 'feed',
            layout: 'fit',
            items: this.form,
            buttons: [{
                xtype: 'button',
                text: 'Import',
                scope: this,
                handler: this.onImportClick
            }, {
                xtype: 'button',
                text: 'Cancel',
                scope: this,
                handler: this.destroy
            }]
        });
        this.callParent(arguments);
    },
    
    /**
     * React to the Import button being clicked.
     * @private
     */
    onImportClick: function() {
        
        
       if(this.form.getForm().isValid()){
            this.form.submit({
                url: 'Import.do',
                waitMsg: 'Importing network...',
                scope: this,
                success: this.onImportSuccess
            });
        }
    },

  onImportSuccess: function(fp, o) {
    //console.log("import succeeded: " + o.result.success);
    //console.log("Processed file " + o.result.file + " on the server");
    this.fireEvent('networkimported', this);
    this.destroy();
  }

});
