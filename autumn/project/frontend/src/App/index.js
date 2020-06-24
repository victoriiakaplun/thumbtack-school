import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import Scene from './Scene';
import Navigation from './Navigation';
import Products from '../scenes/products/Products';
import Register from '../scenes/register/Register';
import UserProfile from '../scenes/profile/UserProfile';
import { fetchMe } from '../services/api';
import { setUser } from '../store/actions/userActions';
import { setProducts } from '../store/actions/productActions';

import NotificationsContainer from './notification/NotificationsContainer';

function App() {
  function useStore() {
    const dispatch = useDispatch();
    useEffect(() => {
      async function fetchData() {
        const user = await fetchMe();
        const products = await fetchAllProducts();
        dispatch(setUser(user));
        dispatch(setProducts(products));
      }
      fetchData();
    }, [dispatch]);
  }

  useStore();

  return (
    <Router>
      <div>
        <Scene>
          <Navigation />
          <NotificationsContainer />
        </Scene>
        <Switch>
          <Route path="/register">
            <Register />
          </Route>
          <Route path="/login">
            <Register />
          </Route>
          <Route path="/products">
            <Products />
          </Route>
          <Route path="/profile">
            <UserProfile />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
