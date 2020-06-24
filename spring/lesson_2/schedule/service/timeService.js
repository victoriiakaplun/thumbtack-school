'use strict';

const utils = require('../utils/utils');

module.exports = {
    fillTime: function (freeTime, startEventTime, endEventTime, startEventDate, endEventDate) {
        let currentDate = startEventDate;
        while (currentDate <= endEventDate) {
            let times = [];
            for (let i = startEventTime; i < endEventTime; i++) {
                times.push(i);
            }
            freeTime.push({date: currentDate, startTimes: times});
            currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 1);
        }
    },

    printFreeTime: function (freeTime) {
        freeTime.forEach(dayInfo => {
            console.log(`${utils.dayOfWeekAsString(dayInfo.date.getDay())} ${dayInfo.date.toLocaleString()}
        TIMES:
        ${dayInfo.startTimes}`);
        });
    },
};