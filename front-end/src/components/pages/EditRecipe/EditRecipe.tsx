import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import PageTemplate from '../../templates/PageTemplate/PageTemplate';
import EditRecipeForm from '../../organisms/EditRecipeForm/EditRecipeForm';
import {
  IngredientItem,
  RecipeEditFormData,
  StepItem,
} from '../../organisms/EditRecipeForm/EditRecipeForm.types';
import {
  useGetRecipeByIdQuery,
  useGetUserProfileQuery,
  useUpdateMutation,
} from '../../../app/api/apiSlice';
import NotFound from '../NotFound/NotFound';
import RecipeInfo from '../../../app/entities/RecipeItem';
import { createRecipeFormData } from '../../../app/api/apiService';

const createDefaultFormValue = (recipeData: RecipeInfo): RecipeEditFormData => {
  const steps: StepItem[] = recipeData.recipeSteps.map((recipeStep) => ({
    step: recipeStep.description,
  }));
  const ingredients: IngredientItem[] = recipeData.ingredients.map(
    (ingredient) => ({
      ...ingredient,
      quantity: ingredient.quantity.toString(),
    })
  );
  const cookTime = recipeData.cookTime.toString();

  return {
    recipeName: recipeData.recipeName,
    ingredients,
    steps,
    cookTime,
  };
};

function EditRecipe() {
  const navigate = useNavigate();
  const { recipeId } = useParams();

  const { data: userData } = useGetUserProfileQuery('');
  const { data: recipeData, isError } = useGetRecipeByIdQuery(recipeId || '');
  const [updateRecipe] = useUpdateMutation();

  const [stepsImages, setStepsImages] = useState<string[] | undefined>();

  useEffect(() => {
    if (recipeData) {
      const newStepsImages = recipeData.recipeSteps.map(
        (recipeStep) => recipeStep.stepPhotoLink
      );
      setStepsImages(newStepsImages);
    }
  }, [recipeData]);

  const onSubmit = async (formData: RecipeEditFormData) => {
    const requestData = await createRecipeFormData(
      formData,
      recipeData?.recipePhotoLink,
      stepsImages
    );

    if (recipeId) {
      const updatedRecipe = await updateRecipe({
        formData: requestData,
        recipeId,
      });

      if (updatedRecipe) {
        navigate(`/recipes/${recipeId}`);
      }
    }
  };
  const onCancel = () => {
    if (recipeId) {
      navigate(`/recipes/${recipeId}`);
    }
  };

  if (isError && recipeData?.author.id !== userData?.id) {
    return <NotFound />;
  }

  if (!recipeData || !recipeId) {
    return null;
  }

  return (
    <PageTemplate background="none">
      <EditRecipeForm
        onSubmit={onSubmit}
        onCancel={onCancel}
        defaultValues={createDefaultFormValue(recipeData)}
        recipeImage={recipeData.recipePhotoLink}
        stepsImages={stepsImages}
      />
    </PageTemplate>
  );
}

export default EditRecipe;
