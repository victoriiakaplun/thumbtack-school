const database = require('./database')
const Url = require('url')

const clientValidMap = {
        firstName: (name) => !!name && name.length > 3,
        lastName: (name) => !!name && name.length > 3,
        age: (age) => !!age && age > 1,
    };

module.exports = {

    addClient(request, response) {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        });

        request.on('end', () => {
            body = Buffer.concat(body).toString();
            const { firstName, lastName, age } = JSON.parse(body);
            if(!Object.entries({firstName, lastName, age}).every(([key, value]) => {
                if(!!clientValidMap[key]) {
                    return clientValidMap[key](value);
                }
                return false;
            })) {
                response.statusCode = 400;
                response.end('ERROR: bad request');
            }
            const id = database.addClient(firstName, lastName, age);
            if( id < 0 ) {
                response.statusCode = 400;
                response.end('ERROR: Invalid input data');
            }
            response.statusCode = 201;
            response.end(JSON.stringify(database.getClientById(id)));
        });
    },

    getClients(request, response) {
        response.statusCode = 200;
        const clients = JSON.stringify(database.getClients());
        response.end(clients);
    },

    getClient(request, response) {
        const parsedUrl = Url.parse(request.url, true);
        const result = database.getClientById(parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1));
        if(result < 0) {
            response.statusCode = 404;
            response.end('ERROR: Client not found');
        }
        response.statusCode = 200;
        response.end(JSON.stringify(result));
    },

    changeClient(request, response) {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        });

        request.on('end', () => {
            body = Buffer.concat(body).toString();
            const parsedUrl = Url.parse(request.url, true);
            const id = parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1);
            const props = JSON.parse(body);
            if(!Object.entries(props).every(([key, value]) => {
               if(!!clientValidMap[key]) {
                    return clientValidMap[key](value);
                }
                    return false;
            })) {
                response.statusCode = 400;
                response.end('ERROR: bad request');
             }
            const res = database.changeClientById(props, id);
            if( res < 0 ) {
                response.statusCode = 404;
                response.end('ERROR: Client not found');
            }
            response.statusCode = 200;
            response.end(JSON.stringify(database.getClientById(id)));
        });
    },

    deleteClient(request, response) {
        const parsedUrl = Url.parse(request.url, true);
        const id = parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1);
        const res = database.deleteClientById(id);
        if(res < 0 ) {
            response.statusCode = 404;
            response.end('ERROR: not found client');
        }
        response.statusCode = 204;
        response.end('Client was successfully deleted');
    },
}

