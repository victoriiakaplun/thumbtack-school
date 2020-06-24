const random = require("./Randomizers.js")

module.exports = class Client {

    constructor(name, email, budget) {
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.id = random.randomInt(10000, 99998);
    }

    set id(id) {
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

    get email() {
        return this._email;
    }

    set email(emailStr) {
        if (emailStr !== "") {
            this._email = emailStr;
        }
    }

    get budget() {
        return this._budget;
    }

    set budget(budgetStr) {
        if (budgetStr !== "") {
            this._budget = budgetStr;
        }
    }

    get id() {
        return this._id;
    }
}
