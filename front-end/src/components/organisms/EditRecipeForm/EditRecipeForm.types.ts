import { MeasureUnits } from '../../../app/entities/Ingredient';

export type IngredientItem = {
  ingredientName: string;
  quantity: string;
  measureUnit: MeasureUnits;
};

export type StepItem = {
  step: string;
  image?: FileList;
};

export type RecipeEditFormData = {
  recipeName: string;
  recipeImage?: FileList;
  ingredients: IngredientItem[];
  steps: StepItem[];
  cookTime: string;
};

export type EditRecipeFormProps = {
  recipeImage?: string;
  stepsImages?: string[];
  onSubmit: (data: RecipeEditFormData) => void;
  defaultValues?: RecipeEditFormData;
  onCancel: () => void;
};
