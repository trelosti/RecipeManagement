import React from 'react';
import Box from '../../atoms/Box/Box';
import { ContainerProps } from './Container.type';

function Container({ background, children }: ContainerProps) {
  return (
    <Box
      width="1300px"
      margin="0 auto"
      style={{ minHeight: '90vh' }}
      background={background}
    >
      {children}
    </Box>
  );
}

export default Container;
