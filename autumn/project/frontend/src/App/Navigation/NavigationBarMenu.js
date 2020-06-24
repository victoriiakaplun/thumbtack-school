import React from 'react';
import { faUser, faShoppingBasket } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useSelector } from 'react-redux';
import NavBarItem from './NavigationBarItem';

function NavigationBarMenu() {
  const user = useSelector(state => state.user);

  return (
    <div className="navbar-menu">
      <div className="navbar-start">
        <NavBarItem to="/products">Product</NavBarItem>
        <NavBarItem to="">Delivery</NavBarItem>
        <NavBarItem to="">Orders</NavBarItem>
        <NavBarItem to="">Requests</NavBarItem>
      </div>
      <div className="navbar-end">
        <NavBarItem to="">
          <FontAwesomeIcon
            css={{ color: 'gainsboro', marginRight: '5px' }}
            icon={faShoppingBasket}
          />
        </NavBarItem>
        <NavBarItem to="/profile">
          <FontAwesomeIcon css={{ color: 'gainsboro', marginRight: '10px' }} icon={faUser} />
          {user && user.name}
        </NavBarItem>
      </div>
    </div>
  );
}

export default NavigationBarMenu;
