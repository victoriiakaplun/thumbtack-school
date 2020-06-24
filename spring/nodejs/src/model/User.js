const { getNewIdGenerator } = require('../helpers/helper');

module.exports = class User {

    userIdGenerator = getNewIdGenerator();

    constructor(name, email, password) {
        this.id = this.userIdGenerator();
        this.name = name;
        this.email = email;
        this.password = password;
    }
};
