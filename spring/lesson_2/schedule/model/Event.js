'use strict';

const utils = require('../utils/utils');

module.exports = class Event {
    constructor(title, date, startTime, endTime) {
        this.id = utils.randomInt(10000, 99998);
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
};
