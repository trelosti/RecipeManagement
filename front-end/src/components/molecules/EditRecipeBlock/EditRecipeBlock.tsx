import React from 'react';
import Box from '../../atoms/Box/Box';
import Text from '../../atoms/Text/Text';
import { EditRecipeBlockProps } from './EditRecipeBlock.types';

function EditRecipeBlock({
  title,
  children,
  ...boxProps
}: EditRecipeBlockProps) {
  return (
    <Box padding="20px" radius="20px" {...boxProps}>
      {title && (
        <Text color="default" size={30} weight="bold" marginBottom="10px">
          {title}
        </Text>
      )}

      {children}
    </Box>
  );
}

export default EditRecipeBlock;
