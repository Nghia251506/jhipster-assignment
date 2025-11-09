import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './site-setting.reducer';

export const SiteSetting = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const siteSettingList = useAppSelector(state => state.siteSetting.entities);
  const loading = useAppSelector(state => state.siteSetting.loading);
  const totalItems = useAppSelector(state => state.siteSetting.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="site-setting-heading" data-cy="SiteSettingHeading">
        <Translate contentKey="seocrawlerApp.siteSetting.home.title">Site Settings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seocrawlerApp.siteSetting.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/site-setting/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seocrawlerApp.siteSetting.home.createLabel">Create new Site Setting</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {siteSettingList && siteSettingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="seocrawlerApp.siteSetting.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('siteTitle')}>
                  <Translate contentKey="seocrawlerApp.siteSetting.siteTitle">Site Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('siteTitle')} />
                </th>
                <th className="hand" onClick={sort('siteDescription')}>
                  <Translate contentKey="seocrawlerApp.siteSetting.siteDescription">Site Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('siteDescription')} />
                </th>
                <th className="hand" onClick={sort('siteKeywords')}>
                  <Translate contentKey="seocrawlerApp.siteSetting.siteKeywords">Site Keywords</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('siteKeywords')} />
                </th>
                <th className="hand" onClick={sort('gaTrackingId')}>
                  <Translate contentKey="seocrawlerApp.siteSetting.gaTrackingId">Ga Tracking Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('gaTrackingId')} />
                </th>
                <th className="hand" onClick={sort('bannerTop')}>
                  <Translate contentKey="seocrawlerApp.siteSetting.bannerTop">Banner Top</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bannerTop')} />
                </th>
                <th className="hand" onClick={sort('bannerSidebar')}>
                  <Translate contentKey="seocrawlerApp.siteSetting.bannerSidebar">Banner Sidebar</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bannerSidebar')} />
                </th>
                <th>
                  <Translate contentKey="seocrawlerApp.siteSetting.tenant">Tenant</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {siteSettingList.map((siteSetting, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/site-setting/${siteSetting.id}`} color="link" size="sm">
                      {siteSetting.id}
                    </Button>
                  </td>
                  <td>{siteSetting.siteTitle}</td>
                  <td>{siteSetting.siteDescription}</td>
                  <td>{siteSetting.siteKeywords}</td>
                  <td>{siteSetting.gaTrackingId}</td>
                  <td>{siteSetting.bannerTop}</td>
                  <td>{siteSetting.bannerSidebar}</td>
                  <td>{siteSetting.tenant ? <Link to={`/tenant/${siteSetting.tenant.id}`}>{siteSetting.tenant.code}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/site-setting/${siteSetting.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/site-setting/${siteSetting.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/site-setting/${siteSetting.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="seocrawlerApp.siteSetting.home.notFound">No Site Settings found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={siteSettingList && siteSettingList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default SiteSetting;
