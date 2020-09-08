function fetchCurrentWeather() {

    var zip = $('#zipcode').val();

    var unitType = $('#units').val();

    var urlForCurrentWeather = 'https://api.openweathermap.org/data/2.5/weather?zip=' + zip + ',us&units=' + unitType + '&appid=94038e7cd233594669487f2f393ca7dc';

    $.ajax({
        type: 'GET',
        url: urlForCurrentWeather,
        success: function (currentWeather, status) {
            clearErrorMessages();

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

            currentConditions.show();
        },
        error: function () {
            $('#errorMessages')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service for Current Weather. Please try again later.'));
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
            clearErrorMessages();

            var degreeUnit = "";

            if (unitType === "imperial") {
                degreeUnit = "F";
            } else {
                degreeUnit = "C";
            }

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

            //get the date
            var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

            var dateArray = [];
            var highTempArray = [];
            var lowTempArray = [];
            var iconArray = [];
            var conditionArray = [];

            var howManyDays = 5;
            var offset = 0;

            //first day determiner, greater than 4 means today is the first day, less than means tomorrow is the first day
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

                var dateConverted = new Date(fiveDayForecast.list[counter].dt_txt.substring(0, 10));
                var date = dateConverted.getUTCDate();
                var monthAsString = months[dateConverted.getUTCMonth()];
                dateArray.push(date + " " + monthAsString);

                iconArray.push(fiveDayForecast.list[counter].weather[0].icon);
                conditionArray.push(fiveDayForecast.list[counter].weather[0].main);
                offset += 1;

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

                var dateConverted = new Date(fiveDayForecast.list[n + counter + offset].dt_txt.substring(0, 10));
                var date = dateConverted.getUTCDate();
                var monthAsString = months[dateConverted.getUTCMonth()];
                dateArray.push(date + " " + monthAsString);

                iconArray.push(fiveDayForecast.list[counter + 4].weather[0].icon);
                conditionArray.push(fiveDayForecast.list[counter + 4].weather[0].main);

                counter += 8;
            }

            //begin constructing Five Day Forecast Row

            //date, icon, condition, high, low
            $('#date-1').text(dateArray[0]);
            $('#icon-1').attr("src", "http://openweathermap.org/img/w/" + iconArray[0] + ".png");
            $('#condition-1').text(conditionArray[0]);
            $('#high-1').text(highTempArray[0] + " " + degreeUnit);
            $('#low-1').text(lowTempArray[0] + " " + degreeUnit);

            $('#date-2').text(dateArray[1]);
            $('#icon-2').attr("src", "http://openweathermap.org/img/w/" + iconArray[1] + ".png");
            $('#condition-2').text(conditionArray[1]);
            $('#high-2').text(highTempArray[1] + " " + degreeUnit);
            $('#low-2').text(lowTempArray[1] + " " + degreeUnit);

            $('#date-3').text(dateArray[2]);
            $('#icon-3').attr("src", "http://openweathermap.org/img/w/" + iconArray[2] + ".png");
            $('#condition-3').text(conditionArray[2]);
            $('#high-3').text(highTempArray[2] + " " + degreeUnit);
            $('#low-3').text(lowTempArray[2] + " " + degreeUnit);

            $('#date-4').text(dateArray[3]);
            $('#icon-4').attr("src", "http://openweathermap.org/img/w/" + iconArray[3] + ".png");
            $('#condition-4').text(conditionArray[3]);
            $('#high-4').text(highTempArray[3] + " " + degreeUnit);
            $('#low-4').text(lowTempArray[3] + " " + degreeUnit);

            $('#date-5').text(dateArray[4]);
            $('#icon-5').attr("src", "http://openweathermap.org/img/w/" + iconArray[4] + ".png");
            $('#condition-5').text(conditionArray[4]);
            $('#high-5').text(highTempArray[4] + " " + degreeUnit);
            $('#low-5').text(lowTempArray[4] + " " + degreeUnit);


            $('#fiveDay').show();

        },
        error: function () {
            $('#errorMessages')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service for Five Day Forecast. Please try again later.'));
        }
    });

}

function clearErrorMessages() {
    $('#errorMessages').empty();
}

function checkAndDisplayZipcodeValidationErrors(zipcode) {

    clearErrorMessages();

    var errorMessageString = "";
    var isZipcodeValid = true;
    var regExSequence = new RegExp("[0-9]{5}");

    var zip = zipcode.val();

    if (zip != undefined) {
        if (zip === "" || zip.length != 5 || !regExSequence.test(zip)) {
            errorMessageString += "Zipcode: Please enter a 5-digit zip code.";
            $('#errorMessages').append($('<li>').attr({class: 'list-group-item list-group-item-danger'}).text(errorMessageString))
            isZipcodeValid = false;
        }
    } else {
        isZipcodeValid = false;
    }


    return isZipcodeValid;
}

$(document).ready(function () {

    $('#currentConditions').hide();
    $('#fiveDay').hide();
    $('#get-weather-button').click(function (event) {

        if (!checkAndDisplayZipcodeValidationErrors($('#get-weather-form').find('input'))) {
            return false;
        } else {

            fetchCurrentWeather();
            fetchFiveDayWeather();
        }


    });

})

