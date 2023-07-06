import { SubmitHandler } from 'react-hook-form';

export type SignUpFormData = {
  firstName: string;
  lastName: string;
  login: string;
  password: string;
};

export type SignUpFormProps = {
  onSignUp: SubmitHandler<SignUpFormData>;
  error?: string;
};
