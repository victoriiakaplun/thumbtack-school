'use strict';
const HttpStatus = require('http-status-codes');
const { Product } = require('../db/models');
const { getIdFromUrl } = require('../helpers/helper');

async function addProduct(ctx) {
  const { name, description, price, imageUrl } = ctx.request.body;
  if (await Product.findOne({ where: { name } })) {
    ctx.response.status = HttpStatus.BAD_REQUEST;
    ctx.response.body = 'Product Already Exists';
    return ctx.response;
  }
  const product = {
    name: name,
    description: description,
    price: price,
    imageUrl: imageUrl,
  };
  try {
    const createdProduct = await Product.create(product);
    ctx.response.status = HttpStatus.CREATED;
    ctx.response.body = createdProduct;
    return ctx.response;
  } catch (err) {
    ctx.response.status = HttpStatus.BAD_REQUEST;
    console.log(err);
    ctx.response.body = err.errors[0].message;
    return ctx.response;
  }
}

async function getProducts(ctx) {
  const products = await Product.findAll();
  if (products) {
    products.length === 0 ? (ctx.response.body = 'No products') : (ctx.response.body = products);
    ctx.response.status = HttpStatus.OK;
    return ctx.response;
  }
  ctx.response.status = HttpStatus.BAD_REQUEST;
  ctx.response.body = 'Products Not Found';
  return ctx.response;
}

async function getProductInfo(ctx) {
  const prodId = getIdFromUrl(ctx.request.url);
  if (prodId <= 0) {
    ctx.response.body = 'Wrong product id';
    ctx.response.status = HttpStatus.BAD_REQUEST;
    return ctx.response;
  }
  const product = await Product.findOne({ where: { id: prodId } });
  if (product) {
    ctx.response.body = product;
    ctx.response.status = HttpStatus.OK;
    return ctx.response;
  }
  ctx.response.body = 'Product Info Not Found';
  ctx.response.status = HttpStatus.BAD_REQUEST;
  return ctx.response;
}

async function updateProductInfo(ctx) {
  const prodId = getIdFromUrl(ctx.request.url);
  if (prodId <= 0) {
    ctx.response.body = 'Wrong product id';
    ctx.response.status = HttpStatus.BAD_REQUEST;
    return ctx.response;
  }
  const product = await Product.findOne({ where: { id: prodId } });
  if (product) {
    const { name, description, price, imageUrl } = ctx.request.body;
    const updatedProduct = Product.update(
      {
        name: name || product.name,
        description: description || product.description,
        price: price || product.price,
        imageUrl: imageUrl || product.imageUrl,
      },
      { where: { id: prodId } },
    );
    if (updatedProduct) {
      ctx.response.body = await Product.findOne({ where: { id: prodId } });
      ctx.response.status = HttpStatus.OK;
      return ctx.response;
    }
  }
  ctx.response.body = 'Product Not Found';
  ctx.response.status = HttpStatus.BAD_REQUEST;
  return ctx.response;
}

async function deleteProduct(ctx) {
  const prodId = getIdFromUrl(ctx.request.url);
  if (prodId <= 0) {
    ctx.response.body = 'Wrong product id';
    ctx.response.status = HttpStatus.BAD_REQUEST;
    return ctx.response;
  }
  const product = await Product.findOne({ where: { id: prodId } });
  if (product) {
    await Product.destroy({ where: { id: prodId } });
    ctx.response.body = 'Successfully deleted';
    ctx.response.status = HttpStatus.OK;
    return ctx.response;
  }
  ctx.response.body = 'Product Not Found';
  ctx.response.status = HttpStatus.BAD_REQUEST;
  return ctx.response;
}

module.exports = { addProduct, updateProductInfo, deleteProduct, getProductInfo, getProducts };
