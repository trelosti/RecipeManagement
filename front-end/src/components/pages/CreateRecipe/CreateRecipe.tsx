import React from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import PageTemplate from '../../templates/PageTemplate/PageTemplate';
import EditRecipeForm from '../../organisms/EditRecipeForm/EditRecipeForm';
import { RecipeEditFormData } from '../../organisms/EditRecipeForm/EditRecipeForm.types';
import { useCreateRecipeMutation } from '../../../app/api/apiSlice';
import { createRecipeFormData } from '../../../app/api/apiService';

function EditRecipe() {
  const navigate = useNavigate();
  const [createRecipe, { data: createdRecipeData }] = useCreateRecipeMutation();

  const onSubmit = async (formData: RecipeEditFormData) => {
    const requestData = await createRecipeFormData(formData);
    await createRecipe(requestData);
  };

  const onCancel = () => {
    navigate(-1);
  };

  if (createdRecipeData) {
    return <Navigate to={`/recipes/${createdRecipeData.id}`} />;
  }

  return (
    <PageTemplate background="none">
      <EditRecipeForm onSubmit={onSubmit} onCancel={onCancel} />
    </PageTemplate>
  );
}

export default EditRecipe;
