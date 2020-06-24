const Product = require('./Product.js')

module.exports = class OrderProduct {
    constructor(product) {
        this.id = product.id;
        this.name = product.name;
        this.weight = product.weight;
        this.price = product.price;
    }
}
