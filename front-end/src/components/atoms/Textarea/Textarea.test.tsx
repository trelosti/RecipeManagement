import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Textarea from './Textarea';
import { TextareaProps } from './Textarea.types';

describe('Textarea', () => {
  const defaultProps: TextareaProps = {
    name: 'test-name',
  };

  it('should render textarea with given name', () => {
    render(<Textarea {...defaultProps} />);
    const textarea = screen.getByRole('textbox');
    expect(textarea).toHaveAttribute('name', 'test-name');
  });

  it('should render textarea with given value', () => {
    render(<Textarea {...defaultProps} value="test-value" />);
    const textarea = screen.getByRole('textbox');
    expect(textarea).toHaveValue('test-value');
  });

  it('should call onChange handler when textarea is changed', () => {
    const handleChange = jest.fn();
    render(<Textarea {...defaultProps} onChange={handleChange} />);
    const textarea = screen.getByRole('textbox');
    fireEvent.change(textarea, { target: { value: 'test-value' } });
    expect(handleChange).toHaveBeenCalledTimes(1);
  });

  it('should render textarea with given placeholder', () => {
    render(<Textarea {...defaultProps} placeholder="test-placeholder" />);
    const textarea = screen.getByRole('textbox');
    expect(textarea).toHaveAttribute('placeholder', 'test-placeholder');
  });

  it('should render textarea with given width', () => {
    render(<Textarea {...defaultProps} width="50%" />);
    const textarea = screen.getByRole('textbox');
    expect(textarea).toHaveStyle({ width: '50%' });
  });
});
