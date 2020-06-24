const OrderStatement = require('./OrderStatement')
const fs = require('fs')
const MESSAGE =  require('./MessageMap')

    function printOrder(path) {
        return fs.readFileSync(path, (error) => {
            if(error) {
                console.log(MESSAGE.get('ERROR_FILE'));
            }
        });
    }

    function sortOrdersByCreationDate(orders) {
        orders.sort((first,second) => first.cretionDate < second.creationDate ? 1: -1);
    }

    function app() {
        let orderStatements = [];
        const folder = './orders/';
        fs.readdir(folder, (error, files) => {
            if(error) {
                console.log(MESSAGE.get('ERROR_DIRECTORY'));
            } else {
                console.log(MESSAGE.get('ERROR_DIRECTORY'));
                files.forEach(function (file) {
                    let tmp = JSON.parse(printOrder(folder + file));
                    orderStatements.push(tmp);
                });
                sortOrdersByCreationDate(orderStatements);
                orderStatements.forEach(function (orderStatement) {
                    console.log(orderStatement);
                });
            }
        });
    }

    app();
