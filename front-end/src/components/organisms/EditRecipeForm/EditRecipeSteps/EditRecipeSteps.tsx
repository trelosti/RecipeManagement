import React from 'react';
import { useFieldArray } from 'react-hook-form';
import EditRecipeBlock from '../../../molecules/EditRecipeBlock/EditRecipeBlock';
import { ADD_STEP_BUTTON, RECIPE_STEPS_TITLE } from './EditRecipeSteps.const';
import { EditRecipeStepsProps } from './EditRecipeSteps.types';
import Button from '../../../atoms/Button/Button';
import EditRecipeStep from '../EditRecipeStep/EditRecipeStep';

function EditRecipeSteps({ stepsImages, control }: EditRecipeStepsProps) {
  const { fields, append, remove } = useFieldArray({
    control,
    name: 'steps',
  });

  return (
    <EditRecipeBlock
      title={RECIPE_STEPS_TITLE}
      flex
      direction="column"
      gap="20px"
    >
      {fields.map((item, index) => (
        <EditRecipeStep
          stepImage={stepsImages?.[index]}
          key={item.id}
          index={index}
          remove={remove}
          control={control}
        />
      ))}

      <Button
        onClick={() => {
          append({ step: '' });
        }}
        variant="frameless"
        marginTop="20px"
        marginX="auto"
      >
        {ADD_STEP_BUTTON}
      </Button>
    </EditRecipeBlock>
  );
}

export default EditRecipeSteps;
