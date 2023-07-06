import React, { useState } from 'react';
import Box from '../../atoms/Box/Box';
import Image from '../../atoms/Image/Image';
import IngredientsList from '../../molecules/IngredientsList/IngredientsList';
import { RecipeInfoProps } from './RecipeInfo.types';
import ModalPhoto from '../../molecules/ModalPhoto/ModalPhoto';

function RecipeInfo({ ingredients, recipeImg, recipeName }: RecipeInfoProps) {
  const [isShow, setIsShow] = useState(false);
  const handleOpenModal = () => setIsShow(true);
  const handleCloseModal = () => setIsShow(false);

  return (
    <Box flex>
      <ModalPhoto
        isShow={isShow}
        onClick={handleCloseModal}
        recipePhoto={recipeImg}
      />
      <Box width="780px" radius="20px" onClick={handleOpenModal}>
        <Image src={recipeImg} alt={recipeName} radius="20px" />
      </Box>
      <Box marginLeft="45px">
        <IngredientsList ingredients={ingredients} />
      </Box>
    </Box>
  );
}

export default RecipeInfo;
