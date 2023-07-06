import React from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import Text from '../../atoms/Text/Text';
import Box from '../../atoms/Box/Box';
import Button from '../../atoms/Button/Button';
import {
  AUTH_FORM_TITLE,
  SIGN_IN_BUTTON,
  SIGN_UP_BUTTON,
} from './SignUpForm.const';
import { SignUpFormData, SignUpFormProps } from './SignUpForm.types';
import { ROUTES } from '../../../router';
import { PASSWORD_REGEX } from '../../../app/consts/regex';
import {
  PASSWORD_PATTERN_ERR,
  REQUIRED_FIELD_ERR,
} from '../../../app/consts/errorMessages';
import TextField from '../../molecules/TextField/TextField';

function SignUpForm({ onSignUp, error }: SignUpFormProps) {
  const navigate = useNavigate();
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<SignUpFormData>();

  const onSignIn = () => {
    navigate(ROUTES.SIGN_IN);
  };

  return (
    <Box background="none" padding="25px 100px">
      <Text color="secondary" size={36} marginBottom="34px" align="center">
        {AUTH_FORM_TITLE}
      </Text>

      <Box
        background="none"
        flex
        gap="10px"
        justify="space-between"
        align="flex-start"
        marginBottom="16px"
      >
        <Controller
          name="firstName"
          control={control}
          rules={{
            required: true,
          }}
          render={({ field }) => (
            <TextField
              {...field}
              placeholder="First Name"
              error={errors.firstName && REQUIRED_FIELD_ERR}
            />
          )}
        />
        <Controller
          name="lastName"
          control={control}
          rules={{
            required: true,
          }}
          render={({ field }) => (
            <TextField
              {...field}
              placeholder="Last Name"
              error={errors.lastName && REQUIRED_FIELD_ERR}
            />
          )}
        />
      </Box>
      <Box marginBottom="16px">
        <Controller
          name="login"
          control={control}
          rules={{
            required: true,
          }}
          render={({ field }) => (
            <TextField
              {...field}
              placeholder="Login"
              error={errors.login && REQUIRED_FIELD_ERR}
            />
          )}
        />
      </Box>
      <Box marginBottom="16px">
        <Controller
          name="password"
          control={control}
          rules={{
            required: true,
            pattern: PASSWORD_REGEX,
          }}
          render={({ field }) => (
            <TextField
              {...field}
              type="password"
              placeholder="Password"
              error={errors.password && PASSWORD_PATTERN_ERR}
            />
          )}
        />
      </Box>

      {error && (
        <Text color="primary" align="center" marginBottom="16px">
          {error}
        </Text>
      )}

      <Button
        onClick={handleSubmit(onSignUp)}
        style={{ width: '100%' }}
        marginBottom="16px"
      >
        {SIGN_UP_BUTTON}
      </Button>
      <Button
        onClick={onSignIn}
        variant="outline"
        style={{ width: '100%' }}
        marginBottom="20px"
      >
        {SIGN_IN_BUTTON}
      </Button>
    </Box>
  );
}

export default SignUpForm;
