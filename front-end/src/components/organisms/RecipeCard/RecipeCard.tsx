import React from 'react';
import { useNavigate } from 'react-router-dom';
import Box from '../../atoms/Box/Box';
import Image from '../../atoms/Image/Image';
import savedIcon from '../../../app/assets/icons/saved.png';
import unsavedIcon from '../../../app/assets/icons/unsaved.png';
import Text from '../../atoms/Text/Text';
import { RecipeCardProps } from './RecipeCard.type';
import { useToggleFavoriteRecipeMutation } from '../../../app/api/apiSlice';
import { ROUTES } from '../../../router';

function RecipeCard({
  recipeName,
  isSaved,
  recipeId,
  recipePhoto,
}: RecipeCardProps) {
  const navigate = useNavigate();
  const [toggleRecipe] = useToggleFavoriteRecipeMutation();

  const handleRecipeDetails = () => {
    navigate(`${ROUTES.RECIPES}/${recipeId}`);
  };
  const handleSaveRecipe = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    event.stopPropagation();
    toggleRecipe(recipeId);
  };

  return (
    <Box
      width="598px"
      radius="10px"
      elevation={2}
      onClick={handleRecipeDetails}
    >
      <Box width="100%" radius="10px">
        <Image
          src={recipePhoto}
          radius="10px"
          style={{ width: '598px', height: '425px', objectFit: 'cover' }}
        />
      </Box>
      <Box padding="12px" radius="10px" flex justify="space-between">
        <Text size={22} uppercase>
          {recipeName}
        </Text>
        <button type="button" onClick={handleSaveRecipe}>
          <Image
            src={isSaved ? savedIcon : unsavedIcon}
            style={{ width: '35px', cursor: 'pointer' }}
          />
        </button>
      </Box>
    </Box>
  );
}

export default RecipeCard;
