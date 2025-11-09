import tenant from 'app/entities/tenant/tenant.reducer';
import appUser from 'app/entities/app-user/app-user.reducer';
import category from 'app/entities/category/category.reducer';
import source from 'app/entities/source/source.reducer';
import post from 'app/entities/post/post.reducer';
import tag from 'app/entities/tag/tag.reducer';
import siteSetting from 'app/entities/site-setting/site-setting.reducer';
import crawlLog from 'app/entities/crawl-log/crawl-log.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  tenant,
  appUser,
  category,
  source,
  post,
  tag,
  siteSetting,
  crawlLog,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
