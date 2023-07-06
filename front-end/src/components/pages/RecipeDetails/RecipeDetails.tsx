import React from 'react';
import { useParams } from 'react-router-dom';
import Box from '../../atoms/Box/Box';
import RecipeHeader from '../../organisms/RecipeHeader/RecipeHeader';
import RecipeInfo from '../../organisms/RecipeInfo/RecipeInfo';
import HeaderWithAmount from '../../organisms/HeaderWithAmount/HeaderWithAmount';
import RecipeSteps from '../../organisms/RecipeSteps/RecipeSteps';

import {
  useGetRecipeByIdQuery,
  useGetUserProfileQuery,
} from '../../../app/api/apiSlice';
import PageTemplate from '../../templates/PageTemplate/PageTemplate';

function RecipeDetails() {
  const { data: user } = useGetUserProfileQuery('');
  const { id } = useParams();

  if (!id) return null;

  const { data: recipe } = useGetRecipeByIdQuery(id);

  if (!recipe || !user) {
    return null;
  }

  return (
    <PageTemplate background="none">
      <Box padding="30px" marginTop="50px" radius="20px">
        <RecipeHeader
          userId={user.id}
          recipeAuthorId={recipe.author.id}
          recipeId={recipe.id}
          recipeName={recipe.recipeName}
          firstName={recipe.author.firstName}
          lastName={recipe.author.lastName}
          saved={recipe.saved}
        />
        <RecipeInfo
          ingredients={recipe.ingredients}
          recipeImg={recipe.recipePhotoLink}
          recipeName={recipe.recipeName}
        />
      </Box>
      <Box padding="30px" margin="30px 0" radius="20px">
        <HeaderWithAmount cookTime={recipe.cookTime} />
        <RecipeSteps steps={recipe.recipeSteps} />
      </Box>
    </PageTemplate>
  );
}

export default RecipeDetails;
