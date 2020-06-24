'use strict';

const userData = ['Male', 'Ivan', 'Ivanov', 'Omsk', 'Russia', 19, 'Batman', 'Iron Man', 'Scrubs'];

let [lastName, firstName, age = null, ...films]  = [userData[2], userData[1], userData[5], userData[6], userData[7], userData[8]];

console.log(lastName, firstName, age, films);


