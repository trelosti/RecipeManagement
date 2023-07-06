import { RecipeEditFormData } from './EditRecipeForm.types';
import { MeasureUnits } from '../../../app/entities/Ingredient';

export const CANCEL_BUTTON = 'Cancel';
export const PUBLISH_BUTTON = 'Publish';
export const RECIPE_EMPTY_FORM_DATA: RecipeEditFormData = {
  recipeName: '',
  ingredients: [
    {
      ingredientName: '',
      quantity: '',
      measureUnit: MeasureUnits.MILLILITER,
    },
    {
      ingredientName: '',
      quantity: '',
      measureUnit: MeasureUnits.MILLILITER,
    },
  ],
  steps: [
    {
      step: '',
    },
    {
      step: '',
    },
  ],
  cookTime: '',
};
