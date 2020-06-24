const database = require('../db/database');
const Url = require('url');
const { validMap } = require('../helpers/valid');
const errors = require('../helpers/errors');

module.exports = {

    getUsers(request, response) {
        response.statusCode = 200;
        const clients = JSON.stringify(database.getUsers());
        response.end(clients);
    },

    register(request, response) {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        });
        request.on('end', () => {
            body = Buffer.concat(body).toString();
            const { name, email, password } = JSON.parse(body);
            if(!Object.entries({name, email, password}).every(([key, value]) => {
                if(!!validMap[key]) {
                    return validMap[key](value);
                }
                return false;
            })) {
                response.statusCode = 400;
                response.end(errors['BAD_REQUEST']);
            }
            const insertedUser = database.addUser(name, email, password);
            if( insertedUser.id < 0 ) {
                response.statusCode = 400;
                response.end(errors['INVALID_DATA']);
            }
            response.statusCode = 201;
            response.end(JSON.stringify(insertedUser));
        });
    },

    getUser(request, response) {
        const parsedUrl = Url.parse(request.url, true);
        const result = database.getUserById(parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1));
        if(Object.keys(result).length === 0) {
            response.statusCode = 404;
            response.end(errors['NOT_FOUND']);
        }
        response.statusCode = 200;
        const {id, name, email} = result;
        response.end(JSON.stringify({id, name, email}));
    },

    updateUser(request, response) {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        });
        request.on('end', () => {
            body = Buffer.concat(body).toString();
            const parsedUrl = Url.parse(request.url, true);
            const id = parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1);
            const props = JSON.parse(body);
            if (!Object.entries(props).every(([key, value]) => {
                if (!!validMap[key]) {
                    return validMap[key](value);
                }
                return false;
            })) {
                response.statusCode = 400;
                response.end(errors['NOT_FOUND']);
            }
            const updatedUser = database.updateUserById(props, id);
            if (Object.keys(updatedUser).length === 0) {
                response.statusCode = 404;
                response.end(errors['NOT_FOUND']);
            }
            response.statusCode = 200;
            response.end(JSON.stringify(updatedUser));
        });
    },

   deleteUser(request, response) {
       const parsedUrl = Url.parse(request.url, true);
       const id = parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1);
       const res = database.deleteUserById(id);
       if(res < 0 ) {
           response.statusCode = 404;
           response.end(errors['NOT_FOUND']);
       }
       response.statusCode = 204;
       response.end(errors['NO_ERROR']);
   },

    getProfile(request, response) {
        const profile = database.getProfile();
        if(Object.keys(profile).length === 0) {
            response.statusCode = 401;
            response.end(errors['UNAUTHORIZED']);
        }
        response.statusCode = 200;
        response.end(JSON.stringify(profile));
    }
};
