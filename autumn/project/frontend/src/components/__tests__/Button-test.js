import { render, fireEvent } from 'test-utils';

import Button from '../common/Button';

const props = {
  onClick: jest.fn(),
};

describe('Button', () => {
  it('renders correctly', () => {
    const { container } = render(<Button {...props}>Click Me!</Button>);
    expect(container.firstChild).toMatchInlineSnapshot(`
      <button
        class="button"
      >
        Click Me!
      </button>
    `);
  });

  it('handles onClick event', () => {
    const text = 'Click Me!';
    const onClickMock = jest.fn();
    const { getByText } = render(
      <Button {...props} onClick={onClickMock}>
        {text}
      </Button>,
    );

    fireEvent.click(getByText(text));

    expect(onClickMock).toBeCalledTimes(1);
  });
});
