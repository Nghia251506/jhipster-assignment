import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './source.reducer';

export const SourceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sourceEntity = useAppSelector(state => state.source.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sourceDetailsHeading">
          <Translate contentKey="seocrawlerApp.source.detail.title">Source</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="seocrawlerApp.source.name">Name</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.name}</dd>
          <dt>
            <span id="baseUrl">
              <Translate contentKey="seocrawlerApp.source.baseUrl">Base Url</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.baseUrl}</dd>
          <dt>
            <span id="listUrl">
              <Translate contentKey="seocrawlerApp.source.listUrl">List Url</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.listUrl}</dd>
          <dt>
            <span id="listItemSelector">
              <Translate contentKey="seocrawlerApp.source.listItemSelector">List Item Selector</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.listItemSelector}</dd>
          <dt>
            <span id="linkAttr">
              <Translate contentKey="seocrawlerApp.source.linkAttr">Link Attr</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.linkAttr}</dd>
          <dt>
            <span id="titleSelector">
              <Translate contentKey="seocrawlerApp.source.titleSelector">Title Selector</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.titleSelector}</dd>
          <dt>
            <span id="contentSelector">
              <Translate contentKey="seocrawlerApp.source.contentSelector">Content Selector</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.contentSelector}</dd>
          <dt>
            <span id="thumbnailSelector">
              <Translate contentKey="seocrawlerApp.source.thumbnailSelector">Thumbnail Selector</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.thumbnailSelector}</dd>
          <dt>
            <span id="authorSelector">
              <Translate contentKey="seocrawlerApp.source.authorSelector">Author Selector</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.authorSelector}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="seocrawlerApp.source.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="seocrawlerApp.source.note">Note</Translate>
            </span>
          </dt>
          <dd>{sourceEntity.note}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.source.tenant">Tenant</Translate>
          </dt>
          <dd>{sourceEntity.tenant ? sourceEntity.tenant.code : ''}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.source.category">Category</Translate>
          </dt>
          <dd>{sourceEntity.category ? sourceEntity.category.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/source" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/source/${sourceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SourceDetail;
