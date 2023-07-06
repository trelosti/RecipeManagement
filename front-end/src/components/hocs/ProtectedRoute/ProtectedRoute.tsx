import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

type ProtectedRouteProps = {
  isAllowed: boolean;
  redirectTo: string;
};

function ProtectedRoutes({ isAllowed, redirectTo }: ProtectedRouteProps) {
  if (!isAllowed) {
    return <Navigate to={redirectTo} replace />;
  }

  return <Outlet />;
}

export default ProtectedRoutes;
