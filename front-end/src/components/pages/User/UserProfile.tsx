import React from 'react';
import { useParams } from 'react-router-dom';
import Container from '../../molecules/Container/Container';
import Navbar from '../../organisms/Navbar/Navbar';
import avatar from '../../../app/assets/images/avatar.jpg';
import ProfileInfo from '../../organisms/ProfileInfo/ProfileInfo';
import Recipes from '../../organisms/Recipes/Recipes';
import {
  useGetRecipesByAuthorQuery,
  useGetUserProfileByIdQuery,
} from '../../../app/api/apiSlice';

function UserProfile() {
  const { id } = useParams();
  const { data: user } = useGetUserProfileByIdQuery(`${id}`);
  const { data: userRecipes } = useGetRecipesByAuthorQuery(`${id}`);

  if (!user) {
    return null;
  }

  return (
    <>
      <Navbar userPhoto={avatar} />
      <Container>
        <ProfileInfo
          firstName={user.firstName}
          lastName={user.lastName}
          userPhoto={avatar}
          recipesCount={userRecipes ? userRecipes.content.length : 0}
        />
        <Recipes userRecipes={userRecipes} />
      </Container>
    </>
  );
}

export default UserProfile;
