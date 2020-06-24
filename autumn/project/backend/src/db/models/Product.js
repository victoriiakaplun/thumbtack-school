'use strict';

const Sequelize = require('sequelize');

module.exports = sequelize => {
  const Product = sequelize.define(
    'Product',
    {
      id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        primaryKey: true,
        allowNull: false,
      },
      name: {
        type: Sequelize.STRING,
        allowNull: false,
        validate: {
          notEmpty: {
            args: true,
            msg: 'Incorrrect name!',
          },
        },
      },
      description: {
        type: Sequelize.STRING,
        allowNull: false,
        validate: {
          notEmpty: {
            args: true,
            msg: 'Incorrrect name!',
          },
        },
      },
      price: {
        type: Sequelize.INTEGER,
        allowNull: false,
        validate: {
          isPositive(value) {
            if (value <= 0) {
              throw new Error('Price must be more than 0!');
            }
          },
        },
      },
      imageUrl: {
        type: Sequelize.STRING,
        allowNull: false,
        validate: {
          isPositive(value) {
            if (value < 0) {
              throw new Error('Wrong imageUrl!');
            }
          },
        },
      },
    },
    {
      underscored: true,
    },
  );

  return Product;
};
