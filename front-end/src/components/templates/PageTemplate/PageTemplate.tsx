import React from 'react';
import avatar from '../../../app/assets/images/avatar.jpg';
import Container from '../../molecules/Container/Container';
import Navbar from '../../organisms/Navbar/Navbar';
import { PageTemplateProps } from './PageTemplate.types';

function PageTemplate({ background, children }: PageTemplateProps) {
  return (
    <>
      <Navbar userPhoto={avatar} />
      <Container background={background}>{children}</Container>
    </>
  );
}

export default PageTemplate;
