import React from 'react';
import Box from '../../atoms/Box/Box';
import Tabs from '../../molecules/Tabs/Tabs';
import RecipeCard from '../RecipeCard/RecipeCard';
import { useAppSelector } from '../../../app/store/hooks';
import { TAB_RECIPES } from '../../molecules/Tabs/Tabs.const';
import { RecipesProps } from './Resipes.types';
import Text from '../../atoms/Text/Text';
import NO_RECIPES from './Recipes.const';

function Recipes({ userRecipes, savedRecipes }: RecipesProps) {
  const { selectedTab } = useAppSelector((state) => state.profile);
  let recipes;
  if (userRecipes) {
    recipes = selectedTab === TAB_RECIPES ? userRecipes : savedRecipes;
  }

  return (
    <Box>
      <Tabs />
      <Box
        width="94%"
        margin="0 auto"
        padding="30px 0"
        flex
        gap="32px 24px"
        style={{ flexWrap: 'wrap' }}
      >
        {recipes ? (
          recipes.content.map((recipe) => (
            <RecipeCard
              recipeId={recipe.id}
              recipeName={recipe.recipeName}
              recipePhoto={recipe.recipePhotoLink}
              isSaved={recipe.saved}
              key={recipe.id}
            />
          ))
        ) : (
          <Box height="60vh" margin="0 auto">
            <Text size={32}>{NO_RECIPES}</Text>
          </Box>
        )}
      </Box>
    </Box>
  );
}

export default Recipes;
