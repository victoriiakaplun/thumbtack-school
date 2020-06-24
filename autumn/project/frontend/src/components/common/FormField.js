import React from 'react';

const FormField = ({ type, name, placeholder }) => (
  <div className="field">
    <input
      className="input is-info is-rounded"
      type={type}
      name={name}
      placeholder={placeholder}
      required
    />
  </div>
);

export default FormField;
