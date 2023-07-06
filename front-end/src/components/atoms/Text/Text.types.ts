import React from 'react';

type TextWeight = 'light' | 'regular' | 'bold';
type TextAlign = 'start' | 'center' | 'end';
type TextColor = 'primary' | 'secondary' | 'default';
type TextElement = 'span' | 'p' | 'h1' | 'h2' | 'h3' | 'h4' | 'h5';

export type TextProps = {
  size?: number;
  weight?: TextWeight;
  align?: TextAlign;
  color?: TextColor;
  element?: TextElement;
  uppercase?: boolean;
  children: React.ReactNode;
};
