import React from 'react';
import { render, screen } from '@testing-library/react';
import ProfileInfoItem from './ProfileInfoItem';

describe('ProfileInfoItem', () => {
  it('renders without errors', () => {
    render(<ProfileInfoItem recipesCount={30} />);
    expect(screen.getByText('30')).toBeInTheDocument();
    expect(screen.getAllByRole('span')[0]).toBeInTheDocument();
  });
});
