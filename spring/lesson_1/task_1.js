'use strict';

const border = parseInt(process.argv[2]);
console.log(`BORDER: ${border}`);
for(let i = 1; i <= border; i++) {
    if (i % 15 === 0) {
        console.log('fizzbuzz');
    }
     else if (i % 3 === 0) {
        console.log('fizz');
    }
    else if (i % 5 === 0) {
        console.log('buzz');
    }
    else {
        console.log(i);
    }
}
