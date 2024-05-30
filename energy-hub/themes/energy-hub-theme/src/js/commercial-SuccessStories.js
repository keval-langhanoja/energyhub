var playSuccessStories = document.getElementById("playSuccessStories");
var playerSuccessStories = document.getElementById("playerSuccessStories");
var videoCoverSuccessStories = document.getElementById("video-coverSuccessStories");
playSuccessStories.addEventListener("click", function () {
    playerSuccessStories.src += '&autoplay=1';
    playSuccessStories.style.visibility = "hidden";
    playerSuccessStories.style.display = "inline";
});