import React from 'react';
import { TextareaProps } from './Textarea.types';
import AtomProps from '../../../app/types/AtomProps';
import getAtomProps from '../../../app/utils/getAtomProps';
import cn from '../../../app/utils/cn';
import styles from './Textarea.module.scss';

const Textarea = React.forwardRef<
  HTMLTextAreaElement,
  TextareaProps & AtomProps
>(
  (
    { name, value, onChange, width, height, placeholder, ...atomProps },
    ref
  ) => {
    const { className, style } = getAtomProps(atomProps);

    return (
      <textarea
        ref={ref}
        name={name}
        className={cn(styles.textarea, className)}
        placeholder={placeholder}
        style={{ ...style, width, height }}
        onChange={onChange}
        value={value || ''}
      />
    );
  }
);

export default Textarea;
