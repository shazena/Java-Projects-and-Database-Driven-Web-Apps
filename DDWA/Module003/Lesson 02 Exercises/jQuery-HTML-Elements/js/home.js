$(document).ready(function () {
    $('h1').addClass('text-center');
    $('h2').addClass('text-center');
    $('.myBannerHeading').addClass('page-header');
    $('.myBannerHeading').removeClass('myBannerHeading');
    $('#yellowHeading').text('Yellow Team');
    $('.orange').css('background-color', 'orange');
    $('.red').css('background-color', 'red');
    $('.blue').css('background-color', 'blue');
    $('.yellow').css('background-color', 'yellow');
    $('#yellowTeamList').html('<li>Joseph Banks</li><li>Simon Jones</li>');
    $('#oops').hide();
    $('#footerPlaceholder').remove();
    $('footer').html('<p>Shazena Khan, email@emailAddress.com</p>');
    $('footer').css({'font-family': 'Courier', 'fontSize': '24px'});
});