import React from 'react';
import { Controller, useFormState } from 'react-hook-form';
import TextField from '../../../molecules/TextField/TextField';
import EditRecipeBlock from '../../../molecules/EditRecipeBlock/EditRecipeBlock';
import { EditRecipeNameProps } from './EditRecipeName.types';
import {
  EDIT_NAME_PLACEHOLDER,
  RECIPE_NAME_TITLE,
} from './EditRecipeName.const';
import { RECIPE_NAME_REGEX } from '../../../../app/consts/regex';
import {
  RECIPE_NAME_PATTERN_ERR,
  REQUIRED_FIELD_ERR,
} from '../../../../app/consts/errorMessages';

function EditRecipeName({ control }: EditRecipeNameProps) {
  const { errors } = useFormState({ control });

  const recipeNameError =
    (errors.recipeName?.type === 'pattern' && RECIPE_NAME_PATTERN_ERR) ||
    (errors.recipeName?.type === 'required' && REQUIRED_FIELD_ERR) ||
    undefined;

  return (
    <EditRecipeBlock
      marginTop="60px"
      title={RECIPE_NAME_TITLE}
      style={{ flexGrow: '1' }}
    >
      <Controller
        name="recipeName"
        control={control}
        rules={{
          required: true,
          pattern: RECIPE_NAME_REGEX,
        }}
        render={({ field }) => (
          <TextField
            {...field}
            placeholder={EDIT_NAME_PLACEHOLDER}
            error={recipeNameError}
          />
        )}
      />
    </EditRecipeBlock>
  );
}

export default EditRecipeName;
