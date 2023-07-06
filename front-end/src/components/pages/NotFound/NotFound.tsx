import React from 'react';
import Text from '../../atoms/Text/Text';
import Box from '../../atoms/Box/Box';
import { NOT_FOUND_TITLE } from './NotFound.const';

function NotFound() {
  return (
    <Box
      style={{
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translateX(-50%) translateY(-50%)',
      }}
      flex
      justify="center"
      align="center"
      padding="60px"
      radius="50px"
    >
      <Text size={80}>{NOT_FOUND_TITLE}</Text>
    </Box>
  );
}

export default NotFound;
