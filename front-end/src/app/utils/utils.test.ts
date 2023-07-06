import cn from './cn';
import getAtomProps from './getAtomProps';
import tgl from './tgl';
import AtomProps from '../types/AtomProps';

describe('cn', () => {
  it('returns a string of concatenated class names', () => {
    const result = cn('foo', 'bar', 'baz');
    expect(result).toBe('foo bar baz');
  });

  it('filters out falsy class names', () => {
    const result = cn('foo', undefined, null, 'bar', '', 'baz');
    expect(result).toBe('foo bar baz');
  });
});

describe('getAtomProps', () => {
  const atomProps: AtomProps = {
    className: 'my-class',
    margin: '1rem',
    marginLeft: '0.5rem',
    marginRight: '0.5rem',
    marginTop: '0.5rem',
    marginBottom: '0.5rem',
    padding: '1rem',
    paddingLeft: '0.5rem',
    paddingRight: '0.5rem',
    paddingTop: '0.5rem',
    paddingBottom: '0.5rem',
    style: {
      backgroundColor: 'red',
      color: 'white',
    },
  };

  it('returns an object with className and style properties', () => {
    const result = getAtomProps(atomProps);
    expect(result).toHaveProperty('className');
    expect(result).toHaveProperty('style');
  });

  it('sets the className property based on the className prop', () => {
    const result = getAtomProps(atomProps);
    expect(result.className).toBe('my-class');
  });

  it('sets the margin property based on the margin prop', () => {
    const result = getAtomProps(atomProps);
    expect(result.style.margin).toBe('1rem');
  });

  it('sets the marginLeft property based on the marginLeft prop', () => {
    const result = getAtomProps(atomProps);
    expect(result.style.marginLeft).toBe('0.5rem');
  });

  it('sets the marginRight property based on the marginRight prop', () => {
    const result = getAtomProps(atomProps);
    expect(result.style.marginRight).toBe('0.5rem');
  });

  it('sets the marginTop property based on the marginTop prop', () => {
    const result = getAtomProps(atomProps);
    expect(result.style.marginTop).toBe('0.5rem');
  });

  it('sets the marginBottom property based on the marginBottom prop', () => {
    const result = getAtomProps(atomProps);
    expect(result.style.marginBottom).toBe('0.5rem');
  });

  it('sets the padding property based on the padding prop', () => {
    const result = getAtomProps(atomProps);
    expect(result.style.padding).toBe('1rem');
  });
});

describe('tgl', () => {
  it('returns option1 when condition is true', () => {
    const condition = true;
    const option1 = 'Hello';
    const option2 = 'World';
    const result = tgl(condition, option1, option2);
    expect(result).toBe(option1);
  });

  it('returns option2 when condition is false', () => {
    const condition = false;
    const option1 = 'Hello';
    const option2 = 'World';
    const result = tgl(condition, option1, option2);
    expect(result).toBe(option2);
  });

  it('returns an empty string when condition is false and option2 is not provided', () => {
    const condition = false;
    const option1 = 'Hello';
    const result = tgl(condition, option1);
    expect(result).toBe('');
  });
});
