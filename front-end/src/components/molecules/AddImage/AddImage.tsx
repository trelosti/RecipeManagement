import React, { useEffect, useState } from 'react';
import Box from '../../atoms/Box/Box';
import { AddImageProps } from './AddImage.types';
import addImage from '../../../app/assets/icons/addPhoto.png';

const AddImage = React.forwardRef<HTMLInputElement, AddImageProps>(
  ({ name, defaultImage, onChange, width, height }, ref) => {
    const [preview, setPreview] = useState<string>(defaultImage || addImage);

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      const selectedImage = e.target.files && e.target.files[0];

      if (selectedImage) {
        const objectUrl = URL.createObjectURL(selectedImage);
        setPreview(objectUrl);
      }

      onChange(e);
    };

    useEffect(() => {
      if (defaultImage) {
        setPreview(defaultImage);
      }
    }, [defaultImage]);

    return (
      <Box
        height={height}
        width={width}
        style={{ position: 'relative', overflow: 'hidden' }}
      >
        {/* eslint-disable-next-line jsx-a11y/label-has-associated-control */}
        <label
          htmlFor={`image-input-${name}`}
          style={{
            display: 'block',
            height: '100%',
            position: 'relative',
            zIndex: '1',
            cursor: 'pointer',
            backgroundImage: `url(${preview})`,
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'center',
            backgroundSize: 'contain',
            borderRadius: '10px',
          }}
        />
        <input
          ref={ref}
          name={name}
          id={`image-input-${name}`}
          type="file"
          accept="image/*"
          onChange={handleImageChange}
          style={{ display: 'none' }}
        />
      </Box>
    );
  }
);

export default AddImage;
