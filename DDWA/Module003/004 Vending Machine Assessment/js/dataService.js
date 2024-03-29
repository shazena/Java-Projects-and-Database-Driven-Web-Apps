var DataService = function () {
    var self = this;

    ///////////////////////////////////////////////////////////
    // Data Manipulation Methods
    //
    self.getItems = function (callback, errorFunction) {
        $.ajax({
            method: 'GET',
            url: 'http://vending.us-east-1.elasticbeanstalk.com/items',
            success: callback,
            error: errorFunction
        });
    }

    self.purchaseItem = function (money, itemId, callback, errorFunction) {
        $.ajax({
            method: 'POST',
            url: 'http://vending.us-east-1.elasticbeanstalk.com/money/' + money + '/item/' + itemId,
            success: callback,
            error: errorFunction
        })
    }

}