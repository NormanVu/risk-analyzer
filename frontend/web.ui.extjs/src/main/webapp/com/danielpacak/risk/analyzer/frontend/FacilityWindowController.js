/** Creates FacilityWindow window and handles all the interaction. */
Ext.define('com.danielpacak.risk.analyzer.frontend.FacilityWindowController', {
	
	_window : undefined,
	
	constructor : function() {
		var me = this;
		me._window = Ext.create('com.danielpacak.risk.analyzer.frontend.FacilityWindow', {
			controller : me
		});
		me.callParent(arguments);
	},
	
	/** Display the managed window. */
	display : function() {
		this._window.show();
	},
	
	/** Callback for the save button click. */
	onSaveButtonClick : function() {
		var me = this;
		
		if (me._window._form.getForm().isValid()) {
			var facility = me._window._form.getForm().getFieldValues();

			me._window._form.setLoading({
				msg: 'Saving facility...'
			});
			Ext.Ajax.request({
				url : 'service/facility',
				jsonData : facility,
				scope: me,
				success : me.onSaveSuccess,
				failure : me.onSaveFailure,
			});
		}
	},

	onSaveSuccess : function(response){
		var me = this;
		me._window._form.setLoading(false);
		me._window.destroy();
	},

	onSaveFailure : function(){
		this._window._form.setLoading(false);
		Ext.MessageBox.show({
			title: 'Application Error',
			msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.ERROR
		});
	},
	
	onAddressChange : function(field, eOpts) {
		var form = this._window._form;
		var fieldValues = form.getForm().getFieldValues();
		var address = fieldValues.address;
		form.setLoading({msg: 'Validating address...'});
		try {
			new google.maps.Geocoder().geocode({'address': address}, function(results, status) {
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
		} catch (error) {
			alert('cannot find location...' + error);
			form.setLoading(false);
		}
	},

	/** Callback for the cancel button click. */
	onCancelButtonClick : function() {
		this._window.destroy();
	}

});
