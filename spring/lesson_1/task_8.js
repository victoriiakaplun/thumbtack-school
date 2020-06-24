'use strict';

class Animal {

    constructor(name, color) {
        this.name = name;
        this.color = color;
        this.energy = 0;
    }

    getAnimalInfo() {
        return `${this.name} ${this.color}`
    }

    eat(energy) {
        const newEnergy = this.energy + energy;
        this.energy = (newEnergy > 100)
            ? 100
            : newEnergy;
    }

    say(message) {
        console.log(message);

    }
}

module.exports = Animal;
