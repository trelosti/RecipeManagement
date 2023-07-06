import SizePropValue from '../../../app/types/SizePropValue';

export type IconButtonProps = {
  src: string;
  size?: SizePropValue;
  onClick: () => void;
  ariaLabel?: string;
};
