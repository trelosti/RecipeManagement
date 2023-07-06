import React from 'react';
import Text from '../../atoms/Text/Text';
import { IngredientsListProps } from './IngredientsList.types';

function IngredientsList({ ingredients }: IngredientsListProps) {
  const normalizeMeasureUnit = (measureUnit: string) => {
    switch (measureUnit) {
      case 'GRAM':
        return 'g';
      case 'KILOGRAM':
        return 'kg';
      case 'MILLIGRAM':
        return 'mg';
      case 'MILLILITER':
        return 'ml';
      case 'LITER':
        return 'l';
      default:
        return 'pcs';
    }
  };

  return (
    <>
      <Text size={24} weight="bold" color="default">
        Main ingredients
      </Text>
      {ingredients.map(({ id, ingredientName, measureUnit, quantity }) => (
        <Text size={18} color="default" style={{ lineHeight: '32px' }} key={id}>
          {ingredientName} - {quantity} {normalizeMeasureUnit(measureUnit)}
        </Text>
      ))}
    </>
  );
}

export default IngredientsList;
