const users = require('../service/users');
module.exports = {
    routerMap: {
        'POST \/users\/register' : users.register,
        'GET \/users\/profile' : users.getProfile,
        'GET \/users\/*' : users.getUser,
        'PUT \/users\/*' : users.updateUser,
        'DELETE \/users\/*' : users.deleteUser,
        'GET \/users\/' : users.getUsers,
    },
};
