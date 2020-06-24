const http = require('http')
const router = require('./router.js')

const server = new http.Server();

console.log('Server is running on [127.0.0.1]:[9000]');

server.on('request', (request, response) => {
    try {
        router.define(request, response);
    } catch (e) {
        response.statusCode = 404;
        response.end('ERROR: bad request');
    }
});

server.listen(9000, '127.0.0.1');