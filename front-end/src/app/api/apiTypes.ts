import Ingredient from '../entities/Ingredient';

export type SignUpRequest = {
  firstName: string;
  lastName: string;
  login: string;
  password: string;
};

export type SignInRequest = {
  login: string;
  password: string;
};

export type RecipeCreatingDto = {
  recipeName: string;
  steps: string[];
  ingredients: Omit<Ingredient, 'id'>[];
  cookTime: number;
};

export type UpdateRecipeRequest = {
  recipeId: string;
  formData: FormData;
};
