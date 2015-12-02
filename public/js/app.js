(function() {

function clickButton() {
  var r = new XMLHttpRequest();
  r.open("POST", "/test", true);
  var data = { test: "foo" };
  r.send(JSON.stringify(data));
}

function init() {
  var btnEl = document.getElementById("theButton")  ;
  btnEl.onclick = clickButton;
}

window.onload = init;

}());
