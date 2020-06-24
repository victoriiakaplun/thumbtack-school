'use strict';


function exclude(arr,...numbers){
    return arr.filter(item => !numbers.includes(item));
}

console.log(exclude([1, 2, 3], 1));
console.log(exclude([1, 2, 3], 1, 2));
console.log(exclude([1, 2, 3], 1, 2, 3));
