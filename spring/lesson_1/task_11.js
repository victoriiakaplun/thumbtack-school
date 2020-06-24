'use strict';

const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});
let numbers = [];
const input = () => {
    return new Promise((resolve, reject) => {
        rl.question('Enter number', (answer) => {
            const number = parseInt(answer);
            if(isNaN(number)) {
                reject();
            }
            numbers.push(number);
            resolve();
        });
    });
};
input()
    .then(() => input())
    .then(() => input())
    .then(() => input())
    .then(() => input())
    .then(() => {
        console.log(numbers);
        console.log(Math.max(...numbers));
    })
    .catch((error) => console.log('Error!'));
