import { CSSProperties } from 'react';
import { GetBoxStylesArgs } from './Box.types';

// eslint-disable-next-line import/prefer-default-export
export const getBoxStyles = ({
  width,
  height,
  gap,
  radius,
  elevation,
  flex,
  justify,
  direction,
  align,
  background,
}: GetBoxStylesArgs) => {
  const result: CSSProperties = {
    width,
    height,
    borderRadius: radius,
    background,
  };

  if (elevation) {
    result.boxShadow = `0px ${elevation}px 4px rgba(0, 0, 0, 0.25)`;
  }

  if (flex) {
    result.display = 'flex';
    result.justifyContent = justify;
    result.flexDirection = direction;
    result.alignItems = align;
    result.gap = gap;
  }

  return result;
};
