const random = require("./Randomizers.js")

module.exports = class Client {

    constructor(firstName, lastName, age) {
        this.id = random.randomInt(10000, 99998);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    get id() {
        return this._id;
    }

    set id(id) {
        this._id = id;
    }

    get firstName() {
        return this._firstName;
    }

    set firstName(firstName) {
            this._firstName = firstName;
    }

    get lastName() {
        return this._lastName;
    }

    set lastName(lastName) {
            this._lastName = lastName;
    }

    get age() {
        return this._age;
    }

    set age(age) {
            this._age = age;
    }
}
