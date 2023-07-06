import React, { useEffect } from 'react';
import { Provider } from 'react-redux';
import { RouterProvider } from 'react-router-dom';
import { store } from './app/store/store';
import router from './router';
import { useAppDispatch, useAppSelector } from './app/store/hooks';
import { getIsAuth, setIsAuth } from './app/store/appSlice';

function App() {
  const dispatch = useAppDispatch();
  const isAuth = useAppSelector(getIsAuth);

  useEffect(() => {
    dispatch(setIsAuth(!!localStorage.getItem('isAuth')));
  }, []);

  if (isAuth === undefined) {
    return null;
  }

  return (
    <Provider store={store}>
      <RouterProvider router={router(isAuth)} />
    </Provider>
  );
}

export default App;
