import React, { useEffect } from 'react';
import styled from '@emotion/styled';
import Button from '../../components/common/Button';

function Notification({ onClose, delay, children }) {
  useEffect(() => {
    setTimeout(() => onClose(), 5000);
  }, [onClose, delay]);
  return (
    <Container>
      <div className="notification">
        {children}
        <Button type="button" onClick={onClose}>
          Close
        </Button>
      </div>
    </Container>
  );
}

const Container = styled.div`
  border-radius: 2px;
  top: 20%;
  width: 250px;
  margin-left: 456px;
  background-color: gainsboro;
  text-align: center;
`;

Notification.defaultProps = {
  delay: 5,
};
export default Notification;
