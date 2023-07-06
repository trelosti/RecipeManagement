import React from 'react';
import { TextProps } from './Text.types';
import getAtomProps from '../../../app/utils/getAtomProps';
import styles from './Text.module.scss';
import cn from '../../../app/utils/cn';
import AtomProps from '../../../app/types/AtomProps';

function Text({
  children,
  align = 'start',
  color = 'primary',
  weight = 'regular',
  size = 15,
  element = 'span',
  uppercase = false,
  ...atomProps
}: TextProps & AtomProps) {
  const { className, style } = getAtomProps(atomProps);

  return (
    <span
      role={element}
      className={cn(styles.text, styles[color], styles[weight], className)}
      style={{
        ...style,
        fontSize: `${size}px`,
        textAlign: align,
        textTransform: uppercase ? 'uppercase' : 'none',
      }}
    >
      {children}
    </span>
  );
}

export default Text;
