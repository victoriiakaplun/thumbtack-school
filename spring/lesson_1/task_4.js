'use strict';

const required = () => { throw new Error("Missing param"); };

function printMessage(message = required(), date = new Date().toTimeString()) {
    console.log(`[${date}] ${message}`);
}

try {
    printMessage("Message");
    printMessage();
} catch(e){
    console.log(`${e.name}: ${e.message}`);
}
