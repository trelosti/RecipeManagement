import React from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import { BrowserRouter } from 'react-router-dom';
import Link from './Link';

const meta: Meta<typeof Link> = {
  title: 'Link',
  component: Link,
};
export default meta;

type StoryType = StoryObj<typeof Link>;

export const Primary: StoryType = {
  decorators: [
    (Story) => (
      <BrowserRouter>
        <Story />
      </BrowserRouter>
    ),
  ],
  render: () => <Link to="/">Link</Link>,
};
