import { RecipeEditFormData } from '../../components/organisms/EditRecipeForm/EditRecipeForm.types';
import { RecipeCreatingDto } from './apiTypes';

export const getImageBlob = (imageUrl: string) =>
  fetch(imageUrl, { credentials: 'include' })
    .then((response) => response.blob())
    .then((blob) => blob);

export const createRecipeFormData = async (
  data: RecipeEditFormData,
  defaultRecipePhoto?: string,
  defaultStepsPhotos?: string[]
): Promise<FormData> => {
  const steps = data.steps.map((step) => step.step);
  const ingredients = data.ingredients.map((ingredient) => ({
    ...ingredient,
    quantity: +ingredient.quantity,
  }));

  const recipeCreatingDto: RecipeCreatingDto = {
    recipeName: data.recipeName,
    cookTime: +data.cookTime,
    steps,
    ingredients,
  };

  const formData = new FormData();
  const blobRecipeCreatingDto = new Blob([JSON.stringify(recipeCreatingDto)], {
    type: 'application/json',
  });

  formData.append('recipeCreatingDto', blobRecipeCreatingDto);
  if (data.recipeImage) {
    formData.append('recipePhoto', data.recipeImage[0]);
  } else if (defaultRecipePhoto) {
    const recipePhotoBlob = await getImageBlob(defaultRecipePhoto);
    formData.append('recipePhoto', recipePhotoBlob);
  }
  data.steps.forEach((step, index) => {
    if (step.image) {
      formData.append('stepPhotos', step.image[0]);
    } else if (defaultStepsPhotos?.[index]) {
      getImageBlob(defaultStepsPhotos[index]).then((recipeStepImageBlob) =>
        formData.append('stepPhotos', recipeStepImageBlob)
      );
    }
  });

  return formData;
};
