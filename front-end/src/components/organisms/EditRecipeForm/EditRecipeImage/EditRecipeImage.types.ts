import { Control } from 'react-hook-form';
import { RecipeEditFormData } from '../EditRecipeForm.types';

export type EditRecipeImageProps = {
  defaultImage?: string;
  control: Control<RecipeEditFormData>;
};
