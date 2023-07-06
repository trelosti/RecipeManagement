import React from 'react';
import { ButtonProps } from './Button.types';
import getAtomProps from '../../../app/utils/getAtomProps';
import cn from '../../../app/utils/cn';
import styles from './Button.module.scss';
import AtomProps from '../../../app/types/AtomProps';

function Button({
  children,
  onClick,
  variant = 'fill',
  size = 'md',
  ariaLabel,
  ...atomProps
}: ButtonProps & AtomProps) {
  const { className, style } = getAtomProps(atomProps);

  return (
    <button
      type="button"
      className={cn(styles.button, styles[size], styles[variant], className)}
      style={style}
      aria-label={ariaLabel}
      onClick={onClick}
    >
      {children}
    </button>
  );
}

export default Button;
