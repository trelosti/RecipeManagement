import React from 'react';
import { useNavigate } from 'react-router-dom';
import Box from '../../atoms/Box/Box';
import Image from '../../atoms/Image/Image';
import { AvatarProps } from './Avatar.types';
import { PROFILE_API_URL } from '../../../app/api/apiRoutes';

function Avatar({ width, height, src }: AvatarProps) {
  const navigate = useNavigate();
  const handleClick = () => navigate(PROFILE_API_URL);

  return (
    <button type="button" onClick={handleClick}>
      <Box
        radius="50%"
        width={width}
        height={height}
        style={{ cursor: 'pointer' }}
      >
        <Image radius="50%" src={src} alt="avatar" />
      </Box>
    </button>
  );
}

export default Avatar;
