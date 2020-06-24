import React from 'react';

import NavigationBarBrand from './NavigationBarBrand';
import Header from '../../components/common/header/Header';

function Navigation() {
  return (
    <div>
      <NavigationBarBrand>
        <Header>
          <span>Delivery</span>
        </Header>
      </NavigationBarBrand>
    </div>
  );
}

export default Navigation;
