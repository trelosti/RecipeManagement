import React from 'react';
import { render } from '@testing-library/react';
import IngredientsList from './IngredientsList';
import { MeasureUnits } from '../../../app/entities/Ingredient';

describe('IngredientsList', () => {
  const ingredients = [
    {
      id: 33,
      createdAt: '2023-04-28T21:17:30',
      updatedAt: '2023-04-28T21:17:30',
      ingredientName: 'Leek',
      measureUnit: MeasureUnits.GRAM,
      quantity: 280,
    },
  ];

  it('renders the text content', () => {
    const { getByText } = render(<IngredientsList ingredients={ingredients} />);
    expect(getByText('Main ingredients')).toBeInTheDocument();
  });
});
