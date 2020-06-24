import React from 'react';
import classNames from 'classnames';

import styles from './Header.scss';

function Header({ children }) {
  return <h1 className={classNames(styles.header, 'title')}>{children}</h1>;
}

export default Header;
