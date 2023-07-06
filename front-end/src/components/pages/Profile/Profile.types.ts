import { TAB_RECIPES, TAB_SAVED } from '../../molecules/Tabs/Tabs.const';

export type selectedTabType = typeof TAB_RECIPES | typeof TAB_SAVED;
export type ProfileSliceInitialSlice = {
  isSelectedRecipe: boolean;
  isSelectedSaved: boolean;
  selectedTab: selectedTabType;
};
