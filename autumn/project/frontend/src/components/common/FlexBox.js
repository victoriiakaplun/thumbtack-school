import React from 'react';

function FlexBox({ children }) {
  return <div style={{ display: 'flex', flexWrap: 'wrap' }}>{children}</div>;
}

export default FlexBox;
