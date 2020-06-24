const User = require('../model/User');

module.exports = {
    currentUser: {},
    authorizedUser : {id: 0, name: 'admin', email: 'admin@gmail.com', password: 'qwerty'},
    users : {},

    getUsers() {
        return this.users;
    },

    addUser(name, email, password){
        const user = new User(name, email, password);
        this.users[user.id] = user;
        this.currentUser = user;
        return user;
    },

    checkIfUserExists(id) {
        return Object.keys(this.users).includes(`${id}`);
    },

    getUserById(id) {
        if (!this.checkIfUserExists(id)) {
            return {};
        } else {
            return this.users[id];
            }
        },

    updateUserById(props, id) {
        if (!this.checkIfUserExists(id)) {
            return {};
        } else {
            for (let propKey of Object.keys(props)) {
                if (propKey !== 'id') {
                    this.users[id][propKey] = props[propKey];
                }
            }
        }
    },

    deleteUserById(id) {
        if (!this.checkIfUserExists(id)) {
            return -1;
        } else {
            delete this.users[id];
        }
    },

    isAuthorizedUser(){
        return this.currentUser.id === this.authorizedUser.id
            && this.currentUser.name === this.authorizedUser.name
            && this.currentUser.email === this.authorizedUser.email
            && this.currentUser.password === this. authorizedUser.password
    },

    getProfile() {
        if(this.isAuthorizedUser()) {
            return this.currentUser;
        } else {
            return {};
        }
    },
};
