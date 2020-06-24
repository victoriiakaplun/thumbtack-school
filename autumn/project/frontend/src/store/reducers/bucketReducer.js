import uuid from 'uuid/v4';
import { ADD_PRODUCT_TO_BUCKET, REMOVE_PRODUCT_FROM_BUCKET } from '../actions/bucketActions';

export function getInitialBucketState() {
  return {
    bucket: [],
  };
}

function bucketReducer(prevState, recAction) {
  const handlerMap = {
    [ADD_PRODUCT_TO_BUCKET]: (state, action) => ({
      ...state,
      bucket: [...state.bucket, { ...action.payload, id: uuid() }],
    }),

    [REMOVE_PRODUCT_FROM_BUCKET]: (state, action) => ({
      ...state,
      notifications: state.bucket.filter(({ id: bucketId }) => action.payload !== bucketId),
    }),
  };

  const handler = handlerMap[recAction.type];
  return handler ? handler(prevState, recAction) : getInitialBucketState();
}

export default bucketReducer;
