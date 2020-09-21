let ds = new DataService();

function formatViewOneDvd(dvd) {
    var dvdAsHTML = "";
    dvdAsHTML += '<h1>' + dvd.title + '</h1><hr/><div class="dvdDetailsExceptTitle"><p>Release Year:</p><p>';
    dvdAsHTML += dvd.releaseYear + '</p><p>Director:</p><p>';
    dvdAsHTML += dvd.director + '</p><p>Rating: </p><p>';
    dvdAsHTML += dvd.rating.toUpperCase() + '</p><p>Notes:</p><p>';
    dvdAsHTML += dvd.notes + '</p></div>';
    return dvdAsHTML;
}


function formatRowOfDvdTable(dvd) {
    var rowAsString = "";
    rowAsString += '<tr><td><a href = "#" onclick="viewThisDvd(';
    rowAsString += dvd.id;
    rowAsString += ')">';
    rowAsString += dvd.title;
    rowAsString += '</a></td><td>';
    rowAsString += dvd.releaseYear;
    rowAsString += '</td><td>';
    rowAsString += dvd.director;
    rowAsString += '</td><td>';
    // rowAsString += dvd.rating.toUpperCase();
    rowAsString += dvd.rating;
    rowAsString += '</td><td><a href = "#" onclick="editDvd(';
    // tableBodyHTML += dvd.id + ')">Edit</a> | <a href = "#"  data-toggle = "modal" data-target = "#deleteModal" data-dvdId="';
    // tableBodyHTML += dvd.id + '">Delete</a></td></tr>';
    rowAsString += dvd.id + ')">Edit</a> | <a href = "#" onclick="deleteDvd(';
    rowAsString += dvd.id + ')">Delete</a></td></tr>';
    return rowAsString;
}

function handleAllDvdsTableErrors() {
    $('#errors-show-all-table')
        .append($('<li>')
            .attr({class: 'list-group-item list-group-item-danger'})
            .text('Error calling Web service. Try again later.'));
}

function handleViewDvdErrors() {
    $('#errors-view-dvd')
        .append($('<li>')
            .attr({class: 'list-group-item list-group-item-danger'})
            .text('Error: That Dvd could not be found.'));
}

function loadAllDvds(dvdArray) {

    $('#all-dvds-body').empty();

    //for each dvd, add it to the table.
    for (let i = 0; i < dvdArray.length; i++) {
        const dvd = dvdArray[i];
        $('#all-dvds-body').append(formatRowOfDvdTable(dvd));
    }
}


function validateSearchInput(category, searchTerm) {
    if (category === null || searchTerm === "") {
        $('#errors-show-all-table')
            .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Both Search Category and Search Term are required.'));
        return false;
    } else return true;
}

function validateTitleAndYear(title, year) {
    var errorCounter = 0;
    var errorsAsHTML = "";
    if (!/\S/.test(title)) {
        errorsAsHTML += '<li class="list-group-item list-group-item-danger">Please enter a title for the DVD.</li>';
        errorCounter++;
    }

    var today = new Date();
    var regExSequence = new RegExp("[0-9]{4}");


    if (!regExSequence.test(year) || year.length != 4 || year > today.getFullYear()) {
        errorsAsHTML += '<li class="list-group-item list-group-item-danger">Please enter a 4-digit year.</li>';
        errorCounter++;
    }

    return errorsAsHTML;

}


function getSearchResults(category, searchTerm) {

    var urlForApi = "https://tsg-dvds.herokuapp.com/dvds/" + category + "/" + searchTerm;

    $.ajax({
        method: 'GET',
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


            $.each(dvdArray, function (dvd) {

                var dvdsAsHTML = formatViewOneDvd(dvd);

                $('#dvdDetails').append(dvdsAsHTML);

            });


            $('#showAllDvds').hide();
            $('#viewDvdContainer').show();
        },
        error: function () {
            handleViewDvdErrors();
        }
    });

}

