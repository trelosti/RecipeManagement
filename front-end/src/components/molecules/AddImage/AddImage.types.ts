import React from 'react';

export type AddImageProps = {
  name: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  defaultImage?: string;
  width?: string;
  height?: string;
  error?: string;
};
