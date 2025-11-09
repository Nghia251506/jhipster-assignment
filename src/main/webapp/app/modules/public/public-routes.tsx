import React from 'react';
import { Route } from 'react-router-dom';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import PublicHome from './public-home';
import PublicPostDetail from './public-post-detail';

const PublicRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PublicHome />} />
    <Route path="post/:slug" element={<PublicPostDetail />} />
  </ErrorBoundaryRoutes>
);

export default PublicRoutes;
