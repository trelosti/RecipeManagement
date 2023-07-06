import { createApi } from '@reduxjs/toolkit/query/react';
import User from '../entities/User';
import { SignInRequest, SignUpRequest, UpdateRecipeRequest } from './apiTypes';
import { AUTH_API_URL, RECIPES_API_URL, USERS_API_URL } from './apiRoutes';
import baseQueryWithReauth from './baseQuery';
import { setIsAuth } from '../store/appSlice';
import RecipeInfo from '../entities/RecipeItem';
import { UserRecipes } from '../entities/UserRecipes';

export const apiSlice = createApi({
  reducerPath: 'pokemonApi',
  baseQuery: baseQueryWithReauth,
  tagTypes: ['User', 'Recipe', 'Recipes'],
  endpoints: (builder) => ({
    getUserProfile: builder.query<User, string>({
      query: () => `${USERS_API_URL}/profile`,
      providesTags: ['User'],
    }),
    getUserProfileById: builder.query<User, string>({
      query: (id) => `${USERS_API_URL}/${id}`,
      providesTags: ['User'],
    }),
    getRecipeById: builder.query<RecipeInfo, string>({
      query: (id) => `${RECIPES_API_URL}/${id}`,
      providesTags: ['Recipe'],
    }),
    getRecipesByAuthor: builder.query<UserRecipes, string>({
      query: (id) => `${RECIPES_API_URL}/user/${id}?page=0&size=10`,
      providesTags: ['Recipes'],
    }),
    getSavedRecipes: builder.query<UserRecipes, string>({
      query: () => `${RECIPES_API_URL}/favorites?page=0&size=10`,
      providesTags: ['Recipes'],
    }),
    toggleFavoriteRecipe: builder.mutation<RecipeInfo, number>({
      query: (recipeId) => ({
        url: `${RECIPES_API_URL}/favorites/${recipeId}`,
        method: 'PUT',
      }),
      invalidatesTags: ['Recipe', 'Recipes'],
    }),
    signUp: builder.mutation<User, SignUpRequest>({
      query: (data) => ({
        url: `${AUTH_API_URL}/sign-up`,
        method: 'POST',
        body: data,
      }),
      async onQueryStarted(arg, { queryFulfilled, dispatch }) {
        try {
          const result = await queryFulfilled;

          if (result.data) {
            localStorage.setItem('isAuth', 'true');
            dispatch(setIsAuth(true));
          }
        } catch (e) {
          // eslint-disable-next-line no-console
          console.log(e);
        }
      },
    }),
    signIn: builder.mutation<User, SignInRequest>({
      query: (data) => ({
        url: `${AUTH_API_URL}/sign-in`,
        method: 'POST',
        body: data,
      }),
      async onQueryStarted(arg, { queryFulfilled, dispatch }) {
        try {
          const result = await queryFulfilled;

          if (result.data) {
            localStorage.setItem('isAuth', 'true');
            dispatch(setIsAuth(true));
          }
        } catch (e) {
          // eslint-disable-next-line no-console
          console.log(e);
        }
      },
    }),
    createRecipe: builder.mutation<RecipeInfo, FormData>({
      query: (data) => ({
        url: `${RECIPES_API_URL}`,
        formData: true,
        method: 'POST',
        body: data,
      }),
    }),
    // getRecipeById: builder.query<RecipeInfo, string>({
    //   query: (data) => `${RECIPES_API_URL}/${data}`,
    // }),
    update: builder.mutation<RecipeInfo, UpdateRecipeRequest>({
      query: ({ formData, recipeId }) => ({
        url: `${RECIPES_API_URL}/${recipeId}`,
        formData: true,
        method: 'PUT',
        body: formData,
      }),
    }),
  }),
});

export const {
  useGetUserProfileQuery,
  useGetUserProfileByIdQuery,
  useGetRecipeByIdQuery,
  useGetRecipesByAuthorQuery,
  useGetSavedRecipesQuery,
  useToggleFavoriteRecipeMutation,
  useSignUpMutation,
  useSignInMutation,
  useCreateRecipeMutation,
  useUpdateMutation,
} = apiSlice;
