import { Control, UseFieldArrayRemove } from 'react-hook-form';
import { RecipeEditFormData } from '../EditRecipeForm.types';
import { SelectOption } from '../../../atoms/Select/Select.types';

export type EditRecipeIngredientProps = {
  remove: UseFieldArrayRemove;
  index: number;
  control: Control<RecipeEditFormData>;
};

export const MEASURE_UNIT_OPTIONS: SelectOption[] = [
  {
    label: 'ml',
    value: 'MILLILITER',
  },
  {
    label: 'gr',
    value: 'GRAM',
  },
  {
    label: 'psc',
    value: 'PCS',
  },
  {
    label: 'liter',
    value: 'LITER',
  },
  {
    label: 'mg',
    value: 'MILLIGRAM',
  },
  {
    label: 'kg',
    value: 'KILOGRAM',
  },
];
