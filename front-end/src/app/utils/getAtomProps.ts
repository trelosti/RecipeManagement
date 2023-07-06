import React from 'react';
import AtomProps from '../types/AtomProps';

type GetAtomProps = {
  className?: string;
  style: React.CSSProperties;
};

const getAtomProps = ({
  className,
  margin,
  marginLeft,
  marginRight,
  marginX,
  marginY,
  marginBottom,
  marginTop,
  paddingBottom,
  padding,
  paddingX,
  paddingLeft,
  paddingY,
  paddingRight,
  paddingTop,
  style,
}: AtomProps) => {
  const result: GetAtomProps = {
    className,
    style: {
      ...style,
    },
  };

  if (margin) {
    result.style.margin = margin;
  }
  if (margin && marginX) {
    result.style.margin = `${margin} ${marginX}`;
  }
  if (margin && marginY) {
    result.style.margin = `${marginY} ${margin}`;
  }
  if (marginX && marginY) {
    result.style.margin = `${marginY} ${marginX}`;
  }
  if (!margin && !marginY && marginX) {
    result.style.margin = `0 ${marginX}`;
  }
  if (!margin && !marginX && marginY) {
    result.style.margin = `${marginY} 0`;
  }
  if (marginLeft) {
    result.style.marginLeft = marginLeft;
  }
  if (marginRight) {
    result.style.marginRight = marginRight;
  }
  if (marginTop) {
    result.style.marginTop = marginTop;
  }
  if (marginBottom) {
    result.style.marginBottom = marginBottom;
  }
  if (padding) {
    result.style.padding = padding;
  }
  if (padding && paddingX) {
    result.style.padding = `${padding} ${paddingX}`;
  }
  if (padding && paddingY) {
    result.style.padding = `${paddingY} ${padding}`;
  }
  if (paddingX && paddingY) {
    result.style.padding = `${paddingY} ${paddingX}`;
  }
  if (paddingLeft) {
    result.style.paddingLeft = paddingLeft;
  }
  if (paddingRight) {
    result.style.paddingRight = paddingRight;
  }
  if (paddingTop) {
    result.style.paddingTop = paddingTop;
  }
  if (paddingBottom) {
    result.style.paddingBottom = paddingBottom;
  }

  return result;
};

export default getAtomProps;
