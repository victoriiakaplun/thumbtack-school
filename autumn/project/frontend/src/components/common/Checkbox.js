import React from 'react';

const Checkbox = ({ name, value, children }) => (
  <div className="container">
    <label className="control">
      <input type="checkbox" name={name} value={value} />
      {children}
    </label>
  </div>
);
export default Checkbox;
