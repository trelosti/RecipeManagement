import SizePropValue from '../../../app/types/SizePropValue';

export type ButtonVariant = 'fill' | 'outline' | 'frameless';

export type ButtonProps = {
  children: React.ReactNode;
  onClick: () => void;
  variant?: ButtonVariant;
  size?: SizePropValue;
  ariaLabel?: string;
};
