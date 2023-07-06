import React from 'react';
import { Controller, useFormState } from 'react-hook-form';
import EditRecipeBlock from '../../../molecules/EditRecipeBlock/EditRecipeBlock';
import AddImage from '../../../molecules/AddImage/AddImage';
import { EditRecipeImageProps } from './EditRecipeImage.types';
import { REQUIRED_IMAGE_ERR } from '../../../../app/consts/errorMessages';
import Text from '../../../atoms/Text/Text';

function EditRecipeImage({ defaultImage, control }: EditRecipeImageProps) {
  const { errors } = useFormState({ control });

  return (
    <EditRecipeBlock width="40%" height="420px">
      <Controller
        name="recipeImage"
        control={control}
        rules={{ required: !defaultImage }}
        render={({ field }) => (
          <>
            <AddImage
              defaultImage={defaultImage}
              {...field}
              onChange={(e) => field.onChange(e.target.files)}
              height="100%"
            />
            {errors.recipeImage && <Text>{REQUIRED_IMAGE_ERR}</Text>}
          </>
        )}
      />
    </EditRecipeBlock>
  );
}

export default EditRecipeImage;
