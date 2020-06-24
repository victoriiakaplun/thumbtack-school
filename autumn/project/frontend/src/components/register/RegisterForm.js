import React, { useState } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import Checkbox from '../common/Checkbox';
import FormField from '../common/FormField';
import Button from '../common/Button';

function RegisterForm() {
  const [inputData, setInputData] = useState({
    name: '',
    email: '',
    phone: '',
    password: '',
    roles: [],
  });

  const handleInput = event => {
    setInputData({
      [event.target.name]: event.target.value,
    });
  };

  return (
    <div className="column is-grouped is-grouped-center">
      <form>
        <div className="field is-grouped is-grouped-center">
          <Checkbox name="role" value="Client">
            Client
          </Checkbox>
          <Checkbox name="role" value="Courier">
            Courier
          </Checkbox>
        </div>
        <FormField type="text" name="name" placeholder="Name" onChange={handleInput} required />
        <FormField type="email" name="email" placeholder="Email" onChange={handleInput} required />
        <FormField type="tel" name="phone" placeholder="Phone" onChange={handleInput} required />
        <FormField
          type="password"
          name="password1"
          placeholder="Password"
          onChange={handleInput}
          required
        />
        <FormField
          type="password"
          name="password2"
          placeholder="Repeat password"
          onChange={handleInput}
          required
        />
        <Button type="button" onClick={register}>
          Click to register
        </Button>
      </form>
    </div>
  );
}

const mapDispatchToProps = dispatch => ({
  register: bindActionCreators(register, dispatch),
});

export default connect(mapDispatchToProps)(RegisterForm);
