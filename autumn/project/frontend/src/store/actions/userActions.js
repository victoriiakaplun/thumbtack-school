export const SET_USER = 'SET_USER';
export function setUser(user) {
  return {
    type: SET_USER,
    payload: user,
  };
}

export const REGISTER = 'REGISTER';
export function register(registerData) {
  return {
    type: REGISTER,
    payload: registerData,
  };
}
