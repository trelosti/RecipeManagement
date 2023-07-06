import React from 'react';
import styles from './ModalPhoto.module.scss';
import Button from '../../atoms/Button/Button';
import { ModalPhotoProps } from './ModalPhoto.types';
import Image from '../../atoms/Image/Image';
import Box from '../../atoms/Box/Box';

function ModalPhoto({ isShow, onClick, recipePhoto }: ModalPhotoProps) {
  if (!isShow) {
    return null;
  }

  return (
    <Box flex align="center" justify="center" className={styles.modal}>
      <Box className={styles.content}>
        <Image src={recipePhoto} alt={recipePhoto} />
        <Button
          onClick={onClick}
          variant="outline"
          className={styles.closeButton}
        >
          Close
        </Button>
      </Box>
    </Box>
  );
}

export default ModalPhoto;
