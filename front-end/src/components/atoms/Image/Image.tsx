import React from 'react';
import { ImageProps } from './Image.types';
import AtomProps from '../../../app/types/AtomProps';
import getAtomProps from '../../../app/utils/getAtomProps';
import cn from '../../../app/utils/cn';
import styles from './Image.module.scss';

function Image({ src, alt, radius, ...atomProps }: ImageProps & AtomProps) {
  const { className, style } = getAtomProps(atomProps);

  return (
    <img
      className={cn(styles.image, className)}
      src={src}
      alt={alt}
      style={{ ...style, borderRadius: radius }}
    />
  );
}

export default Image;
