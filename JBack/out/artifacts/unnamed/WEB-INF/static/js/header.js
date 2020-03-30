document.getElementById("who-am-i").addEventListener("mouseover", function(event) {
    document.getElementById("who-am-i-description").style.display = "inline-block";
}, false);

document.getElementById("who-am-i").addEventListener("mouseout", function(event) {
    document.getElementById("who-am-i-description").style.display = "none";
}, false);

// turn up
var timerCount = 0;
var opacity = 0;
function setOpacity(element, opacity) {
    element.style.opacity = opacity;
}
const headerTimer = setInterval(function () {
    if (timerCount === 10) {
        clearInterval(headerTimer);
        return;
    }
    ++timerCount;
    setOpacity(document.getElementById('header'), opacity);
    opacity += .1;
}, 100);

