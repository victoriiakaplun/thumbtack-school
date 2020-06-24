import React from 'react';

function CardImage({ imgUrl }) {
  return (
    <div className="media-left">
      <figure className="image">
        <img src={imgUrl} alt="card img" />
      </figure>
    </div>
  );
}

export default CardImage;
