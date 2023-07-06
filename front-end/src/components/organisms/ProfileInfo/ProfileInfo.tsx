import React from 'react';
import Box from '../../atoms/Box/Box';
import Avatar from '../../molecules/Avatar/Avatar';
import { ProfileInfoProps } from './ProfileInfo.type';
import ProfileInfoItem from '../../molecules/ProfileInfoItem/ProfileInfoItem';
import Text from '../../atoms/Text/Text';
import backgroundImg from '../../../app/assets/images/auth-bg.jpeg';

function ProfileInfo({
  firstName,
  lastName,
  userPhoto,
  recipesCount,
}: ProfileInfoProps) {
  return (
    <Box
      padding="20px"
      height="270px"
      flex
      gap="30px"
      align="center"
      style={{
        background: `url(${backgroundImg})`,
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        borderBottomRightRadius: '10px',
        borderBottomLeftRadius: '10px',
      }}
    >
      <Avatar width="200px" height="200px" src={userPhoto} />
      <Box
        width="400px"
        height="180px"
        radius="10px"
        padding="17px 25px"
        elevation={2}
      >
        <Text color="secondary" size={36} weight="bold">
          {`${firstName} ${lastName}`}
        </Text>
        <ProfileInfoItem recipesCount={recipesCount} />
      </Box>
    </Box>
  );
}

export default ProfileInfo;
