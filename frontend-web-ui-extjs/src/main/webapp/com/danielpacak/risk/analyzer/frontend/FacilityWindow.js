/** The UI component for editing facility properties. */
Ext.define('com.danielpacak.risk.analyzer.frontend.FacilityWindow', {

	extend : 'Ext.window.Window',

	alias : 'widget.window.facility',
	
	_controller : undefined,
	_form : undefined,
	
	constructor : function(config) {
		var me = this;
		me._controller = config.controller;
		me.callParent(arguments);
	},

	initComponent : function() {
		var me = this;

		me._form = Ext.create('widget.form', {
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
					id : 'facilityKind',
					name : 'kind',
					fieldLabel : 'Kind',
					xtype : 'combo',
					store : [['company', 'Company'], ['supplier', 'Supplier']],
					editable : false,
					allowBlank : false
				}, {
					id : 'facilityName',
					name : 'name',
					fieldLabel : 'Name',
					xtype : 'textfield',
					allowBlank : false
				}, {
					id : 'facilityDescription',
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
					id : 'facilityAddress',
					name : 'address',
					fieldLabel : 'Address',
					xtype : 'textfield',
					allowBlank : false,
					listeners : {
						scope : me._controller,
						blur : me._controller.onAddressChange,
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
						id : 'facilityTypeIndependent',
						inputValue: 'independent',
						boxLabel: 'Independent'
					}, {
						id : 'facilityTypeCorrelated',
						inputValue: 'correlated',
						boxLabel: 'Correlated'
					}]
				}]
			}]
		});

		
		
		
		var saveButton = {
			id : 'facilityDialogSaveButton',
			xtype : 'button',
			text : 'Save',
			scope : me._controller,
			handler : me._controller.onSaveButtonClick
		}; 
		var cancelButton = {
			id : 'facilityDialogCloseButton',
			xtype : 'button',
			text : 'Cancel',
			scope : me._controller,
			handler : me._controller.onCancelButtonClick
		};

		Ext.apply(me, {
			width : 400,
			title : 'Facility',
			modal : true,
			items : me._form,
			buttons : [ saveButton, cancelButton ]
		});

		me.callParent(arguments);
	}

});
