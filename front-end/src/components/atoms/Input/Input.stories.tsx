import React, { useState } from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import Input from './Input';

const meta: Meta<typeof Input> = {
  title: 'Input',
  component: Input,
};
export default meta;

type Story = StoryObj<typeof Input>;

function InputWithHooks() {
  const [value, setValue] = useState('');

  return (
    <Input
      name="field"
      placeholder="field"
      value={value}
      onChange={(e) => setValue(e.target.value)}
      type="text"
    />
  );
}

export const Primary: Story = {
  render: () => <InputWithHooks />,
};
