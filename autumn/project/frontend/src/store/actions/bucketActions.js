export const ADD_PRODUCT_TO_BUCKET = 'ADD_PRODUCT_TO_BUCKET';
export function addProductToBucket(product) {
  return {
    type: ADD_PRODUCT_TO_BUCKET,
    payload: product,
  };
}

export const REMOVE_PRODUCT_FROM_BUCKET = 'REMOVE_PRODUCT_FROM_BUCKET';
export function removeProductFormBucket(id) {
  return {
    type: REMOVE_PRODUCT_FROM_BUCKET,
    payload: id,
  };
}
