import React from 'react';
import { useFieldArray } from 'react-hook-form';
import EditRecipeBlock from '../../../molecules/EditRecipeBlock/EditRecipeBlock';
import EditRecipeIngredient from '../EditRecipeIngredient/EditRecipeIngredient';
import Button from '../../../atoms/Button/Button';
import { EditRecipeIngredientsProps } from './EditRecipeIngredients.types';
import {
  ADD_INGREDIENT_BUTTON,
  RECIPE_INGREDIENTS_TITLE,
} from './EditRecipeIngredients.const';
import { MeasureUnits } from '../../../../app/entities/Ingredient';

function EditRecipeIngredients({ control }: EditRecipeIngredientsProps) {
  const { fields, append, remove } = useFieldArray({
    control,
    name: 'ingredients',
    rules: {
      minLength: 1,
    },
  });

  return (
    <EditRecipeBlock width="60%" title={RECIPE_INGREDIENTS_TITLE}>
      {fields.map((item, index) => (
        <EditRecipeIngredient
          key={item.id}
          index={index}
          remove={remove}
          control={control}
        />
      ))}

      <Button
        onClick={() => {
          append({
            ingredientName: '',
            quantity: '',
            measureUnit: MeasureUnits.MILLILITER,
          });
        }}
        variant="frameless"
        marginTop="20px"
        marginX="auto"
      >
        {ADD_INGREDIENT_BUTTON}
      </Button>
    </EditRecipeBlock>
  );
}

export default EditRecipeIngredients;
