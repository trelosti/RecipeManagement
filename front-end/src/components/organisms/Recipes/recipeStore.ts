// This file contained a temporary mocked up data. This will be removed in the next feature.
import recipePhoto from '../../../app/assets/images/recipeImg.png';
import recipePhoto1 from '../../../app/assets/images/recipeImg1.png';
import recipePhoto2 from '../../../app/assets/images/recipeImg2.png';
import recipePhoto3 from '../../../app/assets/images/recipeImg3.png';

const allRecipes = [
  {
    id: 1,
    recipeName: 'parmesan',
    recipePhotoLink: recipePhoto,
  },
  {
    id: 2,
    recipeName: 'chicken tikka',
    recipePhotoLink: recipePhoto2,
  },
];
const savedRecipes = [
  {
    id: 3,
    recipeName: 'lasagna',
    recipePhotoLink: recipePhoto1,
  },
  {
    id: 4,
    recipeName: 'summer pasta',
    recipePhotoLink: recipePhoto3,
  },
];

export { allRecipes, savedRecipes };
