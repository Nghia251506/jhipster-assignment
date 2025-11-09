import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './site-setting.reducer';

export const SiteSettingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const siteSettingEntity = useAppSelector(state => state.siteSetting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="siteSettingDetailsHeading">
          <Translate contentKey="seocrawlerApp.siteSetting.detail.title">SiteSetting</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{siteSettingEntity.id}</dd>
          <dt>
            <span id="siteTitle">
              <Translate contentKey="seocrawlerApp.siteSetting.siteTitle">Site Title</Translate>
            </span>
          </dt>
          <dd>{siteSettingEntity.siteTitle}</dd>
          <dt>
            <span id="siteDescription">
              <Translate contentKey="seocrawlerApp.siteSetting.siteDescription">Site Description</Translate>
            </span>
          </dt>
          <dd>{siteSettingEntity.siteDescription}</dd>
          <dt>
            <span id="siteKeywords">
              <Translate contentKey="seocrawlerApp.siteSetting.siteKeywords">Site Keywords</Translate>
            </span>
          </dt>
          <dd>{siteSettingEntity.siteKeywords}</dd>
          <dt>
            <span id="gaTrackingId">
              <Translate contentKey="seocrawlerApp.siteSetting.gaTrackingId">Ga Tracking Id</Translate>
            </span>
          </dt>
          <dd>{siteSettingEntity.gaTrackingId}</dd>
          <dt>
            <span id="bannerTop">
              <Translate contentKey="seocrawlerApp.siteSetting.bannerTop">Banner Top</Translate>
            </span>
          </dt>
          <dd>{siteSettingEntity.bannerTop}</dd>
          <dt>
            <span id="bannerSidebar">
              <Translate contentKey="seocrawlerApp.siteSetting.bannerSidebar">Banner Sidebar</Translate>
            </span>
          </dt>
          <dd>{siteSettingEntity.bannerSidebar}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.siteSetting.tenant">Tenant</Translate>
          </dt>
          <dd>{siteSettingEntity.tenant ? siteSettingEntity.tenant.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/site-setting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/site-setting/${siteSettingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SiteSettingDetail;
