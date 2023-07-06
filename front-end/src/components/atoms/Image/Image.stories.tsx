import React from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import Image from './Image';
import image from '../../../app/assets/images/auth-bg.jpeg';

const meta: Meta<typeof Image> = {
  title: 'Image',
  component: Image,
};
export default meta;

type Story = StoryObj<typeof Image>;

export const Primary: Story = {
  args: {
    src: image,
  },
};
