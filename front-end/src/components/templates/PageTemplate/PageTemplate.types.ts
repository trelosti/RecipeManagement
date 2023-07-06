import React from 'react';
import { BoxBackground } from '../../atoms/Box/Box.types';

export type PageTemplateProps = {
  background?: BoxBackground;
  children: React.ReactNode;
};
