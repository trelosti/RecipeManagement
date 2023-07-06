import React from 'react';
import { Controller, useFormState } from 'react-hook-form';
import Box from '../../../atoms/Box/Box';
import TextField from '../../../molecules/TextField/TextField';
import Select from '../../../atoms/Select/Select';
import IconButton from '../../../atoms/IconButton/IconButton';
import deleteButton from '../../../../app/assets/icons/deleteButton.png';
import {
  INGREDIENT_NAME_PLACEHOLDER,
  INGREDIENT_WEIGHT_PLACEHOLDER,
} from './EditRecipeIngredient.const';
import {
  EditRecipeIngredientProps,
  MEASURE_UNIT_OPTIONS,
} from './EditRecipeIngredient.types';
import { REQUIRED_FIELD_ERR } from '../../../../app/consts/errorMessages';

function EditRecipeIngredient({
  remove,
  control,
  index,
}: EditRecipeIngredientProps) {
  const { errors } = useFormState({ control });

  const ingredientErrors = errors.ingredients?.[index];

  return (
    <Box flex gap="10px" marginBottom="20px" align="flex-start">
      <Box style={{ flexGrow: '1' }}>
        <Controller
          name={`ingredients.${index}.ingredientName`}
          control={control}
          rules={{ required: true }}
          render={({ field }) => (
            <TextField
              placeholder={INGREDIENT_NAME_PLACEHOLDER}
              error={ingredientErrors?.ingredientName && REQUIRED_FIELD_ERR}
              {...field}
            />
          )}
        />
      </Box>
      <Controller
        name={`ingredients.${index}.quantity`}
        control={control}
        rules={{ required: true }}
        render={({ field }) => (
          <TextField
            placeholder={INGREDIENT_WEIGHT_PLACEHOLDER}
            error={ingredientErrors?.quantity && REQUIRED_FIELD_ERR}
            {...field}
            type="number"
          />
        )}
      />
      <Controller
        name={`ingredients.${index}.measureUnit`}
        control={control}
        render={({ field }) => (
          <Select {...field} options={MEASURE_UNIT_OPTIONS} />
        )}
      />
      <IconButton
        src={deleteButton}
        onClick={() => remove(index)}
        marginTop="7px"
      />
    </Box>
  );
}

export default EditRecipeIngredient;
