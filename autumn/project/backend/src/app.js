'use strict';
const path = require('path');
const Koa = require('koa');
const appRouter = require('./router');
const koaBody = require('koa-body');
const serve = require('koa-static');
const send = require('koa-send');
const passport = require('./controllers/authController').passport;
const session = require('koa-session');
const SequelizeSessionStore = require('koa-generic-session-sequelize');
const { sequelize } = require('./db/models');
const staticDir = path.resolve(__dirname, '..', '../frontend', 'public');
const app = new Koa();

const COOKIE_MAX_AGE = 24 * 60 * 60 * 1000;

app.use(koaBody());
app.use(serve(staticDir));

app.keys = ['secret'];
const sessionOptions = {
  key: 'koa:sess',
  renew: true,
  maxAge: COOKIE_MAX_AGE,
  resave: false,
  saveUninitializes: false,
  store: new SequelizeSessionStore(sequelize, {
    tableName: 'sessions',
  }),
};

app.use(session(sessionOptions, app));

app.use(passport.initialize());
app.use(passport.session());

app.use(appRouter);

app.use(async ctx => {
  if (!ctx.request.path.startsWith('/api/v1/')) {
    await send(ctx, 'index.html', { root: staticDir });
  }
});

app.listen(3000, () => {});
