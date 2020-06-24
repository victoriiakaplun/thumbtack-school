'use strict';

const utils = require('../utils/utils');
const fs = require('fs');

module.exports = class Schedule {
    constructor(userId, userName, creationDate, events) {
        this.scheduleId = utils.randomInt(0,10000);
        this.userName = userName;
        this.userId = userId;
        this.creationDate = creationDate;
        this.events = [...events];
    }

    printSchedule(path) {
        if (!fs.existsSync(path)) {
            fs.mkdir(path, {recursive: true}, (err) => {
                if (err) throw err;
            });
        }
        fs.writeFile(path + `schedule-${this.userName}.txt`, JSON.stringify(this), function (error) {
            if (error) throw error;
        });
    }
};
