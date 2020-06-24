const random = require("./Randomizers.js")

module.exports = class Product {

    constructor(name, weight, amount, price) {
        this.id = random.randomInt(10000, 99998);
        this.name = name;
        this.weight = weight;
        this.amount = amount;
        this.price = price;
    }

    get id() {
        return this._id;
    }

    set id(id){
        this._id = id;
    }

    get name() {
        return this._name;
    }

    set name(nameStr) {
        if (nameStr !== "") {
            this._name = nameStr;
        }
    }

    get weight() {
        return this._weight;
    }

    set weight(weightStr) {
        if (weightStr !== "") {
            this._weight = weightStr;
        }
    }

    get amount() {
        return this._amount;
    }

    set amount(amountStr) {
        if (amountStr !== "") {
            this._amount = amountStr;
        }
    }

    get price() {
        return this._price;
    }

    set price(priceStr) {
        if (priceStr !== "") {
            this._price = priceStr;
        }
    }
}
