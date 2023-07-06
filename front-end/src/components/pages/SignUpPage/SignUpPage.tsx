import React from 'react';
import { SubmitHandler } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import AuthTemplate from '../../templates/AuthTemplate/AuthTemplate';
import authBg from '../../../app/assets/images/auth-bg.jpeg';
import SignUpForm from '../../organisms/SignUpForm/SignUpForm';
import { SignUpFormData } from '../../organisms/SignUpForm/SignUpForm.types';
import { useSignUpMutation } from '../../../app/api/apiSlice';
import { ROUTES } from '../../../router';
import getErrorMsg from '../../../app/utils/getErrorMsg';

function SignUpPage() {
  const navigate = useNavigate();
  const [signUp, { isSuccess, error }] = useSignUpMutation();

  const errorMsg = getErrorMsg(error);

  const onSignUp: SubmitHandler<SignUpFormData> = async (data) => {
    await signUp(data);

    if (isSuccess) {
      navigate(ROUTES.PROFILE);
    }
  };

  return (
    <AuthTemplate headerImage={authBg}>
      <SignUpForm onSignUp={onSignUp} error={errorMsg} />
    </AuthTemplate>
  );
}

export default SignUpPage;
