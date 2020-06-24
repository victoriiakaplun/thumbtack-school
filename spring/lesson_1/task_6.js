'use strict';

const user = {
    name: 'Ivan',
    age: 18
};

function printUser(user = {}) {
    console.log(user.name, user.age);
}

printUser(user);
printUser({ name: 'Ivan' });
printUser({ age: 19 });
printUser({});
printUser();
