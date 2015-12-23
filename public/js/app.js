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

function clickSetMotorMaxPosButton() {
  ajaxRequest("POST", "/motor-test", { action: "max" });
}

function clickSetMotorMinPosButton() {
  ajaxRequest("POST", "/motor-test", { action: "min" });
}

function clickStopMotorButton() {
  ajaxRequest("POST", "/motor-test", { action: "stop" });
}

function init() {
  document.getElementById("on").onclick = clickOnButton;
  document.getElementById("off").onclick = clickOffButton;
  document.getElementById("setMotorPosBtn").onclick = clickSetMotorPosButton;
  document.getElementById("setMotorMaxPosBtn").onclick = clickSetMotorMaxPosButton;
  document.getElementById("setMotorMinPosBtn").onclick = clickSetMotorMinPosButton;
  document.getElementById("stopMotorBtn").onclick = clickStopMotorButton;
}

window.onload = init;

}());
