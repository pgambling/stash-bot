(function() {

function sendLedRequest(ledState) {
  var r = new XMLHttpRequest();
  r.open("POST", "/test", true);
  r.setRequestHeader("Content-Type", "application/json");
  var data = { ledState: ledState };
  r.send(JSON.stringify(data));
}

function clickOnButton() {
  sendLedRequest(true);
}

function clickOffButton() {
  sendLedRequest(false);
}

function init() {
  document.getElementById("on").onclick = clickOnButton;
  document.getElementById("off").onclick = clickOffButton;
}

window.onload = init;

}());
