import React, { useState } from 'react';
import Box from '../../atoms/Box/Box';
import Image from '../../atoms/Image/Image';
import RecipeStep from '../../molecules/RecipeStep/RecipeStep';
import { RecipeStepsProps } from './RecipeSteps.types';
import prevArrow from '../../../app/assets/icons/prevArrov.png';
import nextArrow from '../../../app/assets/icons/nextArrov.png';

function RecipeSteps({ steps }: RecipeStepsProps) {
  const [currentStepIndex, setCurrentStepIndex] = useState(0);

  const choosePrevStep = () => {
    if (currentStepIndex <= 0) {
      setCurrentStepIndex(steps.length - 1);
    } else {
      setCurrentStepIndex((prev) => prev - 1);
    }
  };
  const chooseNextNext = () => {
    if (currentStepIndex >= steps.length - 1) {
      setCurrentStepIndex(0);
    } else {
      setCurrentStepIndex((prev) => prev + 1);
    }
  };

  return (
    <Box flex>
      <Box marginRight="50px" width="60%">
        {steps.map((step, index) => (
          <RecipeStep
            step={step}
            recipesLength={steps.length}
            currentStepIndex={currentStepIndex}
            index={index}
            key={step.id}
          />
        ))}
      </Box>
      <Box flex width="35%" height="440px" style={{ position: 'relative' }}>
        <Box onClick={choosePrevStep}>
          <Image
            src={prevArrow}
            style={{
              width: '50px',
              position: 'absolute',
              top: '45%',
              cursor: 'pointer',
            }}
          />
        </Box>

        <Image
          src={steps[currentStepIndex].stepPhotoLink}
          radius="20px"
          style={{ width: '450px', height: '440px', objectFit: 'cover' }}
        />
        <Box onClick={chooseNextNext}>
          <Image
            src={nextArrow}
            style={{
              width: '50px',
              position: 'absolute',
              top: '45%',
              right: '-15px',
              cursor: 'pointer',
            }}
          />
        </Box>
      </Box>
    </Box>
  );
}

export default RecipeSteps;
