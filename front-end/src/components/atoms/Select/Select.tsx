import React, { ChangeEvent } from 'react';
import { SelectProps } from './Select.types';
import AtomProps from '../../../app/types/AtomProps';
import getAtomProps from '../../../app/utils/getAtomProps';
import cn from '../../../app/utils/cn';
import styles from './Select.module.scss';

const Select = React.forwardRef<HTMLSelectElement, SelectProps & AtomProps>(
  (
    { name, placeholder, value, onChange, options, width, ...atomProps },
    ref
  ) => {
    const { className, style } = getAtomProps(atomProps);

    const handleOnChange = (event: ChangeEvent<HTMLSelectElement>) => {
      onChange(event.target.value);
    };

    return (
      <select
        ref={ref}
        name={name}
        className={cn(styles.select, className)}
        style={{ ...style, width }}
        onChange={handleOnChange}
        value={value}
      >
        {placeholder && (
          <option value="" disabled>
            {placeholder}
          </option>
        )}
        {options.map(({ label, value: optionValue }) => (
          <option key={optionValue} value={optionValue}>
            {label}
          </option>
        ))}
      </select>
    );
  }
);

export default Select;
