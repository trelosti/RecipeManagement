import { Control } from 'react-hook-form';
import { RecipeEditFormData } from '../EditRecipeForm.types';

export type EditRecipeStepsProps = {
  stepsImages?: string[];
  control: Control<RecipeEditFormData>;
};
