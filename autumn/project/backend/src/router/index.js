'use strict';

const Router = require('koa-router');

const authRouter = require('./authRouter');
const usersRouter = require('./usersRouter');
const productRouter = require('./productsRouter');
const ordersRouter = require('./ordersRouter');

const apiRouter = new Router();
const v1Router = new Router();

v1Router.use(
  '/v1',
  authRouter.routes(),
  usersRouter.routes(),
  productRouter.routes(),
  ordersRouter.routes(),
);
apiRouter.use('/api', v1Router.routes(), v1Router.allowedMethods);

module.exports = apiRouter.routes();
