const tgl = (condition: boolean, option1: any, option2?: any) =>
  condition ? option1 : option2 || '';

export default tgl;
