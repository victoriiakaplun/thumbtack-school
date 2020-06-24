'use strict';

const Sequelize = require('sequelize');

module.exports = sequelize => {
  const Order = sequelize.define('Order', {
    id: {
      type: Sequelize.INTEGER,
      autoIncrement: true,
      primaryKey: true,
      allowNull: false,
    },
    status: {
      type: Sequelize.ENUM({ values: ['CREATED', 'DELIVERING', 'DELIVERED', 'CANCELED', 'DONE'] }),
      allowNull: false,
    },
  });

  Order.associate = models => {
    Order.belongsTo(models.User, { foreignKey: 'client_id', as: 'Client' });
    Order.belongsTo(models.User, { foreignKey: 'courier_id', as: 'Courier' });
    Order.hasOne(models.Product);
  };

  return Order;
};
