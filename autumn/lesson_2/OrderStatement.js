const OrderProduct = require('./OrderProduct.js')

module.exports = class OrderStarement {
    constructor(client, order, products) {
        this.clietId = client.id;
        this.clientName = client.name;
        this.clientEmail = client.email;
        this.orderId = order.id;
        this.orderCreationDate = order.creationDate;
        this.orderProducts = order.productsIds.map((id) => new OrderProduct(products[id]));
        this.orderPrice = order.price;
    }
}
