import React, { useState } from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import Select from './Select';

const meta: Meta<typeof Select> = {
  title: 'Select',
  component: Select,
};
export default meta;

type Story = StoryObj<typeof Select>;

function SelectWithHooks() {
  const [value, setValue] = useState<string>('');

  const onChange = (newValue: string) => setValue(newValue);

  return (
    <Select
      name="field"
      placeholder="select"
      options={[
        { label: 'option 1', value: 'option 1' },
        { label: 'option 2', value: 'option 2' },
      ]}
      value={value}
      onChange={onChange}
    />
  );
}

export const Primary: Story = {
  render: () => <SelectWithHooks />,
};
