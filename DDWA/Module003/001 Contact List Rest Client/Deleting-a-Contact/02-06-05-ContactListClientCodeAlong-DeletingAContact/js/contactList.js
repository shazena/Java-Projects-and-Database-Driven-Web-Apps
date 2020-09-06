$(document).ready(function () {

    loadContacts();

    // Add Button onclick handler
    $('#add-button').click(function (event) {

        // check for errors and display any that we have
        // pass the input associated with the add form to the validation function
        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));

        // if we have errors, bail out by returning false
        if (haveValidationErrors) {
            return false;
        }

        // if we made it here, there are no errors so make the ajax call
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/contact',
            data: JSON.stringify({
                firstName: $('#add-first-name').val(),
                lastName: $('#add-last-name').val(),
                company: $('#add-company').val(),
                phone: $('#add-phone').val(),
                email: $('#add-email').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function(data, status) {
                // clear errorMessages
                $('#errorMessages').empty();
               // Clear the form and reload the table
                $('#add-first-name').val('');
                $('#add-last-name').val('');
                $('#add-company').val('');
                $('#add-phone').val('');
                $('#add-email').val('');
                loadContacts();
            },
            error: function() {
                $('#errorMessages')
                   .append($('<li>')
                   .attr({class: 'list-group-item list-group-item-danger'})
                   .text('Error calling web service.  Please try again later.'));
            }
        });
    });

    // Update Button onclick handler
    $('#edit-button').click(function (event) {

        // check for errors and display any that we have
        // pass the input associated with the edit form to the validation function
        var haveValidationErrors = checkAndDisplayValidationErrors($('#edit-form').find('input'));

        // if we have errors, bail out by returning false
        if (haveValidationErrors) {
            return false;
        }

        // if we get to here, there were no errors, so make the Ajax call
        $.ajax({
           type: 'PUT',
           url: 'http://localhost:8080/contact/' + $('#edit-contact-id').val(),
           data: JSON.stringify({
             contactId: $('#edit-contact-id').val(),
             firstName: $('#edit-first-name').val(),
             lastName: $('#edit-last-name').val(),
             company: $('#edit-company').val(),
             email: $('#edit-email').val(),
             phone: $('#edit-phone').val()
           }),
           headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json'
           },
           'dataType': 'json',
            success: function() {
                // clear errorMessages
                $('#errorMessages').empty();
                hideEditForm();
                loadContacts();
           },
           error: function() {
             $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service.  Please try again later.'));
           }
       })
    });
});

function loadContacts() {
    // we need to clear the previous content so we don't append to it
    clearContactTable();

    // grab the the tbody element that will hold the rows of contact information
    var contentRows = $('#contentRows');

    $.ajax ({
        type: 'GET',
        url: 'http://localhost:8080/contacts',
        success: function (data, status) {
            $.each(data, function (index, contact) {
                var name = contact.firstName + ' ' + contact.lastName;
                var company = contact.company;
                var id = contact.contactId;

                var row = '<tr>';
                    row += '<td>' + name + '</td>';
                    row += '<td>' + company + '</td>';
                    row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
                    row += '<td><a onclick="deleteContact(' + id + ')">Delete</a></td>';
                    row += '</tr>';
                contentRows.append(row);
            });
        },
        error: function() {
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service.  Please try again later.'));
        }
    });
}

function clearContactTable() {
    $('#contentRows').empty();
}

function showEditForm(contactId) {
    // clear errorMessages
    $('#errorMessages').empty();
    // get the contact details from the server and then fill and show the
    // form on success
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/contact/' + contactId,
        success: function(data, status) {
              $('#edit-first-name').val(data.firstName);
              $('#edit-last-name').val(data.lastName);
              $('#edit-company').val(data.company);
              $('#edit-email').val(data.email);
              $('#edit-phone').val(data.phone);
              $('#edit-contact-id').val(data.contactId);
          },
          error: function() {
            $('#errorMessages')
               .append($('<li>')
               .attr({class: 'list-group-item list-group-item-danger'})
               .text('Error calling web service.  Please try again later.'));
          }
    });
    $('#contactTableDiv').hide();
    $('#editFormDiv').show();
}

function hideEditForm() {
    // clear errorMessages
    $('#errorMessages').empty();
    // clear the form and then hide it
    $('#edit-first-name').val('');
    $('#edit-last-name').val('');
    $('#edit-company').val('');
    $('#edit-phone').val('');
    $('#edit-email').val('');
    $('#editFormDiv').hide();
    $('#contactTableDiv').show();
}

function deleteContact(contactId) {
    $.ajax ({
        type: 'DELETE',
        url: "http://localhost:8080/contact/" + contactId,
        success: function (status) {
            loadContacts();
        }
    });
}

// processes validation errors for the given input.  returns true if there
// are validation errors, false otherwise
function checkAndDisplayValidationErrors(input) {
    // clear displayed error message if there are any
    $('#errorMessages').empty();
    // check for HTML5 validation errors and process/display appropriately
    // a place to hold error messages
    var errorMessages = [];

    // loop through each input and check for validation errors
    input.each(function() {
        // Use the HTML5 validation API to find the validation errors
        if(!this.validity.valid)
        {
            var errorField = $('label[for='+this.id+']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });

    // put any error messages in the errorMessages div
    if (errorMessages.length > 0){
        $.each(errorMessages,function(index,message){
            $('#errorMessages').append($('<li>').attr({class: 'list-group-item list-group-item-danger'}).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}
