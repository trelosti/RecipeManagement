import React from 'react';
import { BoxBackground } from '../../atoms/Box/Box.types';

export type ContainerProps = {
  background?: BoxBackground;
  children: React.ReactNode;
};
