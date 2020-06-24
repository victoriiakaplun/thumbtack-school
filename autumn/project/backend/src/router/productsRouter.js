'use strict';
const { authenticateCheck } = require('../middlewares/authHandler');

const Router = require('koa-router');
const productController = require('../controllers/productController');

module.exports = new Router({
  prefix: '/products',
})
  .post('/', authenticateCheck, productController.addProduct)
  .get('/', productController.getProducts)
  .get('/:id', authenticateCheck, productController.getProductInfo)
  .put('/:id', authenticateCheck, productController.updateProductInfo)
  .delete('/:id', authenticateCheck, productController.deleteProduct);
