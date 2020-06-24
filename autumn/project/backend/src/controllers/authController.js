'use strict';

const passport = require('koa-passport');
const bcrypt = require('bcrypt');
const { User, UserRole } = require('../db/models');
const LocalStrategy = require('passport-local').Strategy;
const HttpStatus = require('http-status-codes');
const sequelize = require('../db/models').sequelize;

const PASSWORD_LENGTH = 8;
const SALT_ROUNDS = 10;

passport.serializeUser(async (user, done) => {
  try {
    if (!user) {
      return done(null, false);
    }
    return done(null, user.id);
  } catch (err) {
    return done(err);
  }
});

passport.deserializeUser(async (id, done) => {
  try {
    const user = await User.findOne({ where: { id } });
    if (!user) {
      return done(null, false);
    }
    return done(null, user);
  } catch (err) {
    return done(err);
  }
});

const localStrategyOptions = {
  usernameField: 'email',
  passwordField: 'password',
  passReqToCallback: true,
  session: true,
};

passport.use(new LocalStrategy(localStrategyOptions, verify));

async function verify(ctx, email, password, done) {
  const userInfo = await User.findOne({ where: { email } });
  if (!userInfo) {
    ctx.response.status = HttpStatus.BAD_REQUEST;
    return done(null, false, { message: 'Incorrect username.' });
  } else {
    const isValidPassword = await bcrypt.compare(password, userInfo.password);
    if (isValidPassword === false) {
      ctx.response.status = HttpStatus.BAD_REQUEST;
      return done(null, false, { message: 'Incorrect password.' });
    }
    const userRoles = userInfo.getUserRoles();
    const user = {
      id: userInfo.id,
      name: userInfo.name,
      email: userInfo.email,
      phone: userInfo.phone,
      balance: userInfo.balance,
      roles: userRoles,
    };
    ctx.response.status = HttpStatus.OK;
    return done(null, user);
  }
}

async function login(ctx, next) {
  await passport.authenticate('local', async (err, user) => {
    if (!err && user) {
      await ctx.login(user);
      console.log(user);
      const userData = await {
        id: user.id,
        name: user.name,
        email: user.email,
        phone: user.phone,
        balance: user.balance,
        roles: user.roles,
      };
      ctx.state.user = userData;
      ctx.response.status = HttpStatus.OK;
      ctx.response.body = userData;
      return ctx.response;
    } else {
      ctx.response.status = HttpStatus.BAD_REQUEST;
      ctx.response.body = 'Cant sign in with current params';
      return ctx.response;
    }
  })(ctx, next);
}

async function register(ctx) {
  let transaction;
  try {
    transaction = await sequelize.transaction({ autocommit: false });
    const { name, email, phone, password, roles } = ctx.request.body;
    if (await User.findOne({ where: { email } }, { transaction })) {
      throw Error('User with this email already exists');
    }
    if (await User.findOne({ where: { phone } }, { transaction })) {
      throw Error('User with this phone already exists!');
    }
    if (password.length < PASSWORD_LENGTH) {
      throw Error('Password length must be more then 8 symbols');
    }
    const salt = await bcrypt.genSalt(SALT_ROUNDS);
    const hashedPassword = await bcrypt.hash(password, salt);
    const user = {
      name: name,
      email: email,
      phone: phone,
      password: hashedPassword,
      balance: 0,
    };
    const createdUserInfo = await User.create(user, { transaction });
    const rolesArray = await UserRole.findAll({ transaction });
    const userRoles = rolesArray.filter(role => roles.some(r => r === role.dataValues.role));
    if (userRoles.length <= 0) {
      throw Error('No valid roles for user!');
    }
    for (const userRole of userRoles) {
      await createdUserInfo.addUserRole(userRole, { transaction });
    }
    const createdUser = {
      name: createdUserInfo.name,
      email: createdUserInfo.email,
      phone: createdUserInfo.phone,
      balance: createdUserInfo.balance,
      roles: userRoles,
    };
    ctx.response.status = HttpStatus.CREATED;
    ctx.response.body = createdUser;
    await transaction.commit();
    return ctx.response;
  } catch (e) {
    if (transaction) {
      await transaction.rollback();
    }
    ctx.response.status = HttpStatus.BAD_REQUEST;
    ctx.response.body = e.message;
    return ctx.response;
  }
}

async function logout(ctx) {
  try {
    ctx.logout();
    ctx.response.status = HttpStatus.OK;
  } catch (err) {
    ctx.response.status = HttpStatus.BAD_REQUEST;
  }
}

//TODO
//async function getUserOrders(ctx) {}

module.exports = { passport, login, register, logout /*getUserOrders*/ };
