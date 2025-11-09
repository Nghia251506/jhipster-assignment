import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './post.reducer';

export const Post = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const postList = useAppSelector(state => state.post.entities);
  const loading = useAppSelector(state => state.post.loading);
  const totalItems = useAppSelector(state => state.post.totalItems);

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
      <h2 id="post-heading" data-cy="PostHeading">
        <Translate contentKey="seocrawlerApp.post.home.title">Posts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seocrawlerApp.post.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/post/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seocrawlerApp.post.home.createLabel">Create new Post</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {postList && postList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="seocrawlerApp.post.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('originUrl')}>
                  <Translate contentKey="seocrawlerApp.post.originUrl">Origin Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('originUrl')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="seocrawlerApp.post.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('slug')}>
                  <Translate contentKey="seocrawlerApp.post.slug">Slug</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('slug')} />
                </th>
                <th className="hand" onClick={sort('summary')}>
                  <Translate contentKey="seocrawlerApp.post.summary">Summary</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('summary')} />
                </th>
                <th className="hand" onClick={sort('content')}>
                  <Translate contentKey="seocrawlerApp.post.content">Content</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('content')} />
                </th>
                <th className="hand" onClick={sort('contentRaw')}>
                  <Translate contentKey="seocrawlerApp.post.contentRaw">Content Raw</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contentRaw')} />
                </th>
                <th className="hand" onClick={sort('thumbnail')}>
                  <Translate contentKey="seocrawlerApp.post.thumbnail">Thumbnail</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('thumbnail')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="seocrawlerApp.post.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('publishedAt')}>
                  <Translate contentKey="seocrawlerApp.post.publishedAt">Published At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('publishedAt')} />
                </th>
                <th className="hand" onClick={sort('viewCount')}>
                  <Translate contentKey="seocrawlerApp.post.viewCount">View Count</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('viewCount')} />
                </th>
                <th>
                  <Translate contentKey="seocrawlerApp.post.tenant">Tenant</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="seocrawlerApp.post.source">Source</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="seocrawlerApp.post.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {postList.map((post, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/post/${post.id}`} color="link" size="sm">
                      {post.id}
                    </Button>
                  </td>
                  <td>{post.originUrl}</td>
                  <td>{post.title}</td>
                  <td>{post.slug}</td>
                  <td>{post.summary}</td>
                  <td>{post.content}</td>
                  <td>{post.contentRaw}</td>
                  <td>{post.thumbnail}</td>
                  <td>
                    <Translate contentKey={`seocrawlerApp.PostStatus.${post.status}`} />
                  </td>
                  <td>{post.publishedAt ? <TextFormat type="date" value={post.publishedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{post.viewCount}</td>
                  <td>{post.tenant ? <Link to={`/tenant/${post.tenant.id}`}>{post.tenant.code}</Link> : ''}</td>
                  <td>{post.source ? <Link to={`/source/${post.source.id}`}>{post.source.name}</Link> : ''}</td>
                  <td>{post.category ? <Link to={`/category/${post.category.id}`}>{post.category.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/post/${post.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/post/${post.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/post/${post.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="seocrawlerApp.post.home.notFound">No Posts found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={postList && postList.length > 0 ? '' : 'd-none'}>
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

export default Post;
