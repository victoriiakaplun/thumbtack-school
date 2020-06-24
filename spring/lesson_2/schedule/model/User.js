'use strict';

const utils = require('../utils/utils');

module.exports = class User {
    constructor( name, email) {
        this.id = utils.randomInt(10000, 99998);
        this.name = name;
        this.email = email;
    }

};
