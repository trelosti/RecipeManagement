import React from 'react';
import { BoxProps } from './Box.types';
import AtomProps from '../../../app/types/AtomProps';
import getAtomProps from '../../../app/utils/getAtomProps';
import cn from '../../../app/utils/cn';
import { getBoxStyles } from './Box.service';
import styles from './Box.module.scss';

function Box({
  height,
  width,
  flex,
  justify,
  align,
  direction,
  gap,
  radius,
  elevation = 0,
  children,
  background,
  onClick,
  ...atomProps
}: BoxProps & AtomProps) {
  const { className, style } = getAtomProps(atomProps);

  const boxStyles = getBoxStyles({
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
  });

  return (
    <div
      tabIndex={0}
      onKeyDown={() => {}}
      role="button"
      onClick={onClick}
      className={cn(styles.box, className)}
      style={{ ...boxStyles, ...style }}
    >
      {children}
    </div>
  );
}

export default Box;
