module. exports = {
    clientValidMap: {
        'firstName': (name) => name.length() > 3,
        'lastName': (name) => name.length() > 3,
        'age': (age) => age > 1,
    },
};