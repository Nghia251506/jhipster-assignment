import React from 'react';
import { Route } from 'react-router'; // eslint-disable-line

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tenant from './tenant';
import AppUser from './app-user';
import Category from './category';
import Source from './source';
import Post from './post';
import Tag from './tag';
import SiteSetting from './site-setting';
import CrawlLog from './crawl-log';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="tenant/*" element={<Tenant />} />
        <Route path="app-user/*" element={<AppUser />} />
        <Route path="category/*" element={<Category />} />
        <Route path="source/*" element={<Source />} />
        <Route path="post/*" element={<Post />} />
        <Route path="tag/*" element={<Tag />} />
        <Route path="site-setting/*" element={<SiteSetting />} />
        <Route path="crawl-log/*" element={<CrawlLog />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
