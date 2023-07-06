import React, { useState } from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import Textarea from './Textarea';

const meta: Meta<typeof Textarea> = {
  title: 'Textarea',
  component: Textarea,
};
export default meta;

type Story = StoryObj<typeof Textarea>;

function TextareaWithHooks() {
  const [value, setValue] = useState<string>('');

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) =>
    setValue(e.target.value);

  return (
    <Textarea
      name="field"
      placeholder="field"
      value={value}
      onChange={onChange}
    />
  );
}

export const Primary: Story = {
  render: () => <TextareaWithHooks />,
};
