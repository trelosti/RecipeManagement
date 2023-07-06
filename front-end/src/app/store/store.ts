import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import AppSlice from './appSlice';
import { apiSlice } from '../api/apiSlice';
import ProfileSlice from '../../components/pages/Profile/Profile.slice';

export const store = configureStore({
  reducer: {
    app: AppSlice,
    profile: ProfileSlice,
    [apiSlice.reducerPath]: apiSlice.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(apiSlice.middleware),
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
