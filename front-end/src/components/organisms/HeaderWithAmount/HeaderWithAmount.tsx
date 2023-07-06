import React from 'react';
import Text from '../../atoms/Text/Text';
import RecipeAmount from '../../molecules/RecipeAmount/RecipeAmount';
import Box from '../../atoms/Box/Box';
import { RecipeAmountProps } from '../../molecules/RecipeAmount/RecipeAmount.types';
import STEPS_HEADER from './HeaderWithAmount.const';

function HeaderWithAmount({ cookTime }: RecipeAmountProps) {
  return (
    <Box flex align="center" marginBottom="20px">
      <Text size={30} weight="bold" color="secondary" marginRight="20px">
        {STEPS_HEADER}
      </Text>
      <RecipeAmount cookTime={cookTime} />
    </Box>
  );
}

export default HeaderWithAmount;
