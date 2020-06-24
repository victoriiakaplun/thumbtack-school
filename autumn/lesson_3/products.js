const database = require('./database')
const Url = require('url')

const productValidMap =  {
        name : (str) => !!str && str.length > 0,
        weight : (num) => !!num && num > 0,
    };

module.exports = {

    addProduct(request, response) {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        });

        request.on('end', () => {
            body = Buffer.concat(body).toString();
            const { name, weight } = JSON.parse(body);
            if(!Object.entries({ name, weight }).every(([key, value]) => {
                if(!!productValidMap[key]) {
                    return productValidMap[key](value);
                }
                return false;
            })) {
                response.statusCode = 400;
                response.end('ERROR: bad request');
            }
            const id = database.addProduct(name, weight);
            if( id < 0 ) {
                response.statusCode = 400;
                response.end('ERROR: Invalid input data');
            }
            response.statusCode = 201;
            response.end(JSON.stringify(database.getProductById(id)));
        });
    },

    getProducts(request, response) {
        response.statusCode = 200;
        const products = JSON.stringify(database.getProducts());
        response.end(products);
    },

    getProduct(request, response) {
        const parsedUrl = Url.parse(request.url, true);
        const result = database.getProductById(parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1));
        if(result < 0){
            response.statusCode = 404;
            response.end('ERROR: Product not found');
        }
        response.statusCode = 200;
        response.end(JSON.stringify(result));
    },

    changeProduct(request, response) {
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
                console.log('ENTRY: ', key, ' ', value);
                if(!!productValidMap[key]) {
                    return productValidMap[key](value);
                }
                    return false;
            })) {
                response.statusCode = 400;
                response.end('ERROR: bad request');
            }
            const res = database.changeProductById(props, id);
            if( res < 0 ) {
                response.statusCode = 404;
                response.end('ERROR: Order not found');
            }
            response.statusCode = 200;
            response.end(JSON.stringify(database.getProductById(id)));
        });
    },

    deleteProduct(request, response) {
        const parsedUrl = Url.parse(request.url, true);
        const id = parsedUrl.pathname.substring(parsedUrl.pathname.lastIndexOf('/') + 1);
        const res = database.deleteProductById(id);
        if(res < 0 ) {
            response.statusCode = 404;
            response.end('ERROR: not found product');
        }
        response.statusCode = 204;
        response.end('Product was successfully deleted');
    },
}