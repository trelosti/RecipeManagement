import React from 'react';
import Box from '../../atoms/Box/Box';
import Text from '../../atoms/Text/Text';
import { useAppDispatch, useAppSelector } from '../../../app/store/hooks';
import { setSelectedTab } from '../../pages/Profile/Profile.slice';
import { TAB_RECIPES, TAB_SAVED } from './Tabs.const';

function Tabs() {
  const dispatch = useAppDispatch();
  const { selectedTab } = useAppSelector((state) => state.profile);

  const selectRecipe = () => {
    dispatch(setSelectedTab(TAB_RECIPES));
  };
  const selectSaved = () => {
    dispatch(setSelectedTab(TAB_SAVED));
  };

  return (
    <Box
      width="94%"
      height="95px"
      align="center"
      margin="0 auto"
      flex
      justify="space-around"
      style={{ borderBottom: '2px solid #824670' }}
    >
      <button type="button" onClick={selectRecipe}>
        <Box style={{ cursor: 'pointer' }}>
          <Text
            size={24}
            weight={selectedTab === TAB_RECIPES ? 'bold' : 'light'}
          >
            {TAB_RECIPES}
          </Text>
        </Box>
      </button>
      <button type="button" onClick={selectSaved}>
        <Box style={{ cursor: 'pointer' }}>
          <Text size={24} weight={selectedTab === TAB_SAVED ? 'bold' : 'light'}>
            {TAB_SAVED}
          </Text>
        </Box>
      </button>
    </Box>
  );
}

export default Tabs;
