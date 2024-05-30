$(document).ready(function () {




    $(document).on("click", ".btnprog", function () {

        if ($('.trProgress')[0]) {
            console.log('clicked');
            var $tr = $('.trProgress')[0];
            $tr.remove();
        } else {
            var newRow = $('<tr class="trProgress">');
            var cols = "";

            cols += '<td class="px-0" colspan="5">' +
                '<div class="stepper-wrapper">' +
                '<div class="stepper-item completed">' +
                '<div class=""></div>' +
                '<div class="step-counter"></div>' +
                '<div class="step-name">' +
                '<p class="titleSpan">Project submitted</p>' +
                '<p class="dateSpan">20 Augest 2021</p>' +
                '</div>' +
                '</div>' +
                '<div class="stepper-item active">' +
                '<div class="selector"></div>' +
                '<div class="step-counter selected"></div>' +
                '<div class="step-name">' +
                '<p class="titleSpan">Under review</p>' +
                '<p class="dateSpan">22 Augest 2021</p>' +
                '</div>' +
                '</div>' +
                '<div class="stepper-item">' +
                '<div class=""></div>' +
                '<div class="step-counter"></div>' +
                '<div class="step-name">' +
                '<p class="notCompleted">Published successfully</p>' +
                '<p class="dateSpan"></p>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</td>';

            newRow.append(cols);
            newRow.insertAfter($(this).parents().closest('tr'));

        }
    });
});