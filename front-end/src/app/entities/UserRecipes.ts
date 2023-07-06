type UserRecipe = {
  id: number;
  recipeName: string;
  recipePhotoLink: string;
  saved: boolean;
};

export type UserRecipes = {
  size: number;
  page: number;
  content: UserRecipe[];
  last: boolean;
  totalElements: number;
  totalPages: number;
};
