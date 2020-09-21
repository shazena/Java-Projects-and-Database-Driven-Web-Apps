let ds = new DataService();

/////////////////////////////////////////////////////////////
// PAGE MANIPULATION METHODS

function formatItem(item, idForUser) {
    var formattedPrice = item.price.toLocaleString('en-US', {style: 'currency', currency: 'USD'});

    return `<div class = "itemToPurchase" id = "item${idForUser}" data-idforuser = "${idForUser}" data-idforserver = "${item.id}">
        <p class = "itemId">${idForUser}</p>
        <p class = "itemName">${item.name}</p>
        <p class = "itemPrice">${formattedPrice}</p>
        <p class = "itemQuantity">Quantity Left: ${item.quantity}</p>
    </div>`;
}

function loadAllItems(itemList) {

    var itemIdForUser = 1;

    var itemsContainer = $('#itemsContainer');
    itemsContainer.empty();

    for (let i = 0; i < itemList.length; i++) {
        let item = itemList[i];
        itemsContainer.append(formatItem(item, itemIdForUser));
        itemIdForUser++;
    }
    //
    // $('#moneyInput').val('');
    // $('#messagesDisplay').val('');
    // $('#itemIdDisplay').val('');
    // $('#changeOutput').val('');

}

// ///////////////////////////////////////////////////////////
// PAGE EVENT METHODS


//error function for purchase
function updateMessage(message) {
    if (message != "") {
        $('#messagesDisplay').val(message.responseJSON.message);
    }
}

//callback function for purchase
function updateChange(change) {
    var quarters = change.quarters;
    var dimes = change.dimes;
    var nickels = change.nickels;
    var pennies = change.pennies;
    var coinArray = [quarters, dimes, nickels, pennies];

    var changeAsString = coinStringMaker(coinArray);

    $('#messagesDisplay').val('Thank You!!');

    $('#changeOutput').val(changeAsString);
    $('#moneyInput').val("");
}

function onAddDollarClicked(e) {
    e.preventDefault();
    checkForSuccessfulPurchase();
    addMoneyToMachine(1);
}

function onAddQuarterClicked(e) {
    e.preventDefault();
    checkForSuccessfulPurchase();
    addMoneyToMachine(0.25);
}

function onAddDimeClicked(e) {
    e.preventDefault();
    checkForSuccessfulPurchase();
    addMoneyToMachine(0.10);
}

function onAddNickelClicked(e) {
    e.preventDefault();
    checkForSuccessfulPurchase();
    addMoneyToMachine(0.05);
}

//When you select an item, update the value in the display
function onSelectedItemClicked(e) {
    checkForSuccessfulPurchase();


    var item = $(this);
    var idForUser = item.data('idforuser');
    var idForServer = item.data('idforserver');

    var itemIdDisplay = $('#itemIdDisplay');
    if (itemIdDisplay.val() != idForUser) {
        $('#messagesDisplay').val("");
    }

    itemIdDisplay.val(idForUser);
    itemIdDisplay.data('idforserver', idForServer);
}

function onPurchaseItemClicked(e) {
    e.preventDefault();

    //clear messages, item, money
    $('#messagesDisplay').val("");

    //if no selection, set error message, do this first. then proceed with money collection.
    var itemDisplay = $('#itemIdDisplay');
    var idForUser = itemDisplay.val();
    if (idForUser === "") {
        $('#messagesDisplay').val('Please make a selection');
        return false;
    }

    //grab money input
    var money = $('#moneyInput').val();
    if (money === "") {
        money = 0;
    }
    var idForServer = itemDisplay.data('idforserver');

    // alert('Making purchase');
    ds.purchaseItem(money, idForServer, function (change) {
        updateChange(change);
        $('#itemIdDisplay').val("");
        updateMessage("");
        ds.getItems(loadAllItems, handleGetItemsError);
    }, updateMessage)
}

function onReturnChangeClicked(e) {
    e.preventDefault();

    checkForSuccessfulPurchase();

    var money = parseFloat($('#moneyInput').val());
    $('#moneyInput').val("");
    $('#itemIdDisplay').val("");
    $('#messagesDisplay').val("");
    // updateChange(calculateReturnedChange(money));
    $('#changeOutput').val(calculateReturnedChange(money));
}

function handleGetItemsError(error) {
    alert('Error Calling Web service. Please try again later.');
}

//method from professor
function handleVendItemError(error) {

}

function checkForSuccessfulPurchase() {
    var messagesDisplay = $('#messagesDisplay');
    if (messagesDisplay.val() === "Thank You!!") {
        $('#moneyInput').val('');
        $('#itemIdDisplay').val('');
        $('#changeOutput').val('');
        messagesDisplay.val('');
    }
}


$(document).ready(function () {

    ds.getItems(loadAllItems, handleGetItemsError);

    $(document).on('click', '#addDollarButton', onAddDollarClicked);
    $(document).on('click', '#addDimeButton', onAddDimeClicked);
    $(document).on('click', '#addNickelButton', onAddNickelClicked);
    $(document).on('click', '#addQuarterButton', onAddQuarterClicked);

    $(document).on('click', '#changeReturnButton', onReturnChangeClicked);

    $(document).on('click', '.itemToPurchase', onSelectedItemClicked);

    $(document).on('click', '#purchaseButton', onPurchaseItemClicked);
});


function addMoneyToMachine(amount) {
    var currentMoney = $('#moneyInput').val();
    if (currentMoney === "") {
        currentMoney = 0;
    }
    var newValue = parseFloat(currentMoney) + amount;
    $('#moneyInput').val(Number.parseFloat(newValue).toFixed(2));
}

function coinStringMaker(coinArray) {
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
                        stringChange += coinNameArray[i] + ", ";
                    } else {
                        stringChange += coinNameArray[i] + "s, ";
                    }
                }
            }

        }
    }

    if (stringChange.charAt(stringChange.length - 2) === ",") {
        console.log('whoop whoop!');
        stringChange = stringChange.slice(0, stringChange.length - 2);
    }
    return stringChange;
}

function calculateReturnedChange(money) {
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

    return coinStringMaker(coinArray);
}


function toggleDarkMode() {
    var body = $('body');
    body.toggleClass('darkMode');
    var buttons = $('button');
    buttons.toggleClass('btn-outline-light');
    buttons.toggleClass('btn-outline-dark');
    var toggleIcon = $('#darkModeToggleIcon');
    toggleIcon.toggleClass('fa-moon-o');
    toggleIcon.toggleClass('fa-sun-o');

}
