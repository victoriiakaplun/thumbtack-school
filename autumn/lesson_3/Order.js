const random = require('./Randomizers.js')

module.exports = class Order {

    constructor(customerId, prodIds, courierId, status) {
        this.id = random.randomInt(0, 98);
        this.customerId = customerId;
        this._productsIds = [];
        for (let prodId of prodIds) {
            this.productsIds.push(prodId);
        }
        this.courierId = courierId;
        this.status = status;
    }

    get id() {
        return this._id;
    }

    set id(id) {
        this._id = id;
    }

    get customerId() {
        return this._customerId;
    }

    set customerId(customerId) {
        this._customerId = customerId;
    }

    get courierId() {
        return this._courierId;
    }

    set courierId(courierId) {
        this._courierId = courierId;
    }

    get productsIds() {
        return this._productsIds;
    }

    set productsIds(prodIds) {
        this._productsIds = [];
        for (let prodId of prodIds) {
            this.productsIds.push(prodId);
        }
    }

    get status(){
        return this._status;
    }

    set status(status){
        this._status = status;
    }
}
