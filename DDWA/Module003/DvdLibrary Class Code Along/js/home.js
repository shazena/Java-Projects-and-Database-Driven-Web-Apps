let ds = new DataService();

// ///////////////////////////////////////////////////////////
// Page manipulation methods
function formatRow(dvd) {
    return `<tr>
    <td>${dvd.title}</td>
    <td>${dvd.releaseYear}</td>
    <td>${dvd.director}</td>
    <td>${dvd.rating}</td>
    <td>
        <a href="#" data-dvdid='${dvd.id}' class='editDvd'><i class="fa fa-pencil"></i></a>
        <a href="#" data-dvdid='${dvd.id}' class='deleteDvd'><i class="fa fa-trash"></i></a>
    </td>
</tr>`
}


function refreshTable(dvds) {
    let dvdTable = $("#dvdTable>tbody");
    dvdTable.empty();

    for (let i = 0; i < dvds.length; i++) {
        const dvd = dvds[i];
        $(dvdTable).append(formatRow(dvd));
    }
}


// /////////////////////////////////////////////////////////////////////
// Page event methods
function onAddDvdSubmit(e) {
    e.preventDefault();

    let form = $(this);

    let dvd =
    {
        id: 0,
        title: $("#title").val(),
        releaseYear: $("#releaseYear").val(),
        director: $("#director").val(),
        rating: $("#rating").val()
    };

    ds.addDvd(dvd, function () {
        ds.getDvds(refreshTable, alertError);
    }, alertError);

    $(form)[0].reset();
}

function onDeleteDvdClicked(e) {
    e.preventDefault();

    var dvdId = $(this).data('dvdid');
    ds.removeDvd(dvdId, function(){
        ds.getDvds(refreshTable, alertError);
    }, alertError);
    
}

function onEditDvdClicked(e) {
    e.preventDefault();

    var dvdId = $(this).data("dvdid");
    ds.getDvdById(dvdId, function(dvd){
        if (dvd) {
            $("#editDvdId").val(dvd.id);
            $("#editTitle").val(dvd.title);
            $("#editReleaseYear").val(dvd.releaseYear);
            $("#editDirector").val(dvd.director);
            $("#editRating").val(dvd.rating);
            $("#editDvdModal").modal('show');
        }
    }, alertError);

    
}

function onEditDvdSubmit(e) {
    e.preventDefault();

    let dvd =
    {
        id: $("#editDvdId").val(),
        title: $("#editTitle").val(),
        releaseYear: $("#editReleaseYear").val(),
        director: $("#editDirector").val(),
        rating: $("#editRating").val()
    };

    ds.updateDvd(dvd, function(){
        ds.getDvds(refreshTable, alertError);
    }, alertError);
    
    $("#editDvdModal").modal('hide');
    
}

function alertError(msg) {
    alert(msg.responseJSON.message);
}


// ////////////////////////////////////////////////////////////////
$(document).ready(function () {
    ds.getDvds(refreshTable, alertError);

    $(document).on('submit', '#addDvd', onAddDvdSubmit);
    $(document).on('submit', '#editDvd', onEditDvdSubmit);

    $(document).on('click', '.deleteDvd', onDeleteDvdClicked);

    $(document).on('click', '.editDvd', onEditDvdClicked);
});