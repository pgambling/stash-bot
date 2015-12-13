(function() {

function ajaxRequest(method, url, data) {
  var r = new XMLHttpRequest();
  r.open(method, url, true);
  r.setRequestHeader("Content-Type", "application/json");
  r.send(JSON.stringify(data));
}

function sendLedRequest(ledState) {
  ajaxRequest("POST", "/led-test", { ledState: ledState });
}

function clickOnButton() {
  sendLedRequest(true);
}

function clickOffButton() {
  sendLedRequest(false);
}

function clickSetMotorPosButton() {
  var motorDegree = parseInt(document.getElementById("motorDeg").value, 10);

  if (isNaN(motorDegree) || motorDegree < 0) return;

  ajaxRequest("POST", "/motor-test", { motorDegree: motorDegree });
}

function init() {
  document.getElementById("on").onclick = clickOnButton;
  document.getElementById("off").onclick = clickOffButton;
  document.getElementById("setMotorPosBtn").onclick = clickSetMotorPosButton;
}

window.onload = init;

}());
