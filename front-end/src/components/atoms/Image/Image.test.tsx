import React from 'react';
import { render, screen } from '@testing-library/react';
import Image from './Image';

describe('Image', () => {
  const defaultProps = {
    src: 'https://example.com/image.jpg',
    alt: 'Example Image',
    radius: '5px',
  };

  it('renders with default props', () => {
    render(<Image {...defaultProps} />);
    const imgElement = screen.getByAltText(defaultProps.alt);
    expect(imgElement).toBeInTheDocument();
    expect(imgElement).toHaveAttribute('src', defaultProps.src);
    expect(imgElement).toHaveStyle({ borderRadius: defaultProps.radius });
  });

  it('renders with atom props', () => {
    const atomProps = {
      margin: '10px',
      padding: '10px',
      className: 'testClass',
    };
    render(<Image {...defaultProps} {...atomProps} />);
    const imgElement = screen.getByAltText(defaultProps.alt);
    expect(imgElement).toBeInTheDocument();
    expect(imgElement).toHaveAttribute('src', defaultProps.src);
    expect(imgElement).toHaveStyle({
      borderRadius: defaultProps.radius,
      margin: atomProps.margin,
      padding: atomProps.padding,
    });
    expect(imgElement).toHaveClass(atomProps.className);
  });

  it('does not add invalid atom props', () => {
    const atomProps = {
      invalidProp: 'invalidPropValue',
    };
    render(<Image {...defaultProps} {...atomProps} />);
    const imgElement = screen.getByAltText(defaultProps.alt);
    expect(imgElement).toBeInTheDocument();
    expect(imgElement).toHaveAttribute('src', defaultProps.src);
    expect(imgElement).toHaveStyle({ borderRadius: defaultProps.radius });
    expect(imgElement).not.toHaveAttribute('invalidProp');
  });
});
