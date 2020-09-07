$(document).ready(function () {
    $("#akronInfoDiv").hide();
    $("#minneapolisInfoDiv").hide();
    $("#louisvilleInfoDiv").hide();
    $("#akronButton").on("click", function () {
        $("#akronInfoDiv").toggle();
        $("#akronWeather").hide();
    });
    $("#minneapolisButton").on("click", function () {
        $("#minneapolisInfoDiv").toggle();
        $("#minneapolisWeather").hide();
    });
    $("#louisvilleButton").on("click", function () {
        $("#louisvilleInfoDiv").toggle();
        $("#louisvilleWeather").hide();
    });
    $("#akronWeatherButton").on("click", function () {
        $("#akronWeather").toggle();
    });
    $("#minneapolisWeatherButton").on("click", function () {
        $("#minneapolisWeather").toggle();
    });
    $("#louisvilleWeatherButton").on("click", function () {
        $("#louisvilleWeather").toggle();
    });

    $("td").hover(function () {
            // $(this).parent().addClass("nonHeaderRow");
            $(this).parent().css("background-color", "whiteSmoke");
        },
        function () {
            // $(this).parent().removeClass("nonHeaderRow");
            $(this).parent().css("background-color", "");
        });

});