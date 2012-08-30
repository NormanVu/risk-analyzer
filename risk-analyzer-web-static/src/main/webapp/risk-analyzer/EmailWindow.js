/**
 * @class RiskAnalyzer.EmailWindow
 * @extends Ext.window.Window
 *
 * Shows a dialog for sending simulation report via e-mail.
 * 
 * @constructor
 * Create a new Email Window
 * @param {Object} config The config object
 */

Ext.define('RiskAnalyzer.EmailWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.emailwindow',
  plain: true,

  initComponent: function() {

    this.form = Ext.create('widget.form', {
        bodyPadding: 10,
        border: false,
        unstyled: true,
        items: [
          {
             xtype: 'textfield',
             anchor: '100%',
             fieldLabel: 'To',
             itemId: 'email_to',
             allowBlank: false,
             vtype: 'email'
          },
          {
             xtype: 'textfield',
             anchor: '100%',
             fieldLabel: 'Subject',
             value: '[Risk Analyzer] - Simulation Report',
             itemId: 'email_subject',
             allowBlank: false
           },
           {
             xtype: 'textarea',
             height: 150,
             anchor: '100%',
             fieldLabel: 'Message',
             itemId: 'email_message',
             allowBlank: false
           }
        ]
    });

    Ext.apply(this, {
      width: 500,
      modal: true,
      title: 'Email Simulation Report',
      iconCls: 'feed',
      layout: 'fit',
      items: this.form,
      buttons: [
        {
          xtype: 'button',
          text: 'Send',
          scope: this,
          handler: this.onSendClick
        },
        {
          xtype: 'button',
          text: 'Cancel',
          scope: this,
          handler: this.destroy
        }
      ]
    });

    this.callParent(arguments);
  },

  /**
    * React to the Send button being clicked.
    * @private
    */
  onSendClick: function(){
    if (this.form.getForm().isValid()) {
      var emailTo = this.form.getComponent('email_to').getValue();
      var emailSubject = this.form.getComponent('email_subject').getValue();
      var emailMessage = this.form.getComponent('email_message').getValue();

      this.form.setLoading({
        msg: 'Sending e-mail...'
      });

      Ext.Ajax.request({
        url: 'EmailReport.do',
        params: {
          email_to: emailTo,
          email_subject: emailSubject,
          email_message: emailMessage
        },
        success: this.onEmailReportSuccess,
        failure: this.onEmailReportFailure,
        scope: this
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
