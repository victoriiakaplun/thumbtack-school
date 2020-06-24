'use strict';
const { authenticateCheck } = require('../middlewares/authHandler');

const Router = require('koa-router');
const orderController = require('../controllers/orderController');

module.exports = new Router({
  prefix: '/orders',
})
  .post('/', authenticateCheck, orderController.createOrder)
  .get('/:id', authenticateCheck, orderController.getOrderInfo)
  .put('/:id', authenticateCheck, orderController.updateOrder);
