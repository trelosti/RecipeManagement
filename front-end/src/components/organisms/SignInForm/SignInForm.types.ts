import { SubmitHandler } from 'react-hook-form';

export type SignInFormData = {
  login: string;
  password: string;
};

export type SignInProps = {
  onSignIn: SubmitHandler<SignInFormData>;
  error?: string;
};
