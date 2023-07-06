import React from 'react';
import { render } from '@testing-library/react';
import Select from './Select';
import { SelectProps } from './Select.types';

const options = [
  { label: 'Option 1', value: '1' },
  { label: 'Option 2', value: '2' },
  { label: 'Option 3', value: '3' },
];

const defaultProps: SelectProps = {
  value: '',
  onChange: jest.fn(),
  options,
};

describe('Select', () => {
  it('should render options', () => {
    const { getByText } = render(<Select {...defaultProps} />);
    options.forEach((option) => {
      expect(getByText(option.label)).toBeInTheDocument();
    });
  });

  it('should render a placeholder', () => {
    const { getByText } = render(
      <Select {...defaultProps} placeholder="Choose an option" />
    );
    expect(getByText('Choose an option')).toBeInTheDocument();
  });

  it('should have the correct value', () => {
    const { getByDisplayValue } = render(
      <Select {...defaultProps} value="2" />
    );
    expect(getByDisplayValue('Option 2')).toBeInTheDocument();
  });

  it('should pass through className and style props', () => {
    const { container } = render(
      <Select
        {...defaultProps}
        className="custom-class"
        style={{ color: 'red' }}
      />
    );
    expect(container.firstChild).toHaveClass('custom-class');
    expect(container.firstChild).toHaveStyle('color: red');
  });

  it('should have a custom width', () => {
    const { container } = render(<Select {...defaultProps} width="50%" />);
    expect(container.firstChild).toHaveStyle('width: 50%');
  });
});
