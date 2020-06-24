import { getAllProducts } from '../../services/api';

export const SET_PRODUCTS = 'SET_PRODUCTS';
export function setProducts(products) {
  return {
    type: SET_PRODUCTS,
    payload: products,
  };
}

export function getProducts() {
  return dispatch => getAllProducts().then(products => dispatch(setProducts(products)));
}
