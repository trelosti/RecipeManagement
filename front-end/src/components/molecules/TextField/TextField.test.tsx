import React from 'react';
import { render, screen } from '@testing-library/react';
import TextField from './TextField';

describe('TextField', () => {
  it('renders an input element with provided props', () => {
    render(
      <TextField
        name="test"
        value="test value"
        placeholder="test placeholder"
      />
    );
    const inputElement = screen.getByRole('textbox');
    expect(inputElement).toHaveAttribute('name', 'test');
    expect(inputElement).toHaveAttribute('value', 'test value');
    expect(inputElement).toHaveAttribute('placeholder', 'test placeholder');
  });

  it('renders an error message when error prop is passed', () => {
    render(<TextField error="test error message" name="input" />);
    const errorMessage = screen.getByText('test error message');
    expect(errorMessage).toBeInTheDocument();
  });
});
