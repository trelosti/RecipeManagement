import React from 'react';
import { render } from '@testing-library/react';
import Text from './Text';

describe('Text', () => {
  it('renders the text content', () => {
    const textContent = 'Hello, World!';
    const { getByText } = render(<Text>{textContent}</Text>);
    expect(getByText(textContent)).toBeInTheDocument();
  });

  it('applies the correct font size', () => {
    const fontSize = 18;
    const { getByRole } = render(<Text size={fontSize}>Test Text</Text>);
    expect(getByRole('span')).toHaveStyle(`font-size: ${fontSize}px`);
  });

  it('applies the correct text color', () => {
    const textColor = 'secondary';
    const { getByRole } = render(<Text color={textColor}>Test Text</Text>);
    expect(getByRole('span')).toHaveClass(`text ${textColor}`);
  });

  it('applies the correct text weight', () => {
    const fontWeight = 'bold';
    const { getByRole } = render(<Text weight={fontWeight}>Test Text</Text>);
    expect(getByRole('span')).toHaveClass(`text ${fontWeight}`);
  });

  it('applies the correct text alignment', () => {
    const textAlign = 'center';
    const { getByRole } = render(<Text align={textAlign}>Test Text</Text>);
    expect(getByRole('span')).toHaveStyle(`text-align: ${textAlign}`);
  });

  it('applies uppercase text', () => {
    const { getByRole } = render(<Text uppercase>Test Text</Text>);
    expect(getByRole('span')).toHaveStyle(`text-transform: uppercase`);
  });
});
