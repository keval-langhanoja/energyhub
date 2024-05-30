const realFileBtn = document.getElementById("real-file");
const customBtn = document.getElementById("custom-button");
const customTxt = document.getElementById("custom-text");

customBtn.addEventListener("click", function () {
    realFileBtn.click();
});
realFileBtn.addEventListener("change", function () {
    if (realFileBtn.value) {
        customTxt.innerHTML = realFileBtn.value.match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
    } else {
        customTxt.innerHTML = "No file chosen";
    }
});



$(document).ready(function () {

    var img = document.createElement("img");
    img.src = "assests/images/img/user.jpg";

    $('#tagProgram').tagsinput('add', '');
    $('#tagProgram').tagsinput('add', 'Power');
    $('#tagProgram').tagsinput('add', 'Solar');

    $('.dropdown-menu').on( 'click', 'a', function() {
        var text = $(this).html();
        var htmlText = text + ' <span class="dropdown-image"> <img src="assests/images/dropdown-arrow.svg"></span>';
        $(this).closest('.dropdown').find('.dropdown-toggle').html(htmlText);
    });
});