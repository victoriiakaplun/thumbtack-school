import classNames from 'classnames';
import styled from '@emotion/styled';
import { faBoxOpen } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import NavBarItem from './NavigationBarItem';
import NavigationBarMenu from './NavigationBarMenu';

function NavigationBarBrand({ children }) {
  return (
    <Container>
      <nav
        className={classNames(styled.div, 'title')}
        role="navigation"
        aria-label="main navigation"
      >
        <div className="navbar-brand">
          <NavBarItem to="">
            <FontAwesomeIcon css={{ color: 'gainsboro', marginRight: '10px' }} icon={faBoxOpen} />
            {children}
          </NavBarItem>
          <NavigationBarMenu />
        </div>
      </nav>
    </Container>
  );
}

const Container = styled.div`
  background-color: #10316b;
`;

export default NavigationBarBrand;
