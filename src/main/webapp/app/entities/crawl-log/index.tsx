import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CrawlLog from './crawl-log';
import CrawlLogDetail from './crawl-log-detail';
import CrawlLogUpdate from './crawl-log-update';
import CrawlLogDeleteDialog from './crawl-log-delete-dialog';

const CrawlLogRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CrawlLog />} />
    <Route path="new" element={<CrawlLogUpdate />} />
    <Route path=":id">
      <Route index element={<CrawlLogDetail />} />
      <Route path="edit" element={<CrawlLogUpdate />} />
      <Route path="delete" element={<CrawlLogDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CrawlLogRoutes;
