const database = require('./database')
const Url = require('url')

const orderValidMap = {
        status: (str) => !!str && (str === 'open' || str === 'in progress' || str === 'closed'),
        customerId: (num) => !!num && num > 0,
        courierId: (num) => !!num && num > 0,
        productsIds: (nums) => nums.every((num) => !!num && num > 0),
};

module.exports = {

    addOrder(request, response) {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        });

        request.on('end', () => {
            body = Buffer.concat(body).toString();
            const { customerId, productsIds } = JSON.parse(body);
            if(!Object.entries({ customerId, productsIds }).every(([key, value]) => {
                if(!!orderValidMap[key]) {
                    return orderValidMap[key](value);
                }
                return false;
            })) {
                response.statusCode = 400;
                response.end('ERROR: bad request');
            }
            const id = database.addOrder(customerId, productsIds);
            if( id < 0 ) {
                response.statusCode = 404;
                response.end('ERROR: Not found request data');
            }
            response.statusCode = 201;
            response.end(JSON.stringify(database.getOrderById(id)));
        });
    },

    getOrder(request, response) {
        const parsedUrl = Url.parse(request.url, true);
        const result = database.getOrderById(parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1));
        if(result < 0){
            response.statusCode = 404;
            response.end('ERROR: Order not found');
        }
        response.statusCode = 200;
        response.end(JSON.stringify(result));
    },

    changeOrder(request, response) {
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
                if(!!orderValidMap[key]) {
                    return orderValidMap[key](value);
                }
                    return false;
            })) {
                response.statusCode = 400;
                response.end('ERROR: bad request');
            }
            const res = database.changeOrderById(props, id);
            if( res < 0 ) {
                response.statusCode = 404;
                response.end('ERROR: Product not found');
            }
            response.statusCode = 200;
            response.end(JSON.stringify(database.getOrderById(id)));
        });
    },

    deleteOrder(request, response) {
        const parsedUrl = Url.parse(request.url, true);
        const id = parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1);
        const res = database.deleteOrderById(id);
        if(res < 0 ) {
            response.statusCode = 404;
            response.end('ERROR: order not found');
        }
        response.statusCode = 204;
        response.end('Order was successfully deleted');
    },
}