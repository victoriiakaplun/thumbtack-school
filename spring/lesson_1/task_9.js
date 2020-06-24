'use strict';

const Animal = require('./task_8');

class Cat extends Animal {

    constructor(name, color) {
        super(name, color);
        this.energy = 50;
    }

    say(message) {
        super.say(`Meow ${message}`);
    }

    eat(energy) {
        super.eat(energy);
        if(this.energy < 100){
            this.say('Not enough food');
        }
    }

    catchMouse() {
        (this.energy > 20)
            ? (this.energy = this.energy - 20) && this.say('Mouse caugh')
            : this.say('Not enough energy');
    }
}

class BritishShorthair extends Cat {
    catchMouse() {
        super.catchMouse();
        super.say('I`m too lazy');
    }
}
