// window for creating node
/**
 * @class FeedViewer.NodeWindow
 * @extends Ext.window.Window
 *
 * Shows a dialog for creating and validating a new node.
 * 
 * @constructor
 * Create a new Node Window
 * @param {Object} config The config object
 */

Ext.define('RiskAnalyzer.LoginWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.loginwindow',

  plain: true,

  initComponent: function(){
        this.addEvents(
            /**
             * @event nodecreated
             * @param {FeedViewer.NodeWindow} this
             * @param {String} title
             * @param {String} url
             * @param {String} description
             */
            'loginsucceed'
        );
        
        this.form = Ext.create('widget.form', {
            bodyPadding: '12 10 10',
            border: false,
            unstyled: true,
            items: [{
                anchor: '100%',
                itemId: 'username',
                fieldLabel: 'Username',
                //msgTarget: 'under',
                xtype: 'textfield',
                allowBlank: false
            },
            {anchor: '100%',
            	itemId: 'password',
            	fieldLabel: 'Password',
            	//msgTarget: 'under',
            	xtype: 'textfield',
            	inputType: 'password',
            	allowBlank: false            	
            }]
        });
        
        
        Ext.apply(this, {
            width: 300,
            title: 'Login to Risk Analyzer',
            modal: true,
            iconCls: 'feed',
            layout: 'fit',
            items: this.form,
            buttons: [{
                xtype: 'button',
                text: 'Login',
                scope: this,
                handler: this.onLoginClick
                
            }, {
                xtype: 'button',
                text: 'Cancel'
                //scope: this,
                //handler: this.destroy
            }]
        });
        this.callParent(arguments);
    },
    
    /**
     * React to the add button being clicked.
     * @private
     */
    onLoginClick: function() {
    	console.log('Login button clicked..');
    	console.log('form is valid? ' + this.form.getForm().isValid());
    	if (this.form.getForm().isValid()) {
        var username = this.form.getComponent('username').getValue();
        var password = this.form.getComponent('password').getValue();
        
        Ext.Ajax.request(
          {
            url: 'Login.do',
            params: {
            	username: username,
            	password: password
            },
            scope: this,
            success: this.onLoginSuccess
          }
        );
    	}
        // TODO AJAX CALL TO REALLY LOGIN
        //this.fireEvent('loginsucceeded', this);
        //this.destroy();
    },
    
    onLoginSuccess: function(response) {
    	console.log("onLoginSuccess(" + response + ")");
    	var text = response.responseText;
    	console.log("responseText: " + response);
    	this.fireEvent('loginsucceeded', this);
    	this.destroy();
    },
    
    /**
     * React to the feed validation passing
     * @private
     * @param {Object} response The response object
     */
    addNodeSuccess: function(response){
        this.form.setLoading(false);

    },
    
    /**
     * React to the feed validation failing
     * @private
     */
    addNodeFailure: function(){
        this.form.setLoading(false);
        //this.form.getComponent('feed').markInvalid('The URL specified is not a valid RSS2 feed.');
        alert("error!");
    }
});
