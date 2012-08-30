Ext.define('RiskAnalyzer.NodeWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.nodewindow',
  plain: true,

  defaultFeeds: [
    ['company', 'Company'],
    ['supplier', 'Supplier']
  ],

  initComponent: function() {
    this.addEvents(
    /**
      * @event nodecreated
      * @param {RiskAnalyzer.NodeWindow} this
      */
    'nodecreated'
  );

  this.geocoder = new google.maps.Geocoder();

  this.form = Ext.create('widget.form', {
    bodyPadding: '12 10 10',
    border: false,
    unstyled: true,
    items: [{
      xtype: 'fieldset',
              padding: 10,
              items: [
                {
                  xtype: 'hiddenfield',
                  name: 'id'
                },
	            {
	                anchor: '100%',
	                name: 'kind',
	                fieldLabel: 'Kind',
	                xtype: 'combo',
	                store: this.defaultFeeds,
	                editable: false,
	                allowBlank: false
	            },
	            {anchor: '100%',
	            	name: 'name',
	            	fieldLabel: 'Name',
	            	xtype: 'textfield',
	            	allowBlank: false

	            },
	            {
	            	anchor: '100%',
	            	name: 'description',
	            	fieldLabel: 'Description',
	            	xtype: 'textarea',
	            	maxLength: 255
	            }
            ]
            },
            {
              xtype: 'fieldset',
              title: 'Location',
              padding: 10,
              items: [
            {
            	anchor: '100%',
            	name: 'address',
            	fieldLabel: 'Address',
            	xtype: 'textfield',
            	allowBlank: false,
            	listeners: {
            		blur: this.onAddressBlur,
            		scope: this
            	}
            },
	            {
	            	anchor: '100%',
	            	name: 'latitude',
	            	fieldLabel: 'Latitude',
	            	xtype: 'numberfield',
	            	hideTrigger: true,
	            	allowBlank: false,
	            	decimalPrecision: 8
	            },
	            {
	            	anchor: '100%',
	            	name: 'longitude',
	            	fieldLabel: 'Longitude',
	            	xtype: 'numberfield',
	            	hideTrigger: true,
	            	allowBlank: false,
	            	decimalPrecision: 8
	            }
	            ]
            },

            {
            	xtype: 'fieldset',
            	title: 'Hazard Event Intensities',
            	padding: 10,
            	items: [
{
    xtype: 'container',
    anchor: '100%',
    layout:'column',
    items:[{
        xtype: 'container',
        columnWidth:.5,
        layout: 'anchor',
        items: [{
            xtype: 'numberfield',
            fieldLabel: 'Risk Category I',
            name: 'riskCategory1',
            anchor:'96%',
            hideTrigger: true,
            allowBlank: false,
            labelWidth: 110,
            decimalPrecision: 2
        }, {
            xtype: 'numberfield',
            fieldLabel: 'Risk Category II',
            name: 'riskCategory2',
            anchor:'96%',
            hideTrigger: true,
            allowBlank: false,
            labelWidth: 110,
            decimalPrecision: 2
        },
        {
            xtype: 'numberfield',
            fieldLabel: 'Risk Category III',
            name: 'riskCategory3',
            anchor:'96%',
            hideTrigger: true,
            allowBlank: false,
            labelWidth: 110,
            decimalPrecision: 2
        }
        
        ]
    },
    
    {
        xtype: 'container',
        columnWidth:.5,
        layout: 'anchor',
        items: [{
            xtype: 'numberfield',
            fieldLabel: 'Recovery Time I',
            name: 'recoveryTime1',
            anchor:'100%',
            hideTrigger: true,
            allowBlank: false,
            labelWidth: 110,
            decimalPrecision: 2
        },{
            xtype: 'numberfield',
            fieldLabel: 'Recovery Time II',
            name: 'recoveryTime2',
            anchor:'100%',
            hideTrigger: true,
            allowBlank: false,
            labelWidth: 110,
            decimalPrecision: 2
        },
        {
            xtype: 'numberfield',
            fieldLabel: 'Recovery Time III',
            name: 'recoveryTime3',
            anchor:'100%',
            hideTrigger: true,
            allowBlank: false,
            labelWidth: 110,
            decimalPrecision: 2
        }
        ]
    }
    
    
    
    
    ]},
    
    {
    	xtype: 'radiogroup',
    	fieldLabel: 'Type',
    	columns: 2,
    	allowBlank: false,
    	labelWidth: 110,
    	defaults: {
    		name: 'type'
    	},
    	items: [
    	        {
    	        	inputValue: 'independent',
    	        	boxLabel: 'Independent'
    	        },
    	        {
    	        	inputValue: 'correlated',
    	        	boxLabel: 'Correlated'
    	        }
    	]
    }
    ]
            }


            ]
        });


        Ext.apply(this, {
            width: 400,
            title: 'Facility',
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

    // TODO RENAME TO setFormData(data)
    setFieldValues: function(values) {
      this.form.getForm().setValues(values);
    },

    onAddressBlur: function(field, eOpts) {
      var form = this.form;
      var fieldValues = form.getForm().getFieldValues();
      var address = fieldValues.address;
      form.setLoading({msg: 'Validating address...'});

      this.geocoder.geocode({'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          form.setLoading(false);
          var lng = results[0].geometry.location.lng();
          var lat = results[0].geometry.location.lat();

          fieldValues.latitude = lat;
          fieldValues.longitude = lng;
          form.getForm().setValues(fieldValues);
        } else {
          form.setLoading(false);
          field.markInvalid(address + " is not a valid address");
          fieldValues.latitude = null;
          fieldValues.longitude = null;
          form.getForm().setValues(fieldValues);
        }
      });
    },

    /**
     * React to the Save button being clicked.
     * @private
     */
    onSaveClick: function() {
      if (this.form.getForm().isValid()) {
        var fieldValues = this.form.getForm().getFieldValues();

        this.form.setLoading({
            msg: 'Saving node...'
        });
        Ext.Ajax.request({
            url: 'service/node',
            params: fieldValues,
            success: this.addNodeSuccess,
            failure: this.addNodeFailure,
            scope: this
        });
      }
    },

    /**
     * React to the feed validation passing
     * @private
     * @param {Object} response The response object
     */
    addNodeSuccess: function(response){
        this.form.setLoading(false);
        this.fireEvent('nodecreated', this);
        this.destroy();
    },

    /**
     * React to the feed validation failing
     * @private
     */
    addNodeFailure: function(){
      this.form.setLoading(false);
      Ext.MessageBox.show({
          title: 'Application Error',
          msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
          buttons: Ext.MessageBox.OK,
          icon: Ext.MessageBox.ERROR
      });
    }

});
