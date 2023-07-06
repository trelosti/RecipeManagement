import React from 'react';
import ProfileInfo from '../../organisms/ProfileInfo/ProfileInfo';
import PageTemplate from '../../templates/PageTemplate/PageTemplate';
import avatar from '../../../app/assets/images/avatar.jpg';
import Recipes from '../../organisms/Recipes/Recipes';
import {
  useGetRecipesByAuthorQuery,
  useGetSavedRecipesQuery,
  useGetUserProfileQuery,
} from '../../../app/api/apiSlice';

function Profile() {
  const { data: user } = useGetUserProfileQuery('');
  const { data: userRecipes } = useGetRecipesByAuthorQuery(`${user?.id}` || '');
  const { data: savedRecipes } = useGetSavedRecipesQuery('');

  if (!user) {
    return null;
  }

  return (
    <PageTemplate>
      <ProfileInfo
        firstName={user.firstName}
        lastName={user.lastName}
        userPhoto={avatar}
        recipesCount={userRecipes ? userRecipes.content.length : 0}
      />
      <Recipes userRecipes={userRecipes} savedRecipes={savedRecipes} />
    </PageTemplate>
  );
}

export default Profile;
