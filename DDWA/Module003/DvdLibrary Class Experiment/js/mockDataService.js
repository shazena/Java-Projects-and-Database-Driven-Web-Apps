var DataService = function () {
    var self = this;

    let counter = 2;

    let dvdList = [
        {
            dvdId: 1,
            title: 'Crossroads',
            releaseYear: '1986',
            director: 'Walter Hill',
            rating: 'R'
        }
    ];

    // //////////////////////////////////////////////////////////////////
    // Data manipulation methods
    self.getDvds = function(callback, error) {
        setTimeout(function(){
            callback(dvdList);
        }, 500);
    }

    self.addDvd = function(dvd, callback, error){
        setTimeout(function(){
            dvd.dvdId = counter++;
            dvdList.push(dvd);
            callback();
        }, 500);

        //error();
    }

    self.removeDvd = function (dvdId, callback, error) {
        let tempList = [];

        for (let i = 0; i < dvdList.length; i++) {
            const dvd = dvdList[i];
            if (dvd.dvdId != dvdId) {
                tempList.push(dvd);
            }
        }

        dvdList = tempList;

        setTimeout(function(){
            callback();
        }, 500);
        
        //error();
    }

    self.getDvdById = function (dvdId, callback, error) {
        setTimeout(function() {
            for (let i = 0; i < dvdList.length; i++) {
                const dvd = dvdList[i];
                if (dvd.dvdId == dvdId) {
                     callback(dvd);
                     return;
                }
            }
    
            error("Dvd was not found");
        }, 500);
        
    }

    self.updateDvd = function (dvd, callback, error) {
        setTimeout(function(){
            for (let i = 0; i < dvdList.length; i++) {
                if (dvd.dvdId == dvdList[i].dvdId) {
                    dvdList[i] = dvd;
                    callback();
                    return;
                }
            }

            error("Dvd was not found.");
        }, 500);
        
    }

}