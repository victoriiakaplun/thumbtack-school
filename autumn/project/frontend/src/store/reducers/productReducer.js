import { SET_PRODUCTS } from '../actions/productActions';

export function getInitialProductState() {
  return {
    products: [],
  };
}

function productReducer(prevState, recAction) {
  const handlerMap = {
    [SET_PRODUCTS]: (state, action) => ({
      ...state,
      products: action.payload,
    }),
  };
  const handler = handlerMap[recAction.type];
  return handler ? handler(prevState, recAction) : getInitialProductState;
}

export default productReducer;
