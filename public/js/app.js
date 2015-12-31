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

function clickSetMotorSpeedBtn() {
  var motorSpeed = parseInt(document.getElementById("motorSpeed").value, 10);

  if (isNaN(motorSpeed) || motorSpeed < 0) return;

  ajaxRequest("POST", "/motor-test", { motorSpeed: motorSpeed });
}

function clickClockwiseBtn() {
  ajaxRequest("POST", "/motor-test", { action: "ccw" });
}

function clickCounterClockwiseBtn() {
  ajaxRequest("POST", "/motor-test", { action: "cw" });
}

function clickStopMotorButton() {
  ajaxRequest("POST", "/motor-test", { action: "stop" });
}

function clickSetMotorPosBtn() {
  var position = parseInt(document.getElementById("motorPosition").value, 10);

  if (isNaN(position) || position < 0) return;

  ajaxRequest("POST", "/motor-test", { action: "set-position",
                                       position: position });
}

function init() {
  document.getElementById("on").onclick = clickOnButton;
  document.getElementById("off").onclick = clickOffButton;
  document.getElementById("setMotorSpeedBtn").onclick = clickSetMotorSpeedBtn;
  document.getElementById("clockwiseBtn").onclick = clickClockwiseBtn;
  document.getElementById("counterClockwiseBtn").onclick = clickCounterClockwiseBtn;
  document.getElementById("stopMotorBtn").onclick = clickStopMotorButton;
  document.getElementById("setMotorPosBtn").onclick = clickSetMotorPosBtn;
}

window.onload = init;

}());
