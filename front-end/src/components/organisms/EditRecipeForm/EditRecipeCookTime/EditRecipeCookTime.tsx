import React from 'react';
import { Controller, useFormState } from 'react-hook-form';
import TextField from '../../../molecules/TextField/TextField';
import EditRecipeBlock from '../../../molecules/EditRecipeBlock/EditRecipeBlock';
import { COOKTIME_PLACEHOLDER } from './EditRecipeCookTime.const';
import { EditRecipeCookTimeProps } from './EditRecipeCookTime.types';
import { REQUIRED_FIELD_ERR } from '../../../../app/consts/errorMessages';

function EditRecipeCookTime({ control }: EditRecipeCookTimeProps) {
  const { errors } = useFormState({ control });

  return (
    <EditRecipeBlock
      marginTop="60px"
      title={COOKTIME_PLACEHOLDER}
      width="150px"
    >
      <Controller
        name="cookTime"
        control={control}
        rules={{ required: true }}
        render={({ field }) => (
          <TextField
            type="number"
            {...field}
            error={errors.cookTime && REQUIRED_FIELD_ERR}
            placeholder={COOKTIME_PLACEHOLDER}
          />
        )}
      />
    </EditRecipeBlock>
  );
}

export default EditRecipeCookTime;
