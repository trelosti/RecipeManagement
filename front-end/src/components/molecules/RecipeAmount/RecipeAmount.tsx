import React from 'react';
import Box from '../../atoms/Box/Box';
import Text from '../../atoms/Text/Text';
import { RecipeAmountProps } from './RecipeAmount.types';
import TIME_UNIT from './RecipeAmount.const';

function RecipeAmount({ cookTime }: RecipeAmountProps) {
  return (
    <Box
      width="85px"
      height="85px"
      radius="50%"
      style={{ border: '4px solid #A18F7A' }}
      flex
      align="center"
      justify="center"
    >
      <Text size={22} color="default">
        {cookTime} {TIME_UNIT}
      </Text>
    </Box>
  );
}

export default RecipeAmount;
