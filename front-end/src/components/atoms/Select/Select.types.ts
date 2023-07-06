export type SelectOption = {
  label: string;
  value: string;
};

export type SelectProps = {
  name?: string;
  value: string;
  onChange: (value: string) => void;
  options: SelectOption[];
  placeholder?: string;
  width?: string;
};
