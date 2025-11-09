import React from 'react';
import { Translate } from 'react-jhipster'; // eslint-disable-line

import MenuItem from 'app/shared/layout/menus/menu-item'; // eslint-disable-line

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/tenant">
        <Translate contentKey="global.menu.entities.tenant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-user">
        <Translate contentKey="global.menu.entities.appUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/category">
        <Translate contentKey="global.menu.entities.category" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/source">
        <Translate contentKey="global.menu.entities.source" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/post">
        <Translate contentKey="global.menu.entities.post" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tag">
        <Translate contentKey="global.menu.entities.tag" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/site-setting">
        <Translate contentKey="global.menu.entities.siteSetting" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crawl-log">
        <Translate contentKey="global.menu.entities.crawlLog" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
