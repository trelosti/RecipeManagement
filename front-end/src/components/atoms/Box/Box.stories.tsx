import React from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import Box from './Box';

const meta: Meta<typeof Box> = {
  title: 'Box',
  component: Box,
};
export default meta;

type Story = StoryObj<typeof Box>;

export const Primary: Story = {
  args: {
    children: (
      <>
        <Box>Child 1</Box>
        <Box>Child 2</Box>
      </>
    ),
    elevation: 1,
  },
};
