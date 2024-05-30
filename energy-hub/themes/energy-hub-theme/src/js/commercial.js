var playCertificate = document.getElementById("playCertificate");
var playerCertificate = document.getElementById("playerCertificate");
var videoCoverCertificate = document.getElementById("video-coverCertificate");
playCertificate.addEventListener("click", function () {
    playerCertificate.src += '&autoplay=1';
    playCertificate.style.visibility = "hidden";
    playerCertificate.style.display = "inline";
});