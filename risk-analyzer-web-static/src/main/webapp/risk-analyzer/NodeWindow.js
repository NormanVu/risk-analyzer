Ext.define('RiskAnalyzer.NodeWindow', {
	extend : 'Ext.window.Window',

	alias : 'widget.nodewindow',
	plain : true,

	initComponent : function() {
		this.addEvents('nodecreated');

		this.geocoder = new google.maps.Geocoder();

		this.kindStore = [
			['company', 'Company'],
			['supplier', 'Supplier']
		];

		this.form = Ext.create('widget.form', {
			bodyPadding : '12 10 10',
			border : false,
			unstyled : true,
			items : [{
				xtype : 'fieldset',
				padding : 10,
				defaults : {
					anchor : '100%'
				},
				items : [{
					xtype : 'hiddenfield',
					name : 'id'
				}, {
					name : 'kind',
					fieldLabel : 'Kind',
					xtype : 'combo',
					store : this.kindStore,
					editable : false,
					allowBlank : false
				}, {
					name : 'name',
					fieldLabel : 'Name',
					xtype : 'textfield',
					allowBlank : false
				}, {
					name : 'description',
					fieldLabel : 'Description',
					xtype : 'textarea',
					maxLength : 255
				}]
			}, {
				xtype : 'fieldset',
				title: 'Location',
				padding: 10,
				defaults : {
					anchor : '100%'
				},
				items : [{
					name : 'address',
					fieldLabel : 'Address',
					xtype : 'textfield',
					allowBlank : false,
					listeners : {
						blur : this.onAddressBlur,
						scope : this
					}
				}, {
					name : 'latitude',
					fieldLabel : 'Latitude',
					xtype : 'numberfield',
					hideTrigger : true,
					allowBlank : false,
					decimalPrecision : 8
				}, {
					name : 'longitude',
					fieldLabel : 'Longitude',
					xtype : 'numberfield',
					hideTrigger : true,
					allowBlank : false,
					decimalPrecision : 8
				}]
			}, {
				xtype : 'fieldset',
				title : 'Hazard Event Intensities',
				padding : 10,
				items : [{
					xtype : 'container',
					anchor : '100%',
					layout :'column',
					items : [{
						xtype : 'container',
						columnWidth : .5,
						layout : 'anchor',
						defaults : {
							anchor : '96%',
							labelWidth : 110
						},
						items : [{
							xtype : 'numberfield',
							fieldLabel : 'Risk Category I',
							name : 'riskCategory1',
							hideTrigger : true,
							allowBlank : false,
							decimalPrecision : 2
						}, {
							xtype : 'numberfield',
							fieldLabel : 'Risk Category II',
							name : 'riskCategory2',
							hideTrigger : true,
							allowBlank : false,
							decimalPrecision : 2
						}, {
							xtype : 'numberfield',
							fieldLabel : 'Risk Category III',
							name : 'riskCategory3',
							hideTrigger: true,
							allowBlank: false,
							decimalPrecision: 2
						}]
					}, {
						xtype : 'container',
						columnWidth : .5,
						layout : 'anchor',
						defaults : {
							anchor : '100%',
							labelWidth : 110
						},
						items : [{
							xtype : 'numberfield',
							fieldLabel : 'Recovery Time I',
							name : 'recoveryTime1',
							hideTrigger : true,
							allowBlank : false,
							decimalPrecision: 2
						}, {
							xtype: 'numberfield',
							fieldLabel: 'Recovery Time II',
							name: 'recoveryTime2',
							hideTrigger: true,
							allowBlank: false,
							decimalPrecision: 2
						}, {
							xtype: 'numberfield',
							fieldLabel: 'Recovery Time III',
							name: 'recoveryTime3',
							hideTrigger: true,
							allowBlank: false,
							decimalPrecision: 2
						}]
					}]
				}, {
					xtype: 'radiogroup',
					fieldLabel: 'Type',
					columns: 2,
					allowBlank: false,
					labelWidth: 110,
					defaults: {
						name: 'type'
					},
					items: [{
						inputValue: 'independent',
						boxLabel: 'Independent'
					}, {
						inputValue: 'correlated',
						boxLabel: 'Correlated'
					}]
				}]
			}]
		});

		Ext.apply(this, {
			width : 400,
			title : 'Facility',
			modal : true,
			iconCls : 'feed',
			layout : 'fit',
			items : this.form,
			buttons : [{
				xtype : 'button',
				text : 'Save',
				scope : this,
				handler : this.onSaveClick
			}, {
				xtype : 'button',
				text : 'Cancel',
				scope : this,
				handler : this.destroy
			}]
		});
		this.callParent(arguments);
	},

	setFieldValues : function(values) {
		this.form.getForm().setValues(values);
	},

	onAddressBlur : function(field, eOpts) {
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

	onSaveClick : function() {
		if (this.form.getForm().isValid()) {
			var fieldValues = this.form.getForm().getFieldValues();

			this.form.setLoading({
				msg: 'Saving facility...'
			});
			Ext.Ajax.request({
				url : 'service/facility',
				jsonData : fieldValues,
				success : this.onSaveSuccess,
				failure : this.onSaveFailure,
				scope: this
			});
		}
	},

	onSaveSuccess : function(response){
		this.form.setLoading(false);
		this.fireEvent('nodecreated', this);
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
	}

});
