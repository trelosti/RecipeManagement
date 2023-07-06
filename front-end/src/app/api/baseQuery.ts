import {
  BaseQueryFn,
  FetchArgs,
  fetchBaseQuery,
  FetchBaseQueryError,
} from '@reduxjs/toolkit/dist/query/react';
import { AUTH_API_URL } from './apiRoutes';
import { STATUS_CODES } from './apiConsts';

// const apiUrl = process.env.REACT_APP_API_URL;
const apiUrl = 'http://localhost:8080';

const baseQuery = fetchBaseQuery({
  baseUrl: `${apiUrl}/api/v1`,
  credentials: 'include',
});
const baseQueryWithReauth: BaseQueryFn<
  string | FetchArgs,
  unknown,
  FetchBaseQueryError
> = async (args, api, extraOptions) => {
  let result = await baseQuery(args, api, extraOptions);
  if (result.error && result.error.status === STATUS_CODES.UNAUTHORIZED) {
    const refreshResult = await baseQuery(
      { url: `${AUTH_API_URL}/refresh`, method: 'POST' },
      api,
      extraOptions
    );
    if (!refreshResult.error) {
      localStorage.setItem('isAuth', 'true');
      result = await baseQuery(args, api, extraOptions);
    } else {
      localStorage.setItem('isAuth', '');
    }
  }
  return result;
};

export default baseQueryWithReauth;
