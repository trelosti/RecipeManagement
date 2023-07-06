import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from './store';

type AppSliceInitialSlice = {
  error: string;
  isAuth?: boolean;
};

const initialState: AppSliceInitialSlice = {
  error: '',
};

const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {
    setAppError(state, action: PayloadAction<string>) {
      state.error = action.payload;
    },
    setIsAuth(state, action: PayloadAction<boolean>) {
      state.isAuth = action.payload;
    },
  },
});

export const { setAppError, setIsAuth } = appSlice.actions;

export const getAppError = (state: RootState) => state.app.error;
export const getIsAuth = (state: RootState) => state.app.isAuth;

export default appSlice.reducer;
