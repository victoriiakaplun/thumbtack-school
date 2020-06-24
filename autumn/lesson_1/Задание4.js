'use strict';

class Animal{

    constructor(name, color){
        this.name = name;
        this.color = color;
        this.energy = 0;
    }

    getAnimalInfo(){
        return  this.name + ' ' + this.color;
    }

    eat(energy){
        this.energy = (this.energy + energy > 100) ? 100 : (this.energy + energy);
    }

    say(message){
        console.log(message);
    }
}

class Cat extends Animal{

    constructor(name, color) {
        super(name, color);
        this.energy = 50;
    }

    say(message) {
        super.say('Meow ' + message);
    }

    eat(energy){
        super.eat(energy);
        if(this.energy < 100){
            this.say('Not enough food');
        }
    }

    catchMouse(){
        (this.energy > 20) ? (this.energy = this.energy - 20) && this.say('Mouse caugh') : this.say('Not enough energy');
    }
}

class BritishShorthair extends Cat{
    catchMouse(){
        super.catchMouse();
        super.say('I`m too lazy');
    }
}

class Dog extends Animal {

    constructor(name, color) {
        super(name, color);
        this.energy = 75;
    }

    say(message) {
        super.say('Woof ' + message);
    }
    getEnergy(){
        console.log(this.energy);
    }

    eat(energy) {
        super.eat(energy);
        if(this.energy < 100){
            this.say('Not enough food');
        }
    }

    guard(){
        (this.energy > 25) ? (this.energy = this.energy - 25) && this.say('You are protected') : this.say('Not enough energy');
    }
}

class PitBull extends Dog{
    guard() {
        super.guard();
        super.say('You are absolutely protected!');
    }
}

