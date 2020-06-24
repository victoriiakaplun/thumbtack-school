import React from 'react';

const Button = ({ onClick, children, type }) => (
  // eslint-disable-next-line react/button-has-type
  <button type={type} className="button" style={{ backgroundColor: '#FFA07A' }} onClick={onClick}>
    {children}
  </button>
);
export default Button;
