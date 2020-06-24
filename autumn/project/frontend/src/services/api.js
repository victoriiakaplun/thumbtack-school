import axios from 'axios';

export async function getAllProducts() {
  const { data } = await axios.get('/api/v1/products');
  return data;
}

export async function fetchProduct(productId) {
  const { data } = await axios.get(`/api/v1/products/${productId}`);
  return data;
}

export async function fetchMe() {
  const { data } = await axios.get(`/api/v1/users/${tmpUserId}`);
  return data;
}

export async function updateMe(data, userId) {
  return axios.put(`/api/v1/${userId}`, data);
}

export async function register(req) {
  const { user } = await axios.post('api/v1/users/register');
  return user;
}
