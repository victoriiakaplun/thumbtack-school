import React from 'react';
import { connect } from 'react-redux';
import ProductCard from './ProductCard';
import FlexBox from '../../components/common/FlexBox';
import { addNotification } from '../../store/actions/notificationActions';

function Products({ products }) {
  function add() {
    dispatch(addNotification({ message: 'Product added to cart!' }));
  }
  return (
    <FlexBox>
      {products.map(product => (
        <ProductCard
          imgUrl={product.imageUrl}
          title={product.name}
          description={product.description}
          price={products.price}
          notify={add}
        />
      ))}
    </FlexBox>
  );
}

const mapStateToProps = state => ({
  products: state.products,
});

export default connect(mapStateToProps)(Products);
