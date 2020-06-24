import React from 'react';

function Card({ children, width, margin }) {
  return (
    <div
      className="card"
      style={{ margin: `${margin}`, width: `${width}`, backgroundColor: '#F5FFFA' }}
    >
      <div className="card-content">
        <div className="media">{children}</div>
      </div>
    </div>
  );
}

export default Card;
