import { To } from 'react-router-dom';
import React from 'react';

export type LinkProps = {
  to: To;
  replace?: boolean;
  children: React.ReactNode;
};
