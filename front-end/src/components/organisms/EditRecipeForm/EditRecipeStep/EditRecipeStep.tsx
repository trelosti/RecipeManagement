import React from 'react';
import { Controller, useFormState } from 'react-hook-form';
import Box from '../../../atoms/Box/Box';
import Textarea from '../../../atoms/Textarea/Textarea';
import { EditRecipeStepProps } from './EditRecipeStep.types';
import { STEP_PLACEHOLDER } from './EditRecipeStep.const';
import AddImage from '../../../molecules/AddImage/AddImage';
import IconButton from '../../../atoms/IconButton/IconButton';
import deleteButton from '../../../../app/assets/icons/deleteButton.png';
import {
  REQUIRED_FIELD_ERR,
  REQUIRED_IMAGE_ERR,
} from '../../../../app/consts/errorMessages';
import Text from '../../../atoms/Text/Text';

function EditRecipeStep({
  index,
  control,
  remove,
  stepImage,
}: EditRecipeStepProps) {
  const { errors } = useFormState({ control });
  const stepError = errors.steps?.[index]?.step || undefined;
  const imageError = errors.steps?.[index]?.image || undefined;

  return (
    <Box flex gap="20px" style={{ position: 'relative' }}>
      <IconButton src={deleteButton} onClick={() => remove(index)} />
      <Controller
        name={`steps.${index}.step`}
        control={control}
        rules={{ required: true }}
        render={({ field }) => (
          <Box style={{ flexGrow: 1 }}>
            <Textarea
              placeholder={STEP_PLACEHOLDER}
              {...field}
              width="100%"
              height="100px"
            />
            {stepError && <Text color="primary">{REQUIRED_FIELD_ERR}</Text>}
          </Box>
        )}
      />
      <Controller
        name={`steps.${index}.image`}
        control={control}
        rules={{ required: !stepImage }}
        render={({ field }) => (
          <Box>
            <AddImage
              defaultImage={stepImage}
              {...field}
              onChange={(e) => field.onChange(e.target.files)}
              height="100px"
              width="115px"
            />
            {imageError && <Text>{REQUIRED_IMAGE_ERR}</Text>}
          </Box>
        )}
      />
    </Box>
  );
}

export default EditRecipeStep;
