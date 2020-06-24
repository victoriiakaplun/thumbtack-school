module. exports = {
    validMap: {
        'name': (name) => name.length > 0,
        'email': (email) => email.length > 6,
        'password': (pswd) => pswd.length > 5,
    },
};
