$(document).ready(function () {

    loadAllItems();

    $('#addDollarButton').on('click', function () {
        addMoneyToMachine(1);
    });
    $('#addDimeButton').on('click', function () {
        addMoneyToMachine(0.1)
    });
    $('#addNickelButton').on('click', function () {
        addMoneyToMachine(0.05)
    });
    $('#addQuarterButton').on('click', function () {
        addMoneyToMachine(0.25)
    });

    $(document).on('click', '.itemToPurchase', function () {
        $('#messagesDisplay').val("");
        var item = $(this);
        var idForUser = item.data('idforuser');
        // var idForServer = item.data('idforserver');
        // alert(idForUser + "" + idForServer);

        $('#itemIdDisplay').val(idForUser);

    });

    $(document).on('click', '#changeReturnButton', returnChange);
    $(document).on('click', '#purchaseButton', makePurchase);


});

function makePurchase() {
    //clear messages
    $('#messagesDisplay').val("");
    //if no selection, set error message, do this first. then proceed with money collection.
    var idForUser = $('#itemIdDisplay').val();
    if (idForUser === "") {
        $('#messagesDisplay').val('Please make a selection');
        return false;
    }

    //grab money input
    var money = $('#moneyInput').val();

    //grab item selection so you can get the server id number
    // var itemId = item + idForUser;
    // var idForServer = $('#${itemId}').data('idforserver');
    // alert(idForServer + " , " + money);
    //run AJAX call
    //grab the price of the item... no way to GET one item. so the price needs to be stored in the product as well
    //this added Price might be able to be removed...
    //will the Response code of 422 be recognized as an error in AJAX?
    ////or grab price from div and then parseFloat to turn it back into a number. NO
    //else if there is a selection, now verify that the money is enough for the product NO
    ////if not enough for product, then set error message NO
    //else if there is enough money NO

    alert('hi');

}


function addMoneyToMachine(amount) {
    var currentMoney = $('#moneyInput').val();
    if (currentMoney === "") {
        currentMoney = 0;
    }
    var newValue = parseFloat(currentMoney) + amount;
    $('#moneyInput').val(Number.parseFloat(newValue).toFixed(2));
}

function loadAllItems() {
    //call
    //for each item, format
    //have anId for User thingy, send it to formatter

    var itemIdForUser = 1;

    $.ajax({
        method: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function (items, status) {
            $('#itemsContainer').empty();
            $.each(items, function (index, item) {
                $('#itemsContainer').append(formatItem(item, itemIdForUser))
                itemIdForUser++;
            });
        },
        error: function () {
            //FIXME needs proper error message
            alert('tsk tsk');
        }
    })

}

function formatItem(item, idForUser) {
    var formattedPrice = item.price.toLocaleString('en-US', {style: 'currency', currency: 'USD'});
    return `<div class = "itemToPurchase" id = "item${idForUser}" data-idforuser = "${idForUser}" data-idforserver = "${item.id}"
data-itemprice = "${item.price}">
        <p class = "itemId">${idForUser}</p>
        <p class = "itemName">${item.name}</p>
        <p class = "itemPrice">${formattedPrice}</p>
        <p class = "itemQuantity">Quantity Left: ${item.quantity}</p>
    </div>`;
}

function calculateChange(money) {
    var coinArray = [];
    money = money * 100;

    var quarters = Math.floor(money / 25);
    coinArray.push(quarters);
    money = money % 25;
    var dimes = Math.floor(money / 10);
    coinArray.push(dimes);
    money = money % 10;
    var nickels = Math.floor(money / 5);
    coinArray.push(nickels);
    money = money % 5;
    var pennies = Math.floor(money);
    coinArray.push(pennies);

    var coinNameArray = ["Quarter", "Dime", "Nickel", "Penny"];

    var isThereChange = false;

    for (let i = 0; i < coinArray.length; i++) {
        if (coinArray[i] > 0) {
            isThereChange = true;
        }
    }

    var stringChange = "";

    if (isThereChange) {
        for (let i = 0; i < coinArray.length; i++) {
            if (coinArray[i] > 0) {
                stringChange += coinArray[i] + " ";
                if (i === 3) {
                    if (coinArray[i] === 1) {
                        stringChange += coinNameArray[i] + " ";
                    } else {
                        stringChange += "Pennies ";
                    }
                } else {
                    if (coinArray[i] == 1) {
                        stringChange += coinNameArray[i] + " ";
                    } else {
                        stringChange += coinNameArray[i] + "s ";
                    }
                }
            }
        }
    }

    return stringChange;
}

function returnChange() {
    var money = parseFloat($('#moneyInput').val());
    $('#moneyInput').val("");
    $('#itemIdDisplay').val("");
    $('#messagesDisplay').val("");
    $('#changeOutput').val(calculateChange(money));
}