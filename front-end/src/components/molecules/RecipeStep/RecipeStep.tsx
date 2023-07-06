import React from 'react';
import Box from '../../atoms/Box/Box';
import Text from '../../atoms/Text/Text';
import { RecipeStepProps } from './RecipeStep.types';

function RecipeStep({
  recipesLength,
  step,
  index,
  currentStepIndex,
}: RecipeStepProps) {
  const stepIndex = index + 1;

  return (
    <Box paddingBottom="25px">
      <Text
        size={22}
        marginRight="20px"
        weight="bold"
        style={{ display: 'inline' }}
      >{`${stepIndex}/${recipesLength} `}</Text>
      <Text
        size={22}
        weight={index === currentStepIndex ? 'bold' : 'regular'}
        color={index === currentStepIndex ? 'secondary' : 'default'}
        style={{ display: 'inline' }}
      >
        {step.description}
      </Text>
    </Box>
  );
}

export default RecipeStep;
