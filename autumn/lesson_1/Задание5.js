'use strict';

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

let numbers = [];
const input = (question, rl) => {
    return new Promise((resolve, reject) => {
        rl.question(question, (number) => {
            const nmbr = parseInt(number);
            if(isNaN(nmbr)) {
                reject();
            }
            numbers.push(nmbr);
            resolve();
        });
    });
};

input('Enter number', rl)
    .then(() => input('Enter number', rl))
    .then(() => input('Enter number', rl))
    .then(() => input('Enter number', rl))
    .then(() => input('Enter number', rl))
    .then(() => {
        console.log(numbers);
        console.log(Math.max(...numbers));
    })
    .catch((error) => console.log('Error!'));


