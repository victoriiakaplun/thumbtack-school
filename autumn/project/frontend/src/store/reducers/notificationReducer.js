import uuid from 'uuid/v4';
import { ADD_NOTIFICATION, REMOVE_NOTIFICATION } from '../actions/notificationActions';

export function getInitialNotificationState() {
  return {
    notifications: [],
  };
}

function notificationReducer(prevState, recAction) {
  const handlerMap = {
    [ADD_NOTIFICATION]: (state, action) => ({
      ...state,
      notifications: [...state.notifications, { ...action.payload, id: uuid() }],
    }),

    [REMOVE_NOTIFICATION]: (state, action) => ({
      ...state,
      notifications: state.notifications.filter(
        ({ id: notificationId }) => action.payload !== notificationId,
      ),
    }),
  };

  const handler = handlerMap[recAction.type];
  return handler ? handler(prevState, recAction) : getInitialNotificationState();
}

export default notificationReducer;
