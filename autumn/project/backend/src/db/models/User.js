'use strict';

const Sequelize = require('sequelize');

module.exports = sequelize => {
  const User = sequelize.define(
    'User',
    {
      id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        primaryKey: true,
        allowNull: false,
        unique: true,
      },
      name: {
        type: Sequelize.STRING,
        allowNull: false,
        validate: {
          notEmpty: {
            args: true,
            msg: 'Incorrect name!',
          },
        },
      },
      email: {
        type: Sequelize.STRING,
        allowNull: false,
        unique: true,
        validate: {
          isEmail: {
            args: true,
            msg: 'Incorrect email',
          },
        },
      },
      password: {
        type: Sequelize.STRING,
        allowNull: false,
      },
      phone: {
        type: Sequelize.STRING,
        allowNull: false,
        unique: true,
        validate: {
          isPhoneNumber(value) {
            const phoneRegex = /^[0-9]{11}$/;
            if (!phoneRegex.test(value)) {
              throw new Error('Wrong phone number!');
            }
          },
        },
      },
      balance: {
        type: Sequelize.INTEGER,
        allowNull: false,
        validate: {
          isPositive(value) {
            if (value < 0) {
              throw new Error('Balance must be positive!');
            }
          },
        },
      },
    },
    {
      underscored: true,
    },
  );

  User.associate = models => {
    User.hasMany(models.Order, { foreignKey: 'client_id' });
    User.hasMany(models.Order, { foreignKey: 'courier_id' });
    User.belongsToMany(models.UserRole, {
      through: 'UserUserRole',
    });
  };
  return User;
};
