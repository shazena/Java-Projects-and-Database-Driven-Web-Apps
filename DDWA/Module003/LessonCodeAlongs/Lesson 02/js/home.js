$(document).ready(function () {
    alert("Ready to go!!!");
    $("#emptyDiv").css({
        'width': '400',
        'background-color': 'CornflowerBlue'
    });
    $("H1").css('color', 'blue');
    $("#first").hide();
    $("#emptyDiv").append("<p>A new paragraph of text.</p>");
    $("#third").remove();
});
