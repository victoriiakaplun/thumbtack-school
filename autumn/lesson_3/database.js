const Client = require('./Client.js')
const Product = require('./Product.js')
const Order = require('./Order.js')
module.exports = {

    clientsMap: {},
    productsMap: {},
    ordersMap: {},

    addClient(firstName, lastName, age) {
        if (firstName.length < 3 || lastName.length < 3 || age < 1) {
            return -1;
        }
        const client = new Client(firstName, lastName, age);
        this.clientsMap[client.id] = client;
        return client.id;
    },

    addProduct(name, weight) {
        if (name.length < 1 || weight < 1) {
            return -1;
        }
        const product = new Product(name, weight);
        this.productsMap[product.id] = product;
        return product.id;
    },

    addOrder(customerId, productsIds) {
        if (!this.checkIfClientExists(customerId)) {
            return -1;
        }
        if (productsIds.some((productId) => !this.checkIfProductExists(productId))) {
            return -1;
        }
        const order = new Order(customerId, productsIds, null, 'open');
        this.ordersMap[order.id] = order;
        return order.id;
    },

    getClients() {
        return this.clientsMap;
    },

    getProducts() {
        return this.productsMap;
    },

    getOrders() {
        return this.ordersMap;
    },

    checkIfClientExists(id) {
        return Object.keys(this.clientsMap).includes(`${id}`);
    },

    checkIfProductExists(id) {
        return Object.keys(this.productsMap).includes(`${id}`);
    },

    checkIfOrderExists(id) {
        return Object.keys(this.ordersMap).includes(`${id}`);
    },

    getClientById(id) {
        if (!this.checkIfClientExists(id)) {
            return -1;
        } else {
            return this.clientsMap[id];
        }
    },

    getProductById(id) {
        if (!this.checkIfProductExists(id)) {
            return -1;
        } else {
            return this.productsMap[id];
        }
    },

    getOrderById(id) {
        if (!this.checkIfOrderExists(id)) {
            return -1;
        } else {
            return this.ordersMap[id];
        }
    },

    changeClientById(props, id) {
        if (!this.checkIfClientExists(id)) {
            return -1;
        } else {
            for (let propKey of Object.keys(props)) {
                if (propKey !== 'id') {
                    this.clientsMap[id][propKey] = props[propKey];
                }
            }
        }
    },

    changeProductById(props, id) {
        if(!this.checkIfProductExists(id)) {
            return -1;
        } else {
            for (let propKey of Object.keys(props)) {
                if(propKey !== 'id') {
                    this.productsMap[id][propKey] = props[propKey];
                }
            }
        }
    },

    changeOrderById(props, id) {
        if(!this.checkIfOrderExists(id)) {
            return -1;
        } else {
            for (let propKey of Object.keys(props)) {
                if(propKey !== 'id') {
                    this.ordersMap[id][propKey] = props[propKey];
                }
            }
        }
    },

    deleteClientById(id) {
        if (!this.checkIfClientExists(id)) {
            return -1;
        } else {
            delete this.clientsMap[id];
        }
    },

    deleteProductById(id) {
        if (!this.checkIfProductExists(id)) {
            return -1;
        } else {
            delete this.productsMap[id];
        }
    },

    deleteOrderById(id) {
        if (!this.checkIfOrderExists(id)) {
            return -1;
        } else {
            delete this.ordersMap[id];
        }
    },


}



