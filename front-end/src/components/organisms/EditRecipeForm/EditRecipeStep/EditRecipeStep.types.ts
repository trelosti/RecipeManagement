import { Control, UseFieldArrayRemove } from 'react-hook-form';
import { RecipeEditFormData } from '../EditRecipeForm.types';

export type EditRecipeStepProps = {
  stepImage?: string;
  index: number;
  remove: UseFieldArrayRemove;
  control: Control<RecipeEditFormData>;
};
