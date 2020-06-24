'use strict';

const { User, Product, Order } = require('../db/models');
const HttpStatus = require('http-status-codes');
const { getIdFromUrl } = require('../helpers/helper');
const sequelize = require('../db/models').sequelize;

module.exports = {
  async createOrder(ctx) {
    let transaction;
    try {
      transaction = await sequelize.transaction({ autocommit: false });
      const { productId } = ctx.request.body;
      const product = await Product.findOne({ where: { id: productId } }, { transaction });
      if (!product) {
        throw Error('Product Not Found');
      }
      const userId = ctx.state.user.id;
      const user = await User.findOne({ where: { id: userId } }, { transaction });
      const roles = await user.getUserRoles({ transaction });
      if (roles.every(role => role.dataValues.role !== 'CLIENT')) {
        throw Error('User Is Not A Client!');
      }
      if (product.price > user.balance) {
        throw Error('Not enought balance for order!');
      }
      const order = await Order.create({ status: 'CREATED' }, { transaction });
      if (!order) {
        throw Error('Order Was Not Created!');
      }
      await order.setProduct(product, { transaction });
      await order.setClient(user, { transaction });
      await order.setCourier(null, { transaction });
      await User.update(
        { balance: user.balance - product.price },
        { where: { id: user.id } },
        { transaction },
      );
      await transaction.commit();
      ctx.response.status = HttpStatus.CREATED;
      ctx.response.body = order;
      return ctx.response;
    } catch (e) {
      await transaction.rollback();
      ctx.response.status = HttpStatus.BAD_REQUEST;
      ctx.response.body = e.message;
      return ctx.response;
    }
  },

  async getOrderInfo(ctx) {
    const orderId = getIdFromUrl(ctx.request.url);
    const orderInfo = await Order.findOne({ where: { id: orderId } });
    if (!orderInfo) {
      ctx.response.status = HttpStatus.NOT_FOUND;
      ctx.response.body = 'Order Not Found';
      return ctx.response;
    }
    const productInfo = await orderInfo.getProduct();
    console.log(productInfo);
    const courier = await orderInfo.getCourier();
    const client = await orderInfo.getClient();
    const orderResponse = {
      id: orderInfo.id,
      status: orderInfo.status,
      product: {
        id: productInfo.id,
        name: productInfo.name,
        description: productInfo.description,
        price: productInfo.price,
      },
      courier:
        courier === null
          ? null
          : { id: courier.id, name: courier.name, email: courier.email, phone: courier.phone },
      client: {
        id: client.id,
        name: client.name,
        email: client.email,
        phone: client.phone,
      },
    };
    ctx.response.status = HttpStatus.OK;
    ctx.response.body = orderResponse;
    return ctx.response;
  },

  async updateOrder(ctx) {
    let transaction;
    try {
      transaction = await sequelize.transaction({ autocommit: false });
      const orderId = getIdFromUrl(ctx.request.url);
      const { status } = ctx.request.body;
      const order = await Order.findOne({ where: { id: orderId } }, { transaction });
      if (!order) {
        throw Error();
      }
      const currentUserId = ctx.state.user.dataValues.id;
      const currentUser = await User.findOne({ where: { id: currentUserId } }, { transaction });
      const orderClient = await order.getClient();
      const orderCourier = await order.getCourier();
      const orderProduct = await order.getProduct();
      if (
        currentUser.dataValues.id === orderClient.dataValues.id &&
        order.dataValues.status === 'DELIVERED' &&
        status === 'DONE'
      ) {
        await doneOrder(ctx, transaction, orderProduct, order, orderClient, orderCourier);
      }
      if (
        (currentUser.dataValues.id === orderClient.dataValues.id ||
          (orderCourier === null
            ? false
            : currentUser.dataValues.id === orderCourier.dataValues.id)) &&
        order.dataValues.status !== 'DONE' &&
        status === 'CANCELED'
      ) {
        await Order.update({ status: status }, { where: { id: orderId } }, { transaction });
        ctx.response.status = HttpStatus.OK;
        ctx.response.body = await Order.findOne({ where: { id: orderId } });
      }
      if (
        currentUser.dataValues.id !== orderClient.dataValues.id &&
        orderCourier === null &&
        order.dataValues.status === 'CREATED' &&
        status === 'DELIVERING'
      ) {
        await Order.update(
          { status: status },
          { where: { id: order.dataValues.id } },
          { transaction },
        );
        const updatedOrder = await Order.findOne(
          { where: { id: order.dataValues.id } },
          { transaction },
        );
        await updatedOrder.setCourier(currentUser, { transaction });
        ctx.response.status = HttpStatus.OK;
        ctx.response.body = await Order.findOne(
          { where: { id: order.dataValues.id } },
          { transaction },
        );
      }
      if (
        (orderCourier === null
          ? false
          : currentUser.dataValues.id === orderCourier.dataValues.id) &&
        order.dataValues.status === 'DELIVERING' &&
        status === 'DELIVERED'
      ) {
        const updatedOrder = await Order.update(
          { status: status },
          { where: { id: orderId } },
          { transaction },
        );
        ctx.response.status = HttpStatus.OK;
        ctx.response.body = updatedOrder;
      }
      await transaction.commit();
      return ctx.response;
    } catch (e) {
      ctx.response.status = HttpStatus.BAD_REQUEST;
      ctx.response.body = e.message;
      await transaction.rollback();
      return ctx.response;
    }
  },
};

async function doneOrder(ctx, transaction, product, order, client, courier) {
  const updatedOrder = await Order.update(
    {
      status: status,
    },
    { where: { id: order.dataValues.id } },
    { transaction },
  );
  await User.update(
    { balance: courier.dataValues.balance + 0.1 * order.dataValues.price },
    { where: { id: courier.dataValues.id } },
    { transaction },
  );
  ctx.response.body = updatedOrder;
  ctx.response.status = HttpStatus.OK;
}