function clearEditDvdForm() {
    $('#edit-dvd-id').val('');
    $('#edit-title').val('');
    $('#edit-year').val('');
    $('#edit-director').val('');
    $('#edit-rating').val('g');
    $('#edit-note').val('');
}

function createDvdOnServer() {
    $.ajax({
        method: 'POST',
        url: 'https://tsg-dvds.herokuapp.com/dvd',
        data: JSON.stringify({
            title: $('#create-title').val(),
            releaseYear: $('#create-year').val(),
            director: $('#create-director').val(),
            rating: $('#create-rating').val().toUpperCase(),
            notes: $('#create-note').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },

        'dataType': 'json',
        success: function () {
            $('#errors-create-dvds-form').empty();
            let form = $('#theAddForm');
            $(form)[0].reset();
            $('#createDvd').hide();
            loadAllDvds();
            $('#showAllDvds').show();
        },
        error: function () {
            $('#errors-create-dvds-form')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    })
}


function viewThisDvd(id) {
    //run ajax call to get dvd by id
    //hide table of all dvds
    //show the details of one dvd.
    $.ajax({
        method: 'GET',
        url: 'https://tsg-dvds.herokuapp.com/dvd/' + id,
        success: function (dvd) {
            $('#errors-view-dvd').empty();
            $('#dvdDetails').empty();

            var dvdAsHTML = formatViewOneDvd(dvd);


            $('#dvdDetails').append(dvdAsHTML);

            $('#showAllDvds').hide();
            $('#viewDvdContainer').show();
        },
        error: function () {
            $('#errors-view-dvd')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Try again later.'));
        }
    });
}

function editDvd(id) {
    //When you click on the edit button, it will hide the all dvds table
    //then we will run the ajax call to the server to get the item by the id number
    //and fill in the inputs of the edit form to match the results of the call
    //then it will show the edit form

    $('#showAllDvds').hide();
    $.ajax({
        method: 'GET',
        url: 'https://tsg-dvds.herokuapp.com/dvd/' + id,
        success: function (dvd) {
            $('#errors-edit-dvds-form').empty();

            $('#dvd-title-to-edit').text(dvd.title);

            $('#edit-dvd-id').val(id);
            $('#edit-title').val(dvd.title);
            $('#edit-year').val(dvd.releaseYear);
            $('#edit-director').val(dvd.director);
            $('#edit-rating').val(dvd.rating.toLowerCase());
            $('#edit-note').val(dvd.notes);

            $('#editDvd').show();
        },
        error: function () {
            $('#errors-edit-dvds-form')
                .append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Try again later.'));
        }
    })


    //this needs to show the edit form
    //hide the table
    //put the id in the hidden input so it can be accessed.
}

function deleteDvd(id) {
    $('#deleteModal').modal('show');
    $('#deleteModal').find('#delete-dvd-id').val(id);
    //bring up the modal
    //put the hidden id in the modal
    //////then the okay button in the modal will run the delete.

}

$(document).ready(function () {
    //hide the add form
    //hide the edit form
    //hide show one dvd details
    //hide the delete modal
    $('#createDvd').hide();
    $('#editDvd').hide();
    $('#viewDvdContainer').hide();
    // $('#deleteOneDvdModal').hide();

    ds.getDvds(loadAllDvds, handleAllDvdsTableErrors);


    //on click of the search button, validate the inputs
    //check to see if both a search term and category have been chosen
    //if not, show the specific error

    $(document).on('click', '#search-button', onClickSearchButton);

    function onClickSearchButton() {
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

        ds.getDvdBySearchTerm(category, searchTerm,function (){
            $('#dvdDetails').empty();
            $('#errors-view-dvd').empty();

            if (dvdArray.length === 0) {
                $('#errors-view-dvd')
                    .append($('<li>')
                        .attr({class: 'list-group-item list-group-item-danger'})
                        .text('Error: No Dvds found for that search term.'));
            }


            $.each(dvdArray, function (dvd) {

                var dvdsAsHTML = formatViewOneDvd(dvd);

                $('#dvdDetails').append(dvdsAsHTML);

            });


            $('#showAllDvds').hide();
            $('#viewDvdContainer').show();
        }, handleViewDvdErrors)
    }


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
        getSearchResults(category, searchTerm);

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
    $('#create-cancel-button').click(function (e) {

        let form = $('#theAddForm');
        $(form)[0].reset();

        $('#createDvd').hide();
        $('#showAllDvds').show();
    });

    //on click of the create dvd button, run ajax call to add to server.
    $('#create-create-dvd-button').click(function (event) {

        $('#errors-create-dvds-form').empty();

        var title = $('#create-title').val();
        var year = $('#create-year').val();
        var validation = validateTitleAndYear(title, year);
        if (validation != "") {
            $('#errors-create-dvds-form').html(validation);
            return false;
        }

        createDvdOnServer();
        //after adding to the server, add the movie to the table and load the table. show the table of all dvds again.

    });


    //on clicking the cancel button of the edit form, empty the values of all inputs
    //hide the edit form
    //show the all dvds table div
    $('#edit-cancel-button').click(function () {
        clearEditDvdForm();
        $('#editDvd').hide();
        $('#showAllDvds').show();
    });

    //on clicking the save changes button (get hidden id), run ajax call to PUT to server the new values
    //probably run whatever validation first.
    //you will need to make sure that the user did not enter something blank for the title. Check for whitespace!!!
    $('#edit-save-dvd-button').click(function () {
        var id = $('#edit-dvd-id').val();
        var title = $('#edit-title').val();
        var year = $('#edit-year').val();
        var director = $('#edit-director').val();
        var rating = $('#edit-rating').val().toUpperCase();
        var notes = $('#edit-note').val();

        var validation = validateTitleAndYear(title, year);

        if (validation != "") {
            $('#errors-edit-dvds-form').html(validation);
            return false;
        } else {

            $.ajax({
                method: 'PUT',
                url: 'https://tsg-dvds.herokuapp.com/dvd/' + id,
                data: JSON.stringify({
                    id: id,
                    title: title,
                    releaseYear: year,
                    director: director,
                    rating: rating,
                    notes: notes
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'dataType': 'json',
                success: function () {
                    // alert('This is the id number of that dvd ' + id);
                    // $('#errors-edit-dvds-form').empty();
                    // clearEditDvdForm();
                    // loadAllDvds();
                    // $('#editDvd').hide();
                    // $('#showAllDvds').show();
                },
                error: function () {
                    $('#errors-edit-dvds-form')
                        .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service. Please try again later.'));
                    return false;
                }
            })

            $('#errors-edit-dvds-form').empty();
            clearEditDvdForm();
            var millisecondsToWait = 100;
            setTimeout(function () {
                loadAllDvds();
                $('#editDvd').hide();
                $('#showAllDvds').show();
            }, millisecondsToWait);

        }

    });

    //on clicking the delete button, get the id that is "hidden" in it.
    //make the modal show up, but pass this hidden id to it.
    //DO NOT HIDE THE TABLE!!!!!
    //when you press the button in the modal, run the ajax call with the id to DELETE the dvd.
    //on finishing the call, close the modal.


    $('#delete-yes-button').click(function (event) {
        var id = $('#delete-dvd-id').val();
        $.ajax({
            method: 'DELETE',
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + id,
            success: function () {
                // $('#deleteModal').modal('hide');
                // $('#deleteModal').on('hide.bs.modal', function (e) {
                //     loadAllDvds();
                // })
            }
            // ,
            // error: function () {
            //     $('#errors-edit-dvds-form')
            //         .append($('<li>')
            //             .attr({class: 'list-group-item list-group-item-danger'})
            //             .text('Error calling web service. Please try again later.'));
            //     return false;
            // }
        });
        var millisecondsToWait = 200;
        setTimeout(function () {
            $('#deleteModal').modal('hide');
            loadAllDvds();

        }, millisecondsToWait);
        // $('#deleteModal').on('hide.bs.modal', function (e) {
        //     loadAllDvds();
        // });
        // loadAllDvds();
    });


}); /*end of the document ready function*/
