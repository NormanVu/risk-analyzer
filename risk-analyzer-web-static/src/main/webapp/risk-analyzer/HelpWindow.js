Ext.define('RiskAnalyzer.HelpWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.helpwindow',
  plain: true,

  initComponent: function() {
    Ext.apply(this, {
        width: 400,
        height: 270,
        modal: true,
        title: 'About',
        iconCls: 'feed',
        layout: 'fit',
        html: '<br/><center><strong>Risk Analyzer - 0.0.1-SNAPSHOT</strong></center>' +
              "<br/><center>Copyright 2010-2011 <a href='mailto:info@scirisk.com'>SciRisk</a>, Inc. All rights reserved.</center>" +
              "<br/><br/><br/><br/><br/><br/><br/><center style='font-size: 9px'>UI Design &amp; Development</center>" +
              "<center style='font-size: 9px'><a href='mailto:pacak.daniel@gmail.com'>Daniel Pacak</a></center>",
        buttons: [{
            xtype: 'button',
            text: 'Close',
            scope: this,
            handler: this.destroy
        }]
    });

    this.callParent(arguments);
  }

});
