'use strict';

const Sequelize = require('sequelize');

module.exports = sequelize => {
  const UserRole = sequelize.define(
    'UserRole',
    {
      role: {
        type: Sequelize.ENUM({
          values: ['CLIENT', 'COURIER'],
        }),
      },
    },
    {
      underscored: true,
    },
  );

  UserRole.associate = models => {
    UserRole.belongsToMany(models.User, {
      through: 'UserUserRole',
    });
  };
  return UserRole;
};
