import React from 'react';
import { fireEvent, render, screen } from '@testing-library/react';
import Input from './Input';

describe('Input', () => {
  test('renders input with correct type and placeholder', () => {
    const { container } = render(<Input name="input" />);
    const inputElement = container.querySelector('input');
    expect(inputElement).toBeInTheDocument();
  });

  it('calls the onChange function when input is changed', () => {
    const handleChange = jest.fn();
    const { getByRole } = render(
      <Input name="test-input" onChange={handleChange} value="" />
    );

    const inputElement = getByRole('textbox');
    fireEvent.change(inputElement, { target: { value: 'Test value' } });

    expect(handleChange).toHaveBeenCalledTimes(1);
  });

  it('sets the value of the input element', () => {
    const value = 'test';
    render(<Input name="input" value={value} />);
    const inputElement = screen.getByRole('textbox');
    expect(inputElement).toHaveValue(value);
  });

  test('passes atomProps to input element', () => {
    const atomProps = {
      className: 'custom-class',
    };
    render(<Input name="test" type="text" {...atomProps} />);
    const input = screen.getByRole('textbox');
    expect(input).toHaveClass('custom-class');
  });
});
