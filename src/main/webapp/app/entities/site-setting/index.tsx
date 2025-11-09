import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SiteSetting from './site-setting';
import SiteSettingDetail from './site-setting-detail';
import SiteSettingUpdate from './site-setting-update';
import SiteSettingDeleteDialog from './site-setting-delete-dialog';

const SiteSettingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SiteSetting />} />
    <Route path="new" element={<SiteSettingUpdate />} />
    <Route path=":id">
      <Route index element={<SiteSettingDetail />} />
      <Route path="edit" element={<SiteSettingUpdate />} />
      <Route path="delete" element={<SiteSettingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SiteSettingRoutes;
