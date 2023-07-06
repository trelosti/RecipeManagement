import { RecipeStep } from '../../../app/entities/RecipeItem';

export type RecipeStepProps = {
  recipesLength: number;
  step: RecipeStep;
  index: number;
  currentStepIndex: number;
};
