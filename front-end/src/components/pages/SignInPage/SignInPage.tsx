import React from 'react';
import { SubmitHandler } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import AuthTemplate from '../../templates/AuthTemplate/AuthTemplate';
import authBg from '../../../app/assets/images/auth-bg.jpeg';
import SignInForm from '../../organisms/SignInForm/SignInForm';
import { SignInFormData } from '../../organisms/SignInForm/SignInForm.types';
import { useSignInMutation } from '../../../app/api/apiSlice';
import { ROUTES } from '../../../router';
import getErrorMsg from '../../../app/utils/getErrorMsg';

function SignInPage() {
  const navigate = useNavigate();
  const [signIn, { isSuccess, error }] = useSignInMutation();

  const errorMsg = getErrorMsg(error);

  const onSignIn: SubmitHandler<SignInFormData> = async (data) => {
    await signIn(data);

    if (isSuccess) {
      navigate(ROUTES.PROFILE);
    }
  };

  return (
    <AuthTemplate headerImage={authBg}>
      <SignInForm onSignIn={onSignIn} error={errorMsg} />
    </AuthTemplate>
  );
}

export default SignInPage;
