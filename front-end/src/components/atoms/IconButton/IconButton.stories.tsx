import React from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import IconButton from './IconButton';
import icon from '../../../app/assets/icons/saved.png';

const meta: Meta<typeof IconButton> = {
  title: 'IconButton',
  component: IconButton,
};

export default meta;

type Story = StoryObj<typeof IconButton>;

export const Primary: Story = {
  args: {
    src: icon,
  },
};
