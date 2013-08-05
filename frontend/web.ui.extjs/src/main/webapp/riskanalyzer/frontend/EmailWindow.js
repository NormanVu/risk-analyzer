Ext.define('riskanalyzer.frontend.EmailWindow', {

	extend: 'Ext.window.Window',

	alias: 'widget.emailwindow',
	plain: true,

	initComponent: function() {
		var me = this;

		me.form = Ext.create('widget.form', {
			bodyPadding: 10,
			border: false,
			unstyled: true,
			items: [{
				xtype: 'textfield',
				anchor: '100%',
				fieldLabel: 'To',
				itemId: 'email_to',
				allowBlank: false,
				vtype: 'email'
			}, {
				xtype: 'textfield',
				anchor: '100%',
				fieldLabel: 'Subject',
				value: '[Risk Analyzer] - Simulation Report',
				itemId: 'email_subject',
				allowBlank: false
			}, {
				xtype: 'textarea',
				height: 150,
				anchor: '100%',
				fieldLabel: 'Message',
				itemId: 'email_message',
				allowBlank: false
			}]
		});

		Ext.apply(me, {
			width: 500,
			modal: true,
			title: 'Email Frequency Distribution',
			iconCls: 'feed',
			layout: 'fit',
			items: me.form,
			buttons: [{
				xtype: 'button',
				text: 'Send',
				scope: me,
				handler: me.onSendClick
			}, {
				xtype: 'button',
				text: 'Cancel',
				scope: me,
				handler: me.destroy
			}]
		});

		this.callParent(arguments);
	},

	onSendClick: function() {
		var me = this;
		if (me.form.getForm().isValid()) {
			var emailTo = me.form.getComponent('email_to').getValue();
			var emailSubject = me.form.getComponent('email_subject').getValue();
			var emailMessage = me.form.getComponent('email_message').getValue();

			me.form.setLoading({
				msg: 'Sending e-mail...'
			});

			Ext.Ajax.request({
				url: 'EmailReport.do',
				params: {
					email_to: emailTo,
					email_subject: emailSubject,
					email_message: emailMessage
				},
				success: me.onEmailReportSuccess,
				failure: me.onEmailReportFailure,
				scope: me
			});
		}
	},

	onEmailReportSuccess: function(response) {
		this.form.setLoading(false);
		this.destroy();
	},

	onEmailReportFailure: function() {
		this.form.setLoading(false);
		Ext.MessageBox.show({
			title: 'Application Error',
			msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.ERROR
		});
	}

});