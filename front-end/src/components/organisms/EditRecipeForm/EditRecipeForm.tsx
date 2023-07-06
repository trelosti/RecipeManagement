import React from 'react';
import { useForm } from 'react-hook-form';
import EditRecipeName from './EditRecipeName/EditRecipeName';
import Box from '../../atoms/Box/Box';
import EditRecipeImage from './EditRecipeImage/EditRecipeImage';
import EditRecipeIngredients from './EditRecipeIngredients/EditRecipeIngredients';
import {
  EditRecipeFormProps,
  RecipeEditFormData,
} from './EditRecipeForm.types';
import EditRecipeCookTime from './EditRecipeCookTime/EditRecipeCookTime';
import EditRecipeSteps from './EditRecipeSteps/EditRecipeSteps';
import Button from '../../atoms/Button/Button';
import {
  CANCEL_BUTTON,
  PUBLISH_BUTTON,
  RECIPE_EMPTY_FORM_DATA,
} from './EditRecipeForm.const';

function EditRecipeForm({
  recipeImage,
  stepsImages,
  onCancel,
  onSubmit,
  defaultValues,
}: EditRecipeFormProps) {
  const { control, handleSubmit } = useForm<RecipeEditFormData>({
    defaultValues: defaultValues || RECIPE_EMPTY_FORM_DATA,
  });

  return (
    <Box flex direction="column" gap="20px" background="none">
      <Box flex background="none" gap="20px">
        <EditRecipeName control={control} />
        <EditRecipeCookTime control={control} />
      </Box>

      <Box flex background="none" gap="20px" align="flex-start">
        <EditRecipeImage defaultImage={recipeImage} control={control} />
        <EditRecipeIngredients control={control} />
      </Box>

      <EditRecipeSteps stepsImages={stepsImages} control={control} />

      <Box
        flex
        justify="center"
        gap="30px"
        marginTop="30px"
        marginBottom="40px"
        background="none"
      >
        <Button onClick={onCancel} variant="outline">
          {CANCEL_BUTTON}
        </Button>
        <Button onClick={handleSubmit(onSubmit)}>{PUBLISH_BUTTON}</Button>
      </Box>
    </Box>
  );
}

export default EditRecipeForm;
