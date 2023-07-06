export enum MeasureUnits {
  MILLILITER = 'MILLILITER',
  GRAM = 'GRAM',
  PCS = 'PCS',
  LITER = 'LITER',
  MILLIGRAM = 'MILLIGRAM',
  KILOGRAM = 'KILOGRAM',
}

type Ingredient = {
  id: number;
  ingredientName: string;
  quantity: number;
  measureUnit: MeasureUnits;
};

export default Ingredient;
