const http = require('http');
const router = require('./router/router.js');
const errors = require('./helpers/errors');

const server = new http.Server();

console.log('Server is running on [127.0.0.1]:[9000]');

server.on('request', (request, response) => {
    try {
        router.define(request, response);
    } catch (e) {
        response.statusCode = 400;
        response.end(errors['BAD_REQUEST']);
    }
});

server.listen(9000, '127.0.0.1');
