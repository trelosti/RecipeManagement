import React from 'react';
import Box from '../../atoms/Box/Box';
import Text from '../../atoms/Text/Text';
import PROFILE_INFO_CONTENT from './ProfileInfoItem.const';
import { ProfileInfoItemProps } from './ProfileInfoItem.type';

function ProfileInfoItem({ recipesCount }: ProfileInfoItemProps) {
  return (
    <Box width="100px">
      <Text size={20} weight="bold" align="center" color="default">
        {recipesCount}
      </Text>
      <Text size={20} weight="regular" align="center" color="default">
        {PROFILE_INFO_CONTENT}
      </Text>
    </Box>
  );
}

export default ProfileInfoItem;
