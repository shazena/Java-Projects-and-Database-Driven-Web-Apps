var DataService = function () {
    var self = this;

    //getAllDvds
    self.getDvds = function (callback, errorFunction) {
        $.ajax({
            url: 'https://tsg-dvds.herokuapp.com/dvds',
            method: 'GET',
            success: callback,
            error: errorFunction
        });
    }

    //getDVDbyId
    self.getDvdById = function (id, callback, errorFunction) {
        $.ajax({
            method: 'GET',
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + id,
            success: callback,
            error: errorFunction
        })
    }

    //getDvdForSearchTerm
    self.getDvdBySearchTerm = function (category, searchTerm, callback, errorFunction) {
        $.ajax({
            method: 'GET',
            url: "https://tsg-dvds.herokuapp.com/dvds/" + category + "/" + searchTerm,
            success: callback,
            error: errorFunction
        })

    }

    //addDVD
    self.createDvd = function (dvd, callback, errorFunction) {
        $.ajax({
            method: 'POST',
            url: 'https://tsg-dvds.herokuapp.com/dvd',
            data: JSON.stringify(dvd),
            contentType: 'application/json',
            success: callback,
            error: errorFunction
        })
    }

    //updateDVD
    self.updateDvd = function (dvd, callback, errorFunction) {
        $.ajax({
            method: 'PUT',
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvd.id,
            data: JSON.stringify(dvd),
            contentType: 'application/json',
            success: callback,
            error: errorFunction
        })
    }

    //deleteDVD
    self.deleteDvd = function (dvdId, callback, errorFunction) {
        $.ajax({
            method: 'DELETE',
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvdId,
            success: callback,
            error: errorFunction
        })
    }

}