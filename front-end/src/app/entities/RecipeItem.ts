import Ingredient from './Ingredient';

export type RecipeItem = {
  id: number;
  recipeName: string;
  recipePhotoLink: string;
};

export type RecipeList = {
  content: RecipeItem[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  last: number;
};

export type RecipeAuthor = {
  id: number;
  login: string;
  firstName: string;
  lastName: string;
  userPhotoLink: string;
};

export type RecipeStep = {
  id: string;
  description: string;
  stepPhotoLink: string;
};

type RecipeInfo = {
  id: number;
  recipeName: string;
  author: RecipeAuthor;
  recipePhotoLink: string;
  recipeSteps: RecipeStep[];
  cookTime: number;
  ingredients: Ingredient[];
  createdAt: string;
  updatedAt: string;
  saved: boolean;
};

export default RecipeInfo;
