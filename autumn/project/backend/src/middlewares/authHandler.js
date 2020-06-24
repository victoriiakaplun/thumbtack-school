'use strict';
const HttpStatus = require('http-status-codes');

async function authenticateCheck(ctx, next) {
  if (ctx.isAuthenticated()) {
    await next();
  } else {
    ctx.response.status = HttpStatus.UNAUTHORIZED;
    ctx.response.body = 'Unauthenticated';
    return ctx.response;
  }
}

async function unauthenticateCheck(ctx, next) {
  if (ctx.isUnauthenticated()) {
    await next();
  } else {
    ctx.response.status = 400;
    return ctx.response;
  }
}

module.exports = { authenticateCheck, unauthenticateCheck };
