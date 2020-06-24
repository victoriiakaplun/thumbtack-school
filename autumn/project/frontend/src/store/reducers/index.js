import { combineReducers } from 'redux';
import notificationReducer from './notificationReducer';
import bucketReducer from './bucketReducer';
import userReducer from './userReducer';
import productReducer from './productReducer';

const appReducer = combineReducers({
  notifications: notificationReducer,
  bucketReducer,
  userReducer,
  products: productReducer,
});

export default appReducer;
