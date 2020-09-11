function validateSearchInput(category, searchTerm) {
    if (category === null || searchTerm === "") {
        $('#errors-show-all-table')
            .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Both Search Category and Search Term are required.'));
        return false;
    } else return true;
}

function fetchSearchResults(category, searchTerm) {

    var urlForApi = "https://tsg-dvds.herokuapp.com/dvds/" + category + "/" + searchTerm;

    $.ajax({
        type: 'GET',
        url: urlForApi,
        success: function (dvdArray, status) {
            $('#dvdDetails').empty();
            $('#errors-view-dvd').empty();

            if (dvdArray.length === 0) {
                $('#errors-view-dvd')
                    .append($('<li>')
                        .attr({class: 'list-group-item list-group-item-danger'})
                        .text('Error: No Dvds found for that search term.'));
            }

            var dvdsAsHTML = "";

            $.each(dvdArray, function (index, dvd) {

                dvdsAsHTML += '<h1>' + dvd.title + '</h1><hr/><div class="dvdDetailsExceptTitle"><p>Release Year:</p><p>';
                dvdsAsHTML += dvd.releaseYear + '</p><p>Director:</p><p>';
                dvdsAsHTML += dvd.director + '</p><p>Rating: </p><p>';
                dvdsAsHTML += dvd.rating.toUpperCase() + '</p><p>Notes:</p><p>';
                dvdsAsHTML += dvd.notes + '</p></div>';

            });

            $('#dvdDetails').append(dvdsAsHTML);

            $('#showAllDvds').hide();
            $('#viewDvdContainer').show();
        },
        error: function () {
            $('#errors-show-all-table')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error: That Dvd could not be found.'));
        }
    });

}


function clearCreateDvdForm() {
    $('#create-title').val('');
    $('#create-year').val('');
    $('#create-director').val('');
    $('#create-rating').val('g');
    $('#create-note').val('');
}

$(document).ready(function () {
    //hide the add form
    //hide the edit form
    //hide show one dvd details
    //hide the delete modal
    $('#createDvd').hide();
    $('#editDvd').hide();
    $('#viewDvdContainer').hide();
    $('#deleteOneDvdModal').hide();
    //on click of the search button, validate the inputs
    //check to see if both a search term and category have been chosen
    //if not, show the specific error
    $('#search-button').click(function (event) {
        $('#errors-show-all-table').empty();
        //gather inputs, send to validation function
        //if true, continue, if false, return false to end this process.
        var category = $('#category-selection').val();
        var searchTerm = $('#search-input').val();

        //validation for search inputs
        //if category is null, throw error
        //if search term is a blank string, throw error.
        if (!validateSearchInput(category, searchTerm)) {
            return false;
        }
        fetchSearchResults(category, searchTerm);

        $('#category-selection').val('Select Category');
        $('#search-input').val('');

    });

    $('#viewDvdBackButton').click(function (event) {
        $('#viewDvdContainer').hide();
        $('#showAllDvds').show();
    })


    //on click of the create button,
    //hide the all tables form
    //show the add form

    $('#create-dvd-button').click(function (event) {
        $('#showAllDvds').hide();
        $('#createDvd').show();
    });

    //on click of the cancel button, empty values of all inputs and hide the form, and show the alldvds table
    $('#create-cancel-button').click(function () {

        clearCreateDvdForm();

        $('#createDvd').hide();

        $('#showAllDvds').show();
    });
    //on click of the create dvd button, run ajax call to add to server.
    $('#create-create-dvd-button').click(function (event) {

        $.ajax({
            type: 'POST',
            url: 'https://tsg-dvds.herokuapp.com/dvd',
            data: JSON.stringify({
                title: $('#create-title').val(),
                releaseYear: $('#create-year').val(),
                director: $('#create-director').val(),
                rating: $('#create-rating').val(),
                notes: $('#create-note').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function () {
                $('#errors-create-dvds-form').empty();
                clearCreateDvdForm();
                $('#createDvd').hide();
                $('#showAllDvds').show();
            },
            error: function () {
                $('#errors-create-dvds-form')
                    .append($('<li>')
                        .attr({class: 'list-group-item list-group-item-danger'})
                        .text('Error calling web service. Please try again later.'));
            }
        })
    });

    //after adding to the server, add the movie to the table and load the table. show the table of all dvds again.

    //Q: is there any kind of validation that needs to be ran on the individual entries of the create dvd form?

    //When you click on the edit button, it will hide the all dvds table
    //then we will run the ajax call to the server to get the item by the id number
    //and fill in the inputs of the edit form to match the results of the call
    //then it will show the edit form

    //on clicking the cancel button of the edit form, empty the values of all inputs
    //hide the edit form
    //show the all dvds table div

    //on clicking the save changes button, run ajax call to put to server the new values
    //probably run whatever validation first.
    //you will need to make sure that the user did not enter something blank for the title. Check for whitespace.

    //on clicking the delete button, get the id that is "hidden" in it.
    //make the modal show up, but pass this hidden id to it.
    //DO NOT HIDE THE TABLE!!!!!
    //when you press the button in the modal, run the ajax call with the id to DELETE the dvd.
    //on finishing the call, close the modal.

    // $('#exampleModal').on('show.bs.modal', function (event) {
    //     var button = $(event.relatedTarget); // Button that triggered the modal
    //     var recipient = button.data('whatever'); // Extract info from data-* attributes
    //     // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    //     // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    //     var idToDelete = button.data('idNumber');
    //     var modal = $(this);
    //     $('#idToDeleteSpace').text(recipient);
    //     // modal.find('.modal-title').text('New message to ' + recipient);
    //     // modal.find('.modal-body input').val(recipient);
    //
    //
    // });


    //on clicking the title of the movie
    //hide the showAllDvds table
    //then show the div for the oneDvd.

});

//error code from previous lessons:
// $('#errorMessages')
//     .append($('<li>')
//         .attr({class: 'list-group-item list-group-item-danger'})
//         .text('Error calling web service for Five Day Forecast. Please try again later.'));