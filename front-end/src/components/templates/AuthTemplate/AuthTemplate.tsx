import React from 'react';
import Box from '../../atoms/Box/Box';
import Image from '../../atoms/Image/Image';
import Navbar from '../../organisms/Navbar/Navbar';

type AuthTemplateProps = {
  headerImage: string;
  children: React.ReactNode;
};

function AuthTemplate({ headerImage, children }: AuthTemplateProps) {
  return (
    <>
      <Navbar />
      <Box width="544px" marginX="auto" radius="15px">
        <Image src={headerImage} />

        {children}
      </Box>
    </>
  );
}

export default AuthTemplate;
