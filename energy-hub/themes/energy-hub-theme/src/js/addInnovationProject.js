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
    $('#tagProgram').tagsinput('add', 'Clean energy');
    $('#tagProgram').tagsinput('add', 'Power Generator');

    $('#tagInvited').tagsinput('add', '');
    $('#tagInvited').tagsinput('add', 'Imad shreim');
    $('#tagInvited').tagsinput('add', 'Johm Doe');
    $('#tagInvited').tagsinput('add', 'Imad shreimm');
    $('#tagInvited').tagsinput('add', 'Johm Doee');

    $('#tagEmails').tagsinput('add', '');
    $('#tagEmails').tagsinput('add', 'Imad shreim');
    $('#tagEmails').tagsinput('add', 'Johm Doe');

    $('#tagNotTeamEmails').tagsinput('add', '');
    $('#tagNotTeamEmails').tagsinput('add', 'Johm Doe');
    $('#tagNotTeamEmails').tagsinput('add', 'Imad shreim');
});