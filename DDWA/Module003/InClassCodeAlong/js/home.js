let ds = new DataService();

//////////////////////////////////////////////////////////////////
// Page manipulation methods
function formatRow(dvd) {


    return `<tr>
    <td>${dvd.title}</td>
    <td>${dvd.releaseYear}</td>
    <td>${dvd.director}</td>
    <td>${dvd.rating}</td>
    <td>
        <a href="#" data-dvdid = '${dvd.dvdId}' class = 'editDVD'><i class="fa fa-pencil"></i></a>
        <a href="#" data-dvdid = '${dvd.dvdId}' class = 'deleteDVD'><i class="fa fa-trash"></i></a>
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

};





//////////////////////////////////////////////////////////////////
// Page event methods
function onAddDvdSubmit(e) {
    e.preventDefault();
    let form = $(this);
    let dvd =
    {
        dvdId: 0,
        title: $("#title").val(),
        releaseYear: $("#releaseYear").val(),
        director: $("#director").val(),
        rating: $("#rating").val()
    };
    setTimeout(function () {
        ds.addDvd(dvd);
        refreshTable(ds.getDvds());
        $(form)[0].reset();
    }, 1000);
}

function onDeleteDvdClicked(e) {
    e.preventDefault();
    var dvdId = $(this).data("dvdid");
    ds.removeDVD(dvdId);
    refreshTable(ds.getDvds());
}

function onEditDvdClicked(e) {
    e.preventDefault();
    var dvdId = $(this).data("dvdid");
    let dvd = ds.getDvdById(dvdId);
    if (dvd) {
        $("#editDvdId").val(dvd.dvdId);
        $("#editTitle").val(dvd.title);
        $("#editReleaseYear").val(dvd.releaseYear);
        $("#editDirector").val(dvd.director);
        $("#editRating").val(dvd.rating);
        $("#editDvdModal").modal('show');
    }
}

// function onEditDvdSubmit(e) {
//     e.preventDefault();

//     let dvd =
//     {
//         dvdId: $("#editDvdId").val(),
//         title: $("#editTitle").val(),
//         releaseYear: $("#editReleaseYear").val(),
//         director: $("#editDirector").val(),
//         rating: $("#editRating").val()
//     }


//     setTimeout(function () {
//         updateDvd(dvd);
//         refreshTable(ds.getDvds);
//         $("#editDvdModal").modal('hide');
//     }, 1000)
// }

function onEditDvdSubmit(e) {
    e.preventDefault();
    let dvd =
    {
        dvdId: $("#editDvdId").val(),
        title: $("#editTitle").val(),
        releaseYear: $("#editReleaseYear").val(),
        director: $("#editDirector").val(),
        rating: $("#editRating").val()
    };
    setTimeout(function () {
        ds.updateDvd(dvd);
        refreshTable(ds.getDvds());
        $("#editDvdModal").modal('hide');
    }, 1000);
}


$(document).ready(function () {
    refreshTable(ds.getDvds());

    $(document).on("submit", "#addDVD", onAddDvdSubmit);

    $(document).on("submit", "#editDVD", onEditDvdSubmit);

    $(document).on("click", ".deleteDVD", onDeleteDvdClicked);

    $(document).on("click", ".editDVD", onEditDvdClicked);


});