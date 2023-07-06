import React from 'react';
import Input from '../../atoms/Input/Input';
import Text from '../../atoms/Text/Text';
import { TextFieldProps } from './TextField.types';

const TextField = React.forwardRef<HTMLInputElement, TextFieldProps>(
  ({ error, ...inputProps }, ref) => (
    <div>
      <Input width="100%" {...inputProps} ref={ref} />
      {error && <Text color="primary">{error}</Text>}
    </div>
  )
);

export default TextField;
