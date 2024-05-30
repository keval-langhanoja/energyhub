var playLGBC = document.getElementById("playLGBC");
var playerLGBC = document.getElementById("playerLGBC");
var videoCoverLGBC = document.getElementById("video-coverLGBC");
playLGBC.addEventListener("click", function () {
    playerLGBC.src += '&autoplay=1';
    playLGBC.style.visibility = "hidden";
    playerLGBC.style.display = "inline";
});