import React from 'react';

import styled from '@emotion/styled';
import RegisterForm from '../../components/register/RegisterForm';
import Header from '../../components/common/header/Header';

const Container = styled.div`
  border-radius: 10px;
  position: absolute;
  top: 20%;
  width: 400px;
  padding: 20px;
  margin-left: 556px;
  background-color: #add8e6;
  text-align: center;
`;

const Register = () => (
  <Container>
    <Header>
      <span>Registration</span>
    </Header>
    Who are you?
    <RegisterForm />
  </Container>
);

export default Register;
