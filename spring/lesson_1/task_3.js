'use stict';

function print(arr= [], separator = '-') {
    console.log(arr.join(separator));
}

print();
print([1,2,3], ',');
print([1,2,3]);
