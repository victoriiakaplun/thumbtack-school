const random = require("./Randomizers.js")

module.exports = class Product {

    constructor(name, weight) {
        this.id = random.randomInt(10000, 99998);
        this.name = name;
        this.weight = weight;
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
            this._name = nameStr;
    }

    get weight() {
        return this._weight;
    }

    set weight(weightNum) {
            this._weight = weightNum;
        }
}
