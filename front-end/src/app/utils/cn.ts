const cn = (...classNames: (string | undefined | null)[]): string =>
  classNames.filter(Boolean).join(' ');

export default cn;
