import React from 'react';

import styled from '@emotion/styled';
import { faUser, faEnvelope, faPhoneAlt, faWallet } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import UserInfoItem from '../../components/user/UserInfoItem';
import Header from '../../components/common/header/Header';

const Container = styled.div`
  border-radius: 10px;
  position: absolute;
  top: 20%;
  width: 600px;
  padding: 20px;
  margin-left: 456px;
  background-color: #add8e6;
  text-align: center;
`;

function UserProfile() {
  /* if (store.state.user == null) {
    return <div>Loading</div>;
  }
  const {
    name, email, phone, balance,
  } = store.state.user; */

  return (
    <Container>
      <Header>My profile</Header>
      <UserInfoItem>
        <FontAwesomeIcon css={{ color: '#10316b;', marginRight: '10px' }} icon={faUser} />
        Name
      </UserInfoItem>
      <UserInfoItem>
        <FontAwesomeIcon css={{ color: '#10316b;', marginRight: '10px' }} icon={faEnvelope} />
        Email
      </UserInfoItem>
      <UserInfoItem>
        <FontAwesomeIcon css={{ color: '#10316b;', marginRight: '10px' }} icon={faPhoneAlt} />
        Phone
      </UserInfoItem>
      <UserInfoItem>
        <FontAwesomeIcon css={{ color: '#10316b;', marginRight: '10px' }} icon={faWallet} />
        Balance
      </UserInfoItem>
    </Container>
  );
}

export default UserProfile;
