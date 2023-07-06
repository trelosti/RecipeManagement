import { BoxProps } from '../../atoms/Box/Box.types';
import AtomProps from '../../../app/types/AtomProps';

export type EditRecipeBlockProps = Omit<
  BoxProps & AtomProps,
  'padding' | 'radius' | 'background'
> & {
  // eslint-disable-next-line react/require-default-props
  title?: string;
};
