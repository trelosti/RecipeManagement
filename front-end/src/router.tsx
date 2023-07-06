import React from 'react';
import { createBrowserRouter } from 'react-router-dom';
import SignUpPage from './components/pages/SignUpPage/SignUpPage';
import SignInPage from './components/pages/SignInPage/SignInPage';
import ProtectedRoutes from './components/hocs/ProtectedRoute/ProtectedRoute';
import Profile from './components/pages/Profile/Profile';
import EditRecipe from './components/pages/EditRecipe/EditRecipe';
import CreateRecipe from './components/pages/CreateRecipe/CreateRecipe';
import NotFound from './components/pages/NotFound/NotFound';
import RecipeDetails from './components/pages/RecipeDetails/RecipeDetails';
import UserProfile from './components/pages/User/UserProfile';

const RECIPES_ROUTES_PREFIX = '/recipes';

export const ROUTES = {
  ROOT: '/',
  SIGN_UP: '/sign-up',
  SIGN_IN: '/sign-in',
  PROFILE: '/profile',
  RECIPE_EDIT: `${RECIPES_ROUTES_PREFIX}/:recipeId/edit`,
  CREATE_RECIPE: '/create-recipe',
  NOT_FOUND: '*',
  RECIPES: '/recipes',
  USER_PROFILE: '/profile/:id',
  RECIPE_DETAILS: '/recipes/:id',
};

const router = (isAuth: boolean) =>
  createBrowserRouter([
    {
      path: ROUTES.ROOT,
      element: (
        <ProtectedRoutes isAllowed={!isAuth} redirectTo={ROUTES.PROFILE} />
      ),
      children: [
        {
          path: ROUTES.SIGN_UP,
          element: <SignUpPage />,
        },
        {
          path: ROUTES.SIGN_IN,
          element: <SignInPage />,
        },
      ],
    },

    {
      path: ROUTES.ROOT,
      element: (
        <ProtectedRoutes isAllowed={isAuth} redirectTo={ROUTES.SIGN_IN} />
      ),
      children: [
        {
          path: ROUTES.PROFILE,
          element: <Profile />,
        },
        {
          path: ROUTES.RECIPE_EDIT,
          element: <EditRecipe />,
        },
        {
          path: ROUTES.CREATE_RECIPE,
          element: <CreateRecipe />,
        },
        {
          path: ROUTES.RECIPE_DETAILS,
          element: <RecipeDetails />,
        },
      ],
    },
    {
      path: ROUTES.NOT_FOUND,
      element: <NotFound />,
    },
    {
      path: ROUTES.USER_PROFILE,
      element: <UserProfile />,
    },
  ]);

export default router;
