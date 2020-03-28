document.getElementById("who-am-i").addEventListener("mouseover", function(event) {
    document.getElementById("who-am-i-description").style.display = "inline-block";
}, false);

document.getElementById("who-am-i").addEventListener("mouseout", function(event) {
    document.getElementById("who-am-i-description").style.display = "none";
}, false);

var timerCount = 0;
var opacity = 0;
const headerTimer = setInterval(function () {
    if (timerCount === 10) {
        clearInterval(headerTimer);
        return;
    }
    ++timerCount;
    document.getElementById("header").style.opacity = opacity;
    opacity += .1;
}, 100);

