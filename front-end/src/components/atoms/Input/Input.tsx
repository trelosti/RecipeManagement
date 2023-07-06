import React from 'react';
import { InputProps } from './Input.types';
import AtomProps from '../../../app/types/AtomProps';
import getAtomProps from '../../../app/utils/getAtomProps';
import cn from '../../../app/utils/cn';
import styles from './Input.module.scss';

const Input = React.forwardRef<HTMLInputElement, InputProps & AtomProps>(
  (
    { name, type = 'text', onChange, value, placeholder, width, ...atomProps },
    ref
  ) => {
    const { className, style } = getAtomProps(atomProps);

    return (
      <input
        ref={ref}
        name={name}
        className={cn(styles.input, className)}
        type={type}
        placeholder={placeholder}
        style={{ ...style, width }}
        onChange={onChange}
        value={value || ''}
      />
    );
  }
);

export default Input;
