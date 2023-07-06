import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import IconButton from './IconButton';

describe('IconButton', () => {
  const defaultProps = {
    src: 'https://example.com/image.png',
    onClick: jest.fn(),
    ariaLabel: 'Test icon button',
  };

  it('renders without errors', () => {
    render(<IconButton {...defaultProps} />);
  });

  it('renders the image correctly', () => {
    const { getByAltText } = render(<IconButton {...defaultProps} />);
    expect(getByAltText(defaultProps.ariaLabel)).toBeInTheDocument();
  });

  it('calls onClick handler when clicked', () => {
    const { getByRole } = render(<IconButton {...defaultProps} />);
    fireEvent.click(getByRole('button'));
    expect(defaultProps.onClick).toHaveBeenCalledTimes(1);
  });

  it('applies atom props styles', () => {
    const { getByRole } = render(
      <IconButton {...defaultProps} margin="10px" />
    );
    expect(getByRole('button')).toHaveStyle({
      margin: '10px',
    });
  });
});
