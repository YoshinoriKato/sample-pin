(function() {
  var Options = Class.create({
    initialize: function() {
      this.bg = chrome.extension.getBackgroundPage().bg;
      window.addEventListener("load", function(evt) {
        this.start();
      }.bind(this));
    },
    start: function() {
      this.assignMessages();
      this.assignEventHandlers();
      this.restoreConfigurations();
    },
    assignMessages: function() {
      var hash = {
        "opt***": "opt***"
      };
      for (var key in hash) {
        $(key).innerHTML = chrome.i18n.getMessage(hash[key]);
      }
    },
    assignEventHandlers: function() {
      $("***").onclick = this.onClickZZZZ.bind(this);
    },
    restoreConfigurations: function() {
      $("***").value = this.bg.getZZZZConfig();
    },
    onClickZZZZ: function(evt) {
      var value = $("***").value;
      this.bg.setZZZZConfig(value);
    }
  });
  new Options();
})();