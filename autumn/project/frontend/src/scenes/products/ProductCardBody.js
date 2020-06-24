import React from 'react';
import Header from '../../components/common/header/Header';

function ProductCardBody({ title, description, price, children }) {
  return (
    <div className="media-content is-centered" style={{ textAlign: 'center' }}>
      <Header className="title">{title}</Header>
      <p>{description}</p>
      <br />
      <p>{price}</p>
      <br />
      <p>{children}</p>
    </div>
  );
}

export default ProductCardBody;
