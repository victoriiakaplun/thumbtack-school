import React from 'react';

import { useSelector, useDispatch } from 'react-redux';
import NotificationList from './NotificationList';
import { removeNotification } from '../../store/actions/notificationActions';

function NotificationContainer() {
  const dispatch = useDispatch();
  const notifications = useSelector(state => state.notifications.notifications);

  function remove(id) {
    dispatch(removeNotification(id));
  }

  return <NotificationList notifications={notifications} onClose={remove} />;
}

export default NotificationContainer;
