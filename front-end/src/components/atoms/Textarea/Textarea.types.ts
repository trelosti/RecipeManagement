import React from 'react';

export type TextareaProps = {
  name: string;
  onChange?: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  value?: string;
  type?: string;
  placeholder?: string;
  width?: string;
  height?: string;
};
