import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post.reducer';

export const PostDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const postEntity = useAppSelector(state => state.post.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postDetailsHeading">
          <Translate contentKey="seocrawlerApp.post.detail.title">Post</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{postEntity.id}</dd>
          <dt>
            <span id="originUrl">
              <Translate contentKey="seocrawlerApp.post.originUrl">Origin Url</Translate>
            </span>
          </dt>
          <dd>{postEntity.originUrl}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="seocrawlerApp.post.title">Title</Translate>
            </span>
          </dt>
          <dd>{postEntity.title}</dd>
          <dt>
            <span id="slug">
              <Translate contentKey="seocrawlerApp.post.slug">Slug</Translate>
            </span>
          </dt>
          <dd>{postEntity.slug}</dd>
          <dt>
            <span id="summary">
              <Translate contentKey="seocrawlerApp.post.summary">Summary</Translate>
            </span>
          </dt>
          <dd>{postEntity.summary}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="seocrawlerApp.post.content">Content</Translate>
            </span>
          </dt>
          <dd>{postEntity.content}</dd>
          <dt>
            <span id="contentRaw">
              <Translate contentKey="seocrawlerApp.post.contentRaw">Content Raw</Translate>
            </span>
          </dt>
          <dd>{postEntity.contentRaw}</dd>
          <dt>
            <span id="thumbnail">
              <Translate contentKey="seocrawlerApp.post.thumbnail">Thumbnail</Translate>
            </span>
          </dt>
          <dd>{postEntity.thumbnail}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="seocrawlerApp.post.status">Status</Translate>
            </span>
          </dt>
          <dd>{postEntity.status}</dd>
          <dt>
            <span id="publishedAt">
              <Translate contentKey="seocrawlerApp.post.publishedAt">Published At</Translate>
            </span>
          </dt>
          <dd>{postEntity.publishedAt ? <TextFormat value={postEntity.publishedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="viewCount">
              <Translate contentKey="seocrawlerApp.post.viewCount">View Count</Translate>
            </span>
          </dt>
          <dd>{postEntity.viewCount}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.post.tenant">Tenant</Translate>
          </dt>
          <dd>{postEntity.tenant ? postEntity.tenant.code : ''}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.post.source">Source</Translate>
          </dt>
          <dd>{postEntity.source ? postEntity.source.name : ''}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.post.category">Category</Translate>
          </dt>
          <dd>{postEntity.category ? postEntity.category.name : ''}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.post.tags">Tags</Translate>
          </dt>
          <dd>
            {postEntity.tags
              ? postEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {postEntity.tags && i === postEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostDetail;
