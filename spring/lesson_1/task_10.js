'use strict';

const Animal = require('./task_8');

class Dog extends Animal {

    constructor(name, color) {
        super(name, color);
        this.energy = 75;
    }

    say(message) {
        super.say(`Woof ${message}`);
    }

    eat(energy) {
        super.eat(energy);
        if(this.energy < 100){
            this.say('Not enough food');
        }
    }

    guard() {
        (this.energy > 25)
            ? (this.energy = this.energy - 25) && this.say('You are protected')
            : this.say('Not enough energy');
    }
}

class PitBull extends Dog{
    guard() {
        super.guard();
        super.say('You are absolutely protected!');
    }
}
