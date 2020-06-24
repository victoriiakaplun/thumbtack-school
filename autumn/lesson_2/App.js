const Client = require("./Client.js")
const readline = require('readline')
const Product = require('./Product.js')
const Order = require('./Order.js')
const random = require('./Randomizers.js')
const fs = require('fs')
const OrderStatement = require('./OrderStatement.js')
const OrderProduct = require('./OrderProduct.js')
const MESSAGE = require('./MessageMap')

const rl = readline.createInterface( {
    input: process.stdin,
    output: process.stdout
});

function askQuestion(question, rl) {
    return new Promise((resolve, reject) => {
        rl.question(question, (str) => {
            if(str === "") {
                reject();
            }
            resolve(str);
        });
    });
}

async function registerUser() {
    const name = await askQuestion(MESSAGE.get('INPUT_NAME'), rl);
    const email = await askQuestion(MESSAGE.get('INPUT_EMAIL'), rl);
    const budget = await askQuestion(MESSAGE.get('INPUT_BUDGET'), rl);
    let client = new Client(name, email, budget);
    console.log(MESSAGE.get('USER_NAME'), `${name}`);
    console.log(MESSAGE.get('USER_EMAIL'), `${email}`);
    console.log(MESSAGE.get('USER_BUDGET'), `${budget}`);
    console.log(MESSAGE.get('USER_ID'), `${client.id}`);
    return client;
}

function generateProducts(amount) {
    let products = {};
    for (i = 0; i < amount; i++) {
        tmpName = random.randomStr(4) + " " + random.randomStr(6);
        tmpWeight = random.randomInt(0, 100);
        tmpAmount = (i % 3 === 0) ? 0 : random.randomInt(10, 98);
        tmpPrice = random.randomInt(100, 998);
        tmpProd = new Product(tmpName, tmpWeight, tmpAmount, tmpPrice);
        products[tmpProd.id] = tmpProd;
    }
    return products;
}

function printProductsList(products) {
    console.log(MESSAGE.get('PRODUCT_TABLE'));
    console.log(MESSAGE.get('PRODUCT_TABLE_HEADER'));
    for(let key of Object.keys(products)) {
        if (products[key].amount > 0) {
            console.log(products[key].id + "\t" + products[key].name + "\t" + products[key].price);
        }
    }
}

function isProductsIncludesId(id, products) {
    for(let key of Object.keys(products)) {
        if(key === id) {
            return true;
        }
    }
    return false;
}

function makeOrder(сurrentProducts) {
    return new Promise((resolve, reject) => {
        rl.setPrompt(MESSAGE.get('INPUT_PRODUCT_ID'));
        rl.prompt();
        let output = '';
        rl.on('line', function (line) {
            output = line.split(',');
            let isValid = true;
            for (let id of output) {
                const newId = parseInt(id);
                if (Number.isNaN(id) || id.toString().length !== 5 || !isProductsIncludesId(id, сurrentProducts)) {
                    output = output.filter(nId => nId !== id);
                    rl.setPrompt(MESSAGE.get('ERROR_INVALID_PRODUCT_ID') + output.join(','));
                    rl.write(output.join(','));
                    rl.prompt(true);
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                rl.close();
            }
        }).on('close', function () {
            resolve(output);
        });
    });
}

function checkIfBudgetEnought(orderIds, products, client) {
    let summ = 0;
    for(orderId of orderIds) {
        summ += products[orderId].price;
    }
    return summ <= client.budget;
}

function changeProdAmount(products, orderIds) {
    for(orderId of orderIds) {
        products[orderId].amount--;
    }
}

function writeOrderStatement(statement, path) {
    if(!fs.existsSync(path)) {
        fs.mkdir(path, {recursive: true}, (err) => {
            if(err) throw err;
        });
    }
   fs.writeFile(path + `order-${statement.orderId}.txt`, JSON.stringify(statement), function (error) {
       if(error) throw error;
   });
}

async function main() {
        let client = await registerUser();
        let currentProducts = generateProducts(4);
        printProductsList(currentProducts);
        let isEnought = false;
        let orderIds;
        while (!isEnought) {
            orderIds = await makeOrder(currentProducts);
            isEnought = checkIfBudgetEnought(orderIds, currentProducts, client);
            if(!isEnought) {
            console.log(MESSAGE.get('ERROR_NOT_ENOUGH_MONEY'));
            }
        }
        changeProdAmount(currentProducts, orderIds);
        let order = new Order(client.id, currentProducts, orderIds);
        let statement = new OrderStatement(client, order, currentProducts);
        console.log(MESSAGE.get('SUCCESS'));
        const ordersPath = 'D:\\\\Thumbtack-autumn\\sunday-thumbtack-2019-autumn\\lesson_2\\orders\\'
        writeOrderStatement(statement, ordersPath);
}


 main();









