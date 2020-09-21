var DataService = function () {
    var self = this;


    //https://tsg-dvds.herokuapp.com/

    // //////////////////////////////////////////////////////////////////
    // Data manipulation methods
    self.getDvds = function(callback, errorFunc) {
        $.ajax({
            url: 'https://tsg-dvds.herokuapp.com/dvds',
            method: 'GET',
            success: callback,
            error: errorFunc
        });
    }

    self.addDvd = function(dvd, callback, errorFunc){
        $.ajax({
            url: 'https://tsg-dvds.herokuapp.com/dvd',
            method: 'POST',
            data: JSON.stringify(dvd),
            contentType : "application/json",
            success: callback,
            error: errorFunc
        });
    }

    self.removeDvd = function (dvdId, callback, errorFunc) {
        $.ajax({
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvdId,
            method: 'DELETE',
            success: callback,
            error: errorFunc
        });
    }

    self.getDvdById = function (dvdId, callback, errorFunc) {
        $.ajax({
            url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvdId,
            method: 'GET',
            success: callback,
            error: errorFunc
        });
    }

    self.updateDvd = function (dvd, callback, errorFunc) {
       $.ajax({
        url: 'https://tsg-dvds.herokuapp.com/dvd/' + dvd.id,
        method: 'PUT',
        contentType : "application/json",
        data: JSON.stringify(dvd),
        success: callback,
        error: errorFunc
       })
    }

}