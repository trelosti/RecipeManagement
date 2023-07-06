import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import Link from './Link';

describe('Link', () => {
  it('renders the given children', () => {
    render(
      <BrowserRouter>
        <Link to="/home">Home</Link>
      </BrowserRouter>
    );
    expect(screen.getByText('Home')).toBeInTheDocument();
  });

  it('renders with the given "to" prop', () => {
    render(
      <BrowserRouter>
        <Link to="/home">Home</Link>
      </BrowserRouter>
    );
    expect(screen.getByRole('link')).toHaveAttribute('href', '/home');
  });

  it('applies the given className and style props', () => {
    render(
      <BrowserRouter>
        <Link to="/home" className="test-link">
          Home
        </Link>
      </BrowserRouter>
    );
    const link = screen.getByRole('link');
    expect(link).toHaveClass('test-link');
  });
});
