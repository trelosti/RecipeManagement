import React from 'react';
import { useNavigate } from 'react-router-dom';
import Text from '../../atoms/Text/Text';
import Box from '../../atoms/Box/Box';
import Avatar from '../../molecules/Avatar/Avatar';
import Image from '../../atoms/Image/Image';
import logo from '../../../app/assets/icons/logo.png';
import addRecipe from '../../../app/assets/icons/addRecipe.png';
import { NavbarProps } from './Navbar.type';
import { ROUTES } from '../../../router';

function Navbar({ userPhoto }: NavbarProps) {
  const navigate = useNavigate();
  const handleAddRecipe = () => {
    navigate(ROUTES.CREATE_RECIPE);
  };
  const handleToProfile = () => {
    navigate(ROUTES.PROFILE);
  };

  return (
    <Box
      height="80px"
      flex
      align="center"
      justify={userPhoto ? 'space-between' : 'space-around'}
      padding="0 75px"
      elevation={2}
      style={{ position: 'relative' }}
    >
      <Box flex align="center" justify="center">
        <Box onClick={handleToProfile} style={{ cursor: 'pointer' }}>
          <Image src={logo} alt="logo" />
        </Box>
        <Text color="secondary" size={20} weight="bold" marginLeft="7px">
          Scratch
        </Text>
      </Box>
      {userPhoto && (
        <Box flex align="center">
          <Box marginRight="25px" onClick={handleAddRecipe}>
            <Image
              src={addRecipe}
              alt="addRecipe"
              style={{ width: '32px', cursor: 'pointer' }}
            />
          </Box>
          <Avatar width="65px" height="65px" src={userPhoto} />
        </Box>
      )}
    </Box>
  );
}

export default Navbar;
