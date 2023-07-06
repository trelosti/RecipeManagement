import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import Avatar from './Avatar';

describe('Avatar', () => {
  const defaultProps = {
    width: '85px',
    height: '85px',
    src: 'https://example.com/image.jpg',
    alt: 'avatar',
  };

  it('adds props styles', () => {
    render(
      <BrowserRouter>
        <Avatar {...defaultProps} />
      </BrowserRouter>
    );
    const imgElement = screen.getByAltText(defaultProps.alt);
    expect(imgElement).toBeInTheDocument();
    expect(imgElement).toHaveAttribute('src', defaultProps.src);
  });
});
