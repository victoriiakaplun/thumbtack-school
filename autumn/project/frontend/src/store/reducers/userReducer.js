import { SET_USER, REGISTER } from '../actions/userActions';

export function getInitialUserState() {
  return {
    userInfo: {
      name: 'username',
      email: 'useremail@gmail.com',
      phone: '7-XXX-XXX-XXXX',
      balance: 1000,
      role: ['client'],
    },
  };
}

function userReducer(prevState, recAction) {
  const handlerMap = {
    [SET_USER]: (state, action) => ({
      ...state,
      user: action.payload,
    }),
    [REGISTER]: (state, action) => ({}),
  };

  const handler = handlerMap[recAction.type];
  return handler ? handler(prevState, recAction) : getInitialUserState();
}

export default userReducer;
