import React from 'react';
import { useNavigate } from 'react-router-dom';
import Link from '../../atoms/Link/Link';
import Box from '../../atoms/Box/Box';
import Text from '../../atoms/Text/Text';
import Button from '../../atoms/Button/Button';
import unsavedIcon from '../../../app/assets/icons/unsaved.png';
import savedIcon from '../../../app/assets/icons/saved.png';
import edit from '../../../app/assets/icons/edite.png';
import { RecipeHeaderProps } from './RecipeHeader.types';
import { useToggleFavoriteRecipeMutation } from '../../../app/api/apiSlice';
import Image from '../../atoms/Image/Image';

function RecipeHeader({
  userId,
  recipeAuthorId,
  recipeId,
  recipeName,
  firstName,
  lastName,
  saved,
}: RecipeHeaderProps) {
  const [toggleRecipe] = useToggleFavoriteRecipeMutation();
  const navigate = useNavigate();

  const handleSaveRecipe = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    event.stopPropagation();
    toggleRecipe(recipeId);
  };
  const handleEditRecipe = () => {
    navigate(`/recipes/${recipeId}/edit`);
  };

  return (
    <Box flex justify="space-between">
      <Box marginBottom="20px" style={{ flexGrow: 2 }}>
        <Text
          size={36}
          weight="bold"
          color="secondary"
          align="center"
          uppercase
          marginBottom="12px"
        >
          {recipeName}
        </Text>
        <Link
          to={`/profile/${recipeAuthorId}`}
          style={{ display: 'block', textAlign: 'center' }}
        >
          by: {firstName} {lastName}
        </Link>
      </Box>
      <Box flex height="45px">
        <button type="button" onClick={handleSaveRecipe}>
          <Image
            marginRight="15px"
            src={saved ? savedIcon : unsavedIcon}
            style={{ width: '35px', cursor: 'pointer' }}
          />
        </button>
        {recipeAuthorId === userId && (
          <Button variant="fill" onClick={handleEditRecipe}>
            <Image src={edit} alt="edit" /> Edit
          </Button>
        )}
      </Box>
    </Box>
  );
}
export default RecipeHeader;
