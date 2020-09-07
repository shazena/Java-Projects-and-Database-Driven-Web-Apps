$(document).ready(function () {
    $('#showAll').on('click', function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/bac/game',
            success: function (gameArray) {
                var allGamesDiv = $('#allGames');

                // go through each of the returned contacts and append the info to the
                //contactsDiv
                // var counter = 0;
                $.each(gameArray, function (index, game) {
                    var gameInfo = '<div class = "col-3" id = "game'+game.gameId+'"><p>';
                    gameInfo += game.gameDescription + '</p>';
                    gameInfo += '<hr><br/></div>';

                    // counter++;

                    allGamesDiv.append(gameInfo);
                    // if (counter % 3 === 0) {
                    //     var nextRow = '<div class = "w-100"></div>'
                    //     allGamesDiv.append(nextRow);
                    // }
                })
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("FAILURE!");
            }
        });
    });
});