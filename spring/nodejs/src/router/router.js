const routerMap = require('./routerMap');

module.exports = {
    define(request, response) {
        let requestTypeStr = `${request.method} ${request.url}`;
        console.log('request type: ' , requestTypeStr);
        let requestKey =  Object.keys(routerMap.routerMap).find((key) => requestTypeStr.match(key));
        console.log('request key: ', requestKey);
        routerMap.routerMap[requestKey](request, response);
    }
};
