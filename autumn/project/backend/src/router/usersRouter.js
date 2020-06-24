'use strict';
const Router = require('koa-router');
const userController = require('../controllers/userController');
//const authController = require('../controllers/authController');
const { authenticateCheck } = require('../middlewares/authHandler');

module.exports = new Router({
  prefix: '/users',
})
  .get('/', authenticateCheck, userController.getAllUsers)
  .get('/profile', authenticateCheck, userController.getCurrentUserProfile)
  .get('/:id', authenticateCheck, userController.getUserInfo)
  .put('/:id', authenticateCheck, userController.updateUserInfo)
  .delete('/:id', authenticateCheck, userController.deleteUser);
//.get('/:id/orders', authenticateCheck, authController.getUserOrders);
