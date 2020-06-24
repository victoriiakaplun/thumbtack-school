import React from 'react';
import styled from '@emotion/styled';

const Container = styled.div`
  text-align: center;
  background-color: gainsboro;
`;

function Scene({ children }) {
  return <Container>{children}</Container>;
}

export default Scene;
