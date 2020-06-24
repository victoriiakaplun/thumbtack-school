import React from 'react';

import Card from '../../components/common/Card';
import CardImage from '../../components/common/CardImage';
import ProductCardBody from './ProductCardBody';
import Button from '../../components/common/Button';

function ProductCard({ imgUrl, title, description, price, notify }) {
  return (
    <Card width="450px" margin="20px">
      <CardImage imgUrl={imgUrl} />
      <ProductCardBody title={title} description={description} price={price}>
        <Button type="button" onClick={notify}>
          Add to cart
        </Button>
      </ProductCardBody>
    </Card>
  );
}

export default ProductCard;
