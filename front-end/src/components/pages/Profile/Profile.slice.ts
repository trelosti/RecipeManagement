import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from '../../../app/store/store';
import { ProfileSliceInitialSlice, selectedTabType } from './Profile.types';
import { TAB_RECIPES } from '../../molecules/Tabs/Tabs.const';

const initialState: ProfileSliceInitialSlice = {
  isSelectedRecipe: true,
  isSelectedSaved: false,
  selectedTab: TAB_RECIPES,
};

const profileSlice = createSlice({
  name: 'profile',
  initialState,
  reducers: {
    setIsSelectedRecipe(state, action: PayloadAction<boolean>) {
      state.isSelectedRecipe = action.payload;
    },
    setIsSelectedSaved(state, action: PayloadAction<boolean>) {
      state.isSelectedSaved = action.payload;
    },
    setSelectedTab(state, action: PayloadAction<selectedTabType>) {
      state.selectedTab = action.payload;
    },
  },
});

export const { setIsSelectedRecipe, setIsSelectedSaved, setSelectedTab } =
  profileSlice.actions;

export const getIsSelectedRecipe = (state: RootState) =>
  state.profile.isSelectedRecipe;
export const getIsSelectedSaved = (state: RootState) =>
  state.profile.isSelectedSaved;
export const getSelectedTab = (state: RootState) => state.profile.selectedTab;

export default profileSlice.reducer;
