'use strict';

const Event = require('../model/Event');
const utils = require('../utils/utils');
const MESSAGE = require('../utils/messageMap');

module.exports = {
    generateEvents: function (eventsAmount, freeTime, events) {
        for (let i = 0; i < eventsAmount; i++) {
            const randomDateInfoIndex = utils.randomInt(0, freeTime.length - 1);
            const randomDateInfo = freeTime[randomDateInfoIndex];
            const freeTimesAmount = randomDateInfo.startTimes.length;
            const randomStartTimeIndex = utils.randomInt(0, freeTimesAmount - 1);
            const randomStartTime = randomDateInfo.startTimes[randomStartTimeIndex];
            let isRightEndTime = false;
            let randomEndTimeIndex;
            let randomEndTime;
            while (!isRightEndTime) {
                randomEndTimeIndex = utils.randomInt(randomStartTimeIndex, freeTimesAmount - 1);
                randomEndTime = randomDateInfo.startTimes[randomEndTimeIndex];
                isRightEndTime = isTimeAvailable(randomStartTime, randomEndTime, randomDateInfo.startTimes);
            }
            const randomEventTitle = utils.randomStr(4) + ' ' + utils.randomStr(6);
            const randomEvent = new Event(randomEventTitle, randomDateInfo.date, randomStartTime, randomEndTime + 1);
            addEvent(randomDateInfo.date, randomEvent, events);
            excludeDateHours(freeTime, randomDateInfo, randomDateInfoIndex, randomStartTimeIndex, randomEndTimeIndex);
        }
    },

    inputEventDateInfo: async function(rl, freeTime) {
        return new Promise(resolve => {
            if (freeTime.length === 0) {
                console.log(MESSAGE.get('NO_DATE'));
                rl.close();
                resolve();
            }
            let date;
            rl.setPrompt(MESSAGE.get('INPUT_EVENT_DATE'));
            rl.prompt();
            rl.on('line', function (line) {
                let isValid = true;
                const dateParts = line.split('-');
                date = new Date(parseInt(dateParts[0]), parseInt(dateParts[1]) - 1, parseInt(dateParts[2]));
                if (!freeTime.find(dayObj => dayObj.date.getTime() === date.getTime())) {
                    rl.setPrompt(MESSAGE.get('NO_TIME'));
                    rl.prompt(true);
                    isValid = false;
                }
                if (isValid) {
                    resolve(date);
                }
            });
        });
    },

    inputEventTimeInfo: async function(rl, date, freeTime) {
        return new Promise(resolve => {
            let times = [];
            let start, end;
            rl.setPrompt(MESSAGE.get('INPUT_EVENT_START_END_TIME'));
            rl.prompt();
            rl.on('line', function (line) {
                let isValid = true;
                times = line.split(',');
                start = parseInt(times[0]);
                end = parseInt(times[1]) - 1;
                let dateStartTimes = freeTime.find(d => d.date.getTime() === date.getTime());
                if (!isTimeAvailable(start, end, dateStartTimes.startTimes)) {
                    rl.setPrompt(MESSAGE.get('INVALID_EVENT_TIME'));
                    rl.prompt(true);
                    isValid = false;
                }
                if (isValid) {
                    resolve(times);
                }
            });
        });
    },

    printEvents: function (currentDate, events) {
        events.forEach((value, key) => {
            if (currentDate > key) {
                console.log('The event already ended:');
            }
            console.log(`${utils.dayOfWeekAsString(key.getDay())} ${key.toLocaleString()}`);
            value.forEach(e => console.log(`${e.title} ${e.startTime} ${e.endTime}`));
        });
    },

    addEvent,
    excludeDateHours,
};

function addEvent(date, event, events) {
    if (events.has(date)) {
        events.get(date).push(event);
    } else {
        events.set(date, [event]);
    }
}

function excludeDateHours(freeTime, dateTimes, dateTimesIndex, startTimeIndex, endTimeIndex) {
    dateTimes.startTimes.splice(startTimeIndex, endTimeIndex - startTimeIndex + 1);
    if (dateTimes.startTimes.length === 0) {
        freeTime.splice(dateTimesIndex, 1);
    }
}

function isTimeAvailable(startTime, endTime, times) {
    for (let i = startTime; i <= endTime; i++) {
        if (!times.includes(i)) {
            return false;
        }
    }
    return true;
}
