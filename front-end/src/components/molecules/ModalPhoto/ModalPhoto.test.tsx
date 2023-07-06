import React from 'react';
import { render, screen } from '@testing-library/react';
import ModalPhoto from './ModalPhoto';

describe('ModalPhoto', () => {
  const defaultProps = {
    isShow: true,
    onClick: () => {},
    recipePhoto: 'addPhoto.png',
  };

  it('adds props', () => {
    render(<ModalPhoto {...defaultProps} />);
    const imgElement = screen.getByAltText(defaultProps.recipePhoto);
    expect(imgElement).toBeInTheDocument();
    expect(imgElement).toHaveAttribute('src', defaultProps.recipePhoto);
  });
});
