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
        var item = $(this);
        var idForUser = item.data('idforuser');
        alert(idForUser);
        var idForServer = item.data('idforserver');
        alert(idForServer);
    });


});

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
    return `<div class = "itemToPurchase" data-idforuser = "${idForUser}" data-idforserver = "${item.id}">
        <p class = "itemId">${idForUser}</p>
        <p class = "itemName">${item.name}</p>
        <p class = "itemPrice">${formattedPrice}</p>
        <p class = "itemQuantity">Quantity Left: ${item.quantity}</p>
    </div>`;
}

function returnChangeToUser(money) {
    var coinArray = [];
    money = money * 100;

    var quarters = Math.floor(money / 25);
    coinArray.push(quarters);
    var dimes = Math.floor((money % 25) / 10);
    coinArray.push(dimes);
    var nickels = Math.floor((money % 10) / 5);
    coinArray.push(nickels);
    var pennies = Math.floor((money % 5));
    coinArray.push(pennies);

    var coinNameArray = ["Quarter", "Dime", "Nickel", "Pennies"];

    var isThereChange = false;

    for (let i = 0; i < coinArray.length; i++) {
        if(coinArray[i]>0){
            isThereChange = true;
        }
    }

    var stringChange = "";

    if(isThereChange){
        for (let i = 0; i < coinArray.length; i++) {
                if(coinArray[i]==1){
                    // stringChange +=
                }
        }
    }




}