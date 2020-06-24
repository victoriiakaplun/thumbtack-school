const random = require('./Randomizers.js')
const Product = require('./Product.js')

module.exports = class Order {

    constructor(userId, products, orderIds) {
        this.id = random.randomInt(0, 98);
        this.userId = userId;
        this.creationDate = new Date().toDateString();
        this.productsIds = [];
        let tmpSum = 0;
        for(orderId of orderIds){
            this.productsIds.push(orderId);
            tmpSum += products[orderId].price;
        }
        this.price = tmpSum;
    }

    get id() {
        return this._id;
    }

    set id(id) {
        this._id = id;
    }

    get userId() {
        return this._userId = userId;
    }

    set userId(userId) {
        this._userId = userId;
    }

    get creationDate() {
        return this._creationDate;
    }

    set creationDate(creationDate) {
        this._creationDate = creationDate;
    }

    get products() {
        return this._products;
    }

    set products(products) {
        this._products = Array.from(products);
    }

    get price() {
        return this._price;
    }

    set price(price) {
        this._price = price;
    }
}
