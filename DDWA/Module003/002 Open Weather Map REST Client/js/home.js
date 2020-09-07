function fetchCurrentWeather() {

    var zip = $('#zipcode').val();

    var unitType = $('#units').val();

    var urlForCurrentWeather = 'https://api.openweathermap.org/data/2.5/weather?zip=' + zip + ',us&units=' + unitType + '&appid=94038e7cd233594669487f2f393ca7dc';

    $.ajax({
        type: 'GET',
        url: urlForCurrentWeather,
        success: function (currentWeather, status) {

            var currentConditions = $('#currentConditions');
            currentConditions.empty();

            var header = '<div class = "col-12"><hr/>';
            header += '<h2> Current Conditions in ' + currentWeather.name + ' </h2>';
            header += '</div>';
            currentConditions.append(header);

            var firstColumn = '<div class = "col-6" style = "margin-bottom: auto; margin-top: auto">';
            firstColumn += '<table><tbody><tr><td><img src = "';
            firstColumn += 'http://openweathermap.org/img/w/' + currentWeather.weather[0].icon + '.png" class = "float-left" > ';
            firstColumn += '</td><td><p>' + currentWeather.weather[0].main + ': ' + currentWeather.weather[0].description;
            firstColumn += '</p></td></tr></tbody></table></div>';
            currentConditions.append(firstColumn);

            var degreeUnit = "";
            var speedUnit = "";

            if (unitType === "imperial") {
                degreeUnit = "F";
                speedUnit = "mi";
            } else {
                degreeUnit = "C";
                speedUnit = "km";
            }

            var secondColumn = '<div class = "col-6" style = "margin-bottom: auto; margin-top: auto">';
            secondColumn += '<p>Temperature: ' + currentWeather.main.temp + ' ' + degreeUnit + '</p>';
            secondColumn += '<p>Humidity: ' + currentWeather.main.humidity + '%</p>';
            secondColumn += '<p>Wind: ' + currentWeather.wind.speed + ' ' + speedUnit + '/hr</p>';
            secondColumn += '</div>';

            currentConditions.append(secondColumn);
        },
        error: function () {
            $('#errorMessages')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });


}

function fetchFiveDayWeather() {

    var zip = $('#zipcode').val();

    var unitType = $('#units').val();

    var urlForFiveDayWeather = 'https://api.openweathermap.org/data/2.5/forecast?zip=' + zip + ',us&units=' + unitType + '&appid=94038e7cd233594669487f2f393ca7dc';

    $.ajax({
        type: 'GET',
        url: urlForFiveDayWeather,
        success: function (fiveDayForecast, status) {
            //read the dates for 0-7, counter if they are for the same date.
            //get first date, add on to counter if the date is the same.
            //if counter > 4, start with today
            //if counter < 4, start with tomorrow
            var counter = 0;
            var referenceDate = fiveDayForecast.list[counter].dt_txt;
            for (let i = 0; i < 7; i++) {
                if (fiveDayForecast.list[i].dt_txt.substring(0, 10) === referenceDate.substring(0, 10)) {
                    counter++;
                }
            }

            var highTempArray = [];
            var lowTempArray = [];

            var howManyDays = 4;

            //first day temp determiner
            if (counter > 4) {
                howManyDays--;
                //show today as the first day
                //put all the highs of today in an array
                var temporaryArrayOfHighTemperatures = [];
                var temporaryArrayOfLowTemperatures = [];
                for (let j = 0; j < counter; j++) {
                    temporaryArrayOfHighTemperatures.push(fiveDayForecast.list[j].main.temp_max);
                    temporaryArrayOfLowTemperatures.push(fiveDayForecast.list[j].main.temp_min);
                }
                //find the maximum of this array and add it to the main High & Low Temperature Array
                highTempArray.push(Math.max(...temporaryArrayOfHighTemperatures));
                lowTempArray.push(Math.min(...temporaryArrayOfLowTemperatures));

            }

            //show tomorrow as the first day,
            //get the date of the counter and then continue onwards, every 7 is a new day

            for (let n = 0; n < howManyDays; n++) {
                var temporaryArrayOfHighTemperatures = [];
                var temporaryArrayOfLowTemperatures = [];
                for (let m = counter; m < counter + 7; m++) {
                    //put all the highs of today in an array
                    temporaryArrayOfHighTemperatures.push(fiveDayForecast.list[m].main.temp_max);
                    temporaryArrayOfLowTemperatures.push(fiveDayForecast.list[m].main.temp_min);
                }
                //find the maximum of this array and add it to the main High & Low Temperature Array
                highTempArray.push(Math.max(...temporaryArrayOfHighTemperatures));
                lowTempArray.push(Math.min(...temporaryArrayOfLowTemperatures));
            }
            $('#firstHigh').text(highTempArray[0]);
            $('#firstLow').text(lowTempArray[0]);


        },
        error: function () {
            $('#errorMessages')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });

}

$(document).ready(function () {

    // $('#currentConditions').hide();
    // $('#fiveDay').hide();
    $('#get-weather-button').click(function (event) {
        //TODO validate zip code using HTML and JS
        fetchCurrentWeather();
        fetchFiveDayWeather();
        //remember to clear the stuff before starting another call

    });

})

