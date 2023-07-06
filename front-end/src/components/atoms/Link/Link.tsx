import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { LinkProps } from './Link.types';
import AtomProps from '../../../app/types/AtomProps';
import getAtomProps from '../../../app/utils/getAtomProps';
import cn from '../../../app/utils/cn';
import styles from './Link.module.scss';

function Link({ to, replace, children, ...atomProps }: LinkProps & AtomProps) {
  const { className, style } = getAtomProps(atomProps);

  return (
    <RouterLink
      className={cn(styles.link, className)}
      to={to}
      replace={replace}
      style={style}
    >
      {children}
    </RouterLink>
  );
}

export default Link;
