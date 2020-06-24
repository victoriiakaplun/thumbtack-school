'use strict';

function print(arr = [], separator = '-') {
    console.log(arr.join(separator));
}

console.log("First call::")
print([1, 2, 3], ',');
console.log("Second call::")
print([1, 2, 3]);
console.log("Third call::")
print();


const required = () => { throw new Error("Missing param");};

function printMessage(message = required(), date = new Date().toTimeString()) {
    console.log('[' + date + ']' + message);
}
try {
    printMessage("Message");
    printMessage();
}catch(e){
    console.log(e.name + ': ' + e.message);
}


function exclude(arr,...numbers){
    return arr.filter(item => !numbers.includes(item));
}

console.log(exclude([1, 2, 3], 1));
console.log(exclude([1, 2, 3], 1, 2));
console.log(exclude([1, 2, 3], 1, 2, 3));





const user = {
    name: 'Ivan',
    age: 18
};

function printUser(user = null) {
    console.log(user);
}

printUser(user);
printUser({name: 'Ivan'});
printUser({age: 19});
printUser({});
printUser();