var playNetMetering = document.getElementById("playNetMetering");
var playerNetMetering = document.getElementById("playerNetMetering");
var videoCoverNetMetering = document.getElementById("video-coverNetMetering");
playNetMetering.addEventListener("click", function () {
    playerNetMetering.src += '&autoplay=1';
    playNetMetering.style.visibility = "hidden";
    playerNetMetering.style.display = "inline";
});