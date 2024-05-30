$(document).ready(function () {
	$("a.menuBar").removeClass("active");
	
	if(location.href.includes('home') || location.pathname == '/'){
		$($('.menuBar')[0]).addClass('active')
	}else if(location.href.includes('about')){
		$($('.menuBar')[1]).addClass('active')
	}else if(location.href.includes('usertype')){
		$($('.menuBar')[3]).addClass('active')
	}else if(location.href.includes('contact')){
		$($('.menuBar')[4]).addClass('active')
	}else if(location.href.includes('reports')){
		$($('.menuBar')[5]).addClass('active')
	}else if(location.href.includes('messaging')){
	}else if(!location.href.includes('about') && !location.href.includes('home') 
		&& !location.href.includes('reports') && !location.href.includes('messaging')
		&& !location.href.includes('usertype') && !location.href.includes('contact')){
		$(".programParent").addClass("active");
	}
    $('html, body').animate({ scrollTop: 0 }, '0');
    $('.topBorder').addClass('fillBorder')
    $('.description textarea').css('height', 'calc(100% - ' + $('.description label').height() + 'px - 7px)');
    $('input').prop('required', false);
});

/*active menu item*/
$(window).scroll(function () {
    var scrollDistance = $(window).scrollTop();
    $('section').each(function (i) {
        if ($(this).position().top <= scrollDistance) {
            $('.menu a.active').removeClass('active');
            $('.menu a').eq(i).addClass('active');
        }
    });
}).scroll();

/*language*/
$(document).on('click', '.lang', function () {
    if ($('.langBtn').html() == 'English') {
        $('html').attr('dir', 'ltr').attr('lang', 'en')
        $('.langBtn').html('عربي')
    }
    else {
        $('html').attr('dir', 'rtl').attr('lang', 'ar')
        $('.langBtn').html('English')
    }
});

/*mobile menu*/
function removeHidden(name){
   if($('.'+name).hasClass('hidden'))  $('.'+name).removeClass('hidden');
   else  $('.'+name).addClass('hidden');
}

/*choose user type*/
$(document).on('click', '.userTypesContainer .card', function () {
    $('.selectUser').not($(this).find('.selectUser')).removeClass('userSelected');
    $(this).find('.selectUser').addClass('userSelected');
    if ($('.userSelected').length)
        $('.procced').prop('disabled', false);
    else
        $('.procced').prop('disabled', true);
})

/*validations*/
$('.NumOnly').allowedChars();
$("input").prop('required', true);
$('.alphaOnly').allowedChars({
    allowed: 'abcdefghijklmnopqrstuvwxyz ',
    caseSensitive: false
});

$('.phoneNumberChars').allowedChars({
    allowed: '0123456789',
    caseSensitive: false
});

/*phone validation*/
$('.phone').on('keyup', function () {
    if ($(this).val()) {
        $(this).parent().parent().find('.error').remove()
        if ($(this).val().length != 8) {
            if (screen.width > 767)
                $("<span class='error'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please Enter valid phone number</span>").insertBefore(($(this).parent()));
            else
                $("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please Enter valid phone number</span>").insertAfter(($(this).parent()));
        }
    }
});

/*email verification*/
$('.verifyEmail').on('blur change keyup', function () {
    var regExp = /^([\w\.\+]{1,})([^\W])(@)([\w]{1,})(\.[\w]{1,})+$/;
    if ($(this).val()) {
        $(this).parent().parent().find('.error').remove()
        if (!regExp.test($(this).val())) {
            if (screen.width > 767)
                $("<span class='error'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please Enter valid email address</span>").insertBefore(($(this).parent()));
            else
                $("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please Enter valid email address</span>").insertAfter(($(this).parent()));
        }
    }
});

$('.password').on('blur change keyup', function () {
	if ($(this).val()) {
		$(this).parent().parent().find('.error').remove();
        if ($(this).val().length < 6 || $(this).val().match(/\d/) == null) {
        	if (screen.width > 767)
                $("<span class='error'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please Enter a password with 6+ characters & a Number</span>").insertBefore(($(this).parent()));
            else 
        		$("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please Enter a password with 6+ characters & a Number</span>").insertAfter(($(this).parent()));
        }

	}
});

$('.ConfirmPassword').on('blur change keyup', function () {
	if ($(this).val()) {
		$(this).parent().parent().find('.error').remove();
        if ($(this).val() != $('.password').val()) {
        	if (screen.width > 767)
                $("<span class='error'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Passwords don't match</span>").insertBefore(($(this).parent()));
            else
                $("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Passwords don't match</span>").insertAfter(($(this).parent()));
        }
	}
});


/*required display required error*/
$(document).on('blur change keyup', '.required input, .required textarea, .required select, .required .note-editable', function () {
	if($(this)[0].className == 'note-editable' && $(this)[0].innerHTML == "<p><br></p>") {
		$(this).parent().parent().find('.error').remove()
        if (screen.width > 767)
            $("<span class='error'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>This Field is required</span>").insertBefore(($(this).parent()));
        else
            $("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>This Field is required</span>").insertAfter(($(this).parent()));
    } else if ($(this)[0].className != 'note-editable' && (!$(this).val() || $(this).val()[0] == ' ')) {
        $(this).parent().parent().find('.error').remove()
        if (screen.width > 767)
            $("<span class='error'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>This Field is required</span>").insertBefore(($(this).parent()));
        else
            $("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>This Field is required</span>").insertAfter(($(this).parent()));
    } else{
        if(!$(this).hasClass('phone') && !$(this).hasClass('verifyEmail') 
        		&& !$(this).hasClass('password') && !$(this).hasClass('ConfirmPassword'))
        $(this).parent().parent().find('.error').remove()
    }
});

/*required enable/ disable submit button*/
$(document).on('blur change keyup DOMSubtreeModified', '.required input, .required textarea, .documentsContainer, .required select, .required .note-editable, .validNumber input', function () {
   var isReq = true;
   
	if (!$('.error').length) isReq = false;
    else isReq = true;
	
	if(!isReq) {
	    if ($('.documentsContainer').length) {
	    	if ($('.document').length) isReq = false;
	    	else isReq = true;
	    }
	}
   
    if(!isReq) {
	    $(".validNumber input:not(input[type=hidden])").each(function () {
	        if (!$(this).val()) isReq = true;
	        else isReq = false;
	    })
    }
    
    if(!isReq) {
	    $(".required input:not(input[type=hidden])").each(function () {
	        if (!$(this).val()) isReq = true;
	        else isReq = false;
	    })
    }
    
    if(!isReq) {
    	$('.required .note-editable').each(function () {
            if ($(this)[0].innerHTML == "<p><br></p>" || $(this)[0].innerHTML == "") {
            	isReq = true;
            } else isReq = false;
        })
    }
    
    if(!isReq) {
	    $(".required select").each(function () {
	        if (!$(this).val() || $(this).val()[0] == '' || $(this).val()[0] == ' ') {
	        	isReq = true;
	        } else isReq = false;
	    })
    }
    
//    if(!isReq) {
//	    $(".required select").each(function () {
//	        if (!$(this).val() || $(this).val()[0] == '' || $(this).val()[0] == ' ') {
//	        	isReq = true;
//	        } else isReq = false;
//	    })
//    }
    
    if (isReq) {
        $('.submit').prop('disabled', true);
    }else $('.submit').prop('disabled', false);
});

$('.documentsContainer').bind('DOMSubtreeModified', function () {
    if (!$('.document').length) {
        $('.documentsContainer').next('.error').remove()
        $("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>This Field is required</span>").insertAfter(($('.documentsContainer')));
    }
    else
        $('.documentsContainer').next('.error').remove()

});
$(document).on('click', '.submit', function () {
    $('.required input, .required textarea, .required select, .required .note-editable').trigger('blur');
    $('.phone, .verifyEmail, .password, .ConfirmPassword').trigger('blur');
    $('.documentsContainer').trigger('DOMSubtreeModified');
})

/*clear form fields*/
$(document).on('click', '.reset', function () {
    $(this).parent().parent().find('input').val('');
})
$(document).on('click', '.document .remove', function(){
    $(this).parent().remove()
})

function enhanceScroll(item, pageTitle, lang, nav_item_url, is_child) {
	if(pageTitle.toLowerCase() == "home" && !is_child){
		if(item.href.includes("reports")){
			window.location.href = "/reports";
		} else if(item.href.includes("getinvolved")){
			window.location.href = "/usertype";
		} else if(item.href.includes("messaging")){
			window.location.href = "/messaging";
		}
		else window.location.href = item.href;
	} else if(pageTitle.toLowerCase() != "home" && !is_child){
		if(item.href.includes("getinvolved")){
			window.location.href = "/usertype";
		} else if(item.href.includes("contact")){
			window.location.href = item.href;
		} else if(item.href.includes("about")){
			window.location.href = nav_item_url;
		} else if(item.href.includes("home")) {
			window.location.href = nav_item_url;
		} else if(item.href.includes("reports")){
			window.location.href = "/reports";
		} else if(item.href.includes("messaging")){
			window.location.href = "/messaging";
		}
	} else  window.location.href = nav_item_url;
	
	$("a.menuBar").removeClass("active");
	if(is_child || pageTitle.toLowerCase().indexOf("commercial") != -1) $(".programParent").addClass("active");
	else $(item).addClass("active");
	
	if(pageTitle.toLowerCase() == "about") $(item).addClass("active");
	if(pageTitle.toLowerCase() == "usertype") $(item).addClass("active");
	if(pageTitle.toLowerCase() == "reports") $(item).addClass("active");
}
