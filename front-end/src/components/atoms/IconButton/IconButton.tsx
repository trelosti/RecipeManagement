import React from 'react';
import { IconButtonProps } from './IconButton.types';
import getAtomProps from '../../../app/utils/getAtomProps';
import styles from './IconButton.module.scss';
import cn from '../../../app/utils/cn';
import AtomProps from '../../../app/types/AtomProps';

function IconButton({
  src,
  size = 'md',
  onClick,
  ariaLabel,
  ...atomProps
}: IconButtonProps & AtomProps) {
  const { className, style } = getAtomProps(atomProps);

  return (
    <button
      type="button"
      onClick={onClick}
      aria-label={ariaLabel}
      className={cn(styles.iconButton, styles[size], className)}
      style={style}
    >
      <img src={src} alt={ariaLabel} />
    </button>
  );
}

export default IconButton;
