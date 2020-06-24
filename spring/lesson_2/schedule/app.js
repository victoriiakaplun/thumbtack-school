'use strict';

const User = require('./model/User');
const Event = require('./model/Event');
const Schedule = require('./model/Schedule');
const MESSAGE = require('./utils/messageMap');
const utils = require('./utils/utils');
const eventService = require('./service/eventService');
const timeService = require('./service/timeService');
const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});


async function registerUser() {
    const name = await utils.askQuestion(MESSAGE.get('INPUT_NAME'), rl);
    const email = await utils.askQuestion(MESSAGE.get('INPUT_EMAIL'), rl);
    let user = new User(name, email);
    console.log(MESSAGE.get('USER_NAME'), `${name}`);
    console.log(MESSAGE.get('USER_EMAIL'), `${email}`);
    console.log(MESSAGE.get('USER_ID'), `${user.id}`);
    return user;
}

async function addEventFromConsole(freeTime, events) {
    let title = await utils.askQuestion(MESSAGE.get('INPUT_EVENT_TITLE'), rl);
    const date = await eventService.inputEventDateInfo(rl, freeTime);
    console.log("DATE:: ", date);
    const times = await eventService.inputEventTimeInfo(rl, date, freeTime);
    console.log("TIMES:: ",times);
    const event = new Event(title, date, parseInt(times[0]), parseInt(times[1]));
    eventService.addEvent(date, event, events);
    let dateTimes = freeTime.find(d => d.date.getTime() === date.getTime());
    let dateTimesIndex = freeTime.indexOf(dateTimes);
    let startTimeIndex = dateTimes.startTimes.indexOf(parseInt(times[0]));
    let endTimeIndex = dateTimes.startTimes.indexOf(parseInt(times[1])-1);
    eventService.excludeDateHours(freeTime, dateTimes, dateTimesIndex, startTimeIndex, endTimeIndex);
}

async function main() {
    let user = await registerUser();
    let freeTime = [];
    let events = new Map();
    let startEventTime = 8;
    let endEventTime = 19;
    let startEventDate = new Date(2020, 2, 2);
    let endEventDate = new Date(2020, 2, 8);
    timeService.fillTime(freeTime,startEventTime, endEventTime, startEventDate, endEventDate);
    eventService.generateEvents(10, freeTime, events);
    timeService.printFreeTime(freeTime);
    eventService.printEvents(Date.now(), events);
    await addEventFromConsole(freeTime, events);
    console.log("AFTER::::::::::::::::::");
    timeService.printFreeTime(freeTime);
    eventService.printEvents(Date.now(), events);
    const schedule = new Schedule(user.id, user.name, Date.now(), events);
        schedule.printSchedule('../');
    rl.close();
}

main();
