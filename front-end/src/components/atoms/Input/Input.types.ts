import React from 'react';

export type InputProps = {
  name: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  value?: string | number;
  type?: string;
  placeholder?: string;
  width?: string;
};
