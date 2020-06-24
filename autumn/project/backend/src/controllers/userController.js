'use strict';
const { User } = require('../db/models');
const HttpStatus = require('http-status-codes');
const { getIdFromUrl } = require('../helpers/helper');

async function getAllUsers(ctx) {
  const users = await User.findAll();
  if (users) {
    users.length === 0 ? (ctx.response.body = 'No users') : (ctx.response.body = users);
    ctx.response.status = HttpStatus.OK;
    return ctx.response;
  }
  ctx.response.status = HttpStatus.BAD_REQUEST;
  ctx.response.body = 'Users Not Found';
  return ctx.response;
}

async function getCurrentUserProfile(ctx) {
  const user = ctx.state.user;
  if (user) {
    ctx.response.body = {
      id: user.id,
      name: user.name,
      email: user.email,
      phone: user.phone,
      balance: user.balance,
    };
    ctx.response.status = HttpStatus.OK;
    return ctx.response;
  }
  ctx.response.body = 'User Not Found';
  ctx.response.status = HttpStatus.BAD_REQUEST;
  return ctx.response;
}

async function getUserInfo(ctx) {
  const userId = getIdFromUrl(ctx.request.url);
  if (userId <= 0) {
    ctx.response.body = 'Wrong user id';
    ctx.response.status = HttpStatus.BAD_REQUEST;
    return ctx.response;
  }
  const user = await User.findOne({ where: { id: userId } });
  if (user) {
    ctx.response.body = {
      id: user.id,
      name: user.name,
      email: user.email,
      phone: user.phone,
      balance: user.balance,
    };
    ctx.response.status = HttpStatus.OK;
    return ctx.response;
  }
  ctx.response.body = 'User Info Not Found';
  ctx.response.status = HttpStatus.BAD_REQUEST;
  return ctx.response;
}

async function updateUserInfo(ctx) {
  const userId = getIdFromUrl(ctx.request.url);
  if (userId <= 0) {
    ctx.response.body = 'Wrong user id';
    ctx.response.status = HttpStatus.BAD_REQUEST;
    return ctx.response;
  }
  const user = await User.findOne({ where: { id: userId } });
  if (user) {
    const { name, email, phone, balance } = ctx.request.body;
    const updatedUser = User.update(
      {
        name: name || user.name,
        email: email || user.email,
        phone: phone || user.phone,
        balance: balance || user.balance,
      },
      { where: { id: userId } },
    );
    if (updatedUser) {
      ctx.response.body = await User.findOne({ where: { id: userId } });
      ctx.response.status = HttpStatus.OK;
      return ctx.response;
    }
  }
  ctx.response.body = 'User Not Found';
  ctx.response.status = HttpStatus.BAD_REQUEST;
  return ctx.response;
}

async function deleteUser(ctx) {
  const userId = getIdFromUrl(ctx.request.url);
  if (userId <= 0) {
    ctx.response.body = 'Wrong user id';
    ctx.response.status = HttpStatus.BAD_REQUEST;
    return ctx.response;
  }
  const user = await User.findOne({ where: { id: userId } });
  if (user) {
    await User.destroy({ where: { id: userId } });
    ctx.response.body = 'Successfully deleted';
    ctx.response.status = HttpStatus.OK;
    return ctx.response;
  }
  ctx.response.body = 'User Not Found';
  ctx.response.status = HttpStatus.BAD_REQUEST;
  return ctx.response;
}

module.exports = {
  getAllUsers,
  updateUserInfo,
  deleteUser,
  getCurrentUserProfile,
  getUserInfo,
};
