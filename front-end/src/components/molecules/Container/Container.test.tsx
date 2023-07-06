import React from 'react';
import { render, screen } from '@testing-library/react';
import Container from './Container';

describe('Container', () => {
  it('renders without errors', () => {
    render(<Container>Container</Container>);
    expect(screen.getByText('Container')).toBeInTheDocument();
    expect(screen.getByText('Container')).toHaveStyle({
      width: '1300px',
      margin: '0 auto',
      minHeight: '90vh',
    });
  });
});
