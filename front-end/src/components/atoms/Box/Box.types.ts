import React from 'react';

export type BoxJustify =
  | 'flex-start'
  | 'center'
  | 'flex-end'
  | 'space-around'
  | 'space-between'
  | 'space-evenly';

export type BoxAlign = 'flex-start' | 'center' | 'flex-end';

export type BoxDirection = 'row' | 'column' | 'row-reverse' | 'column-reverse';

export type BoxElevation = 0 | 1 | 2 | 3 | 4;

export type BoxBackground = 'default' | 'none';

export type BoxProps = {
  children: React.ReactNode;
  width?: string;
  height?: string;
  radius?: string;
  flex?: boolean;
  justify?: BoxJustify;
  align?: BoxAlign;
  direction?: BoxDirection;
  gap?: string;
  elevation?: BoxElevation;
  background?: BoxBackground;
  onClick?: React.MouseEventHandler<HTMLDivElement>;
};

export type GetBoxStylesArgs = Omit<BoxProps, 'children'>;
