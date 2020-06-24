export const ADD_NOTIFICATION = 'ADD_NOTIFICATION';
export function addNotification(notification) {
  return {
    type: ADD_NOTIFICATION,
    payload: notification,
  };
}

export const REMOVE_NOTIFICATION = 'REMOVE_NOTIFICATION';
export function removeNotification(id) {
  return {
    type: REMOVE_NOTIFICATION,
    payload: id,
  };
}
