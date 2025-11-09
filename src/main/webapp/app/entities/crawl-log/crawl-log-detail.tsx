import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './crawl-log.reducer';

export const CrawlLogDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const crawlLogEntity = useAppSelector(state => state.crawlLog.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="crawlLogDetailsHeading">
          <Translate contentKey="seocrawlerApp.crawlLog.detail.title">CrawlLog</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{crawlLogEntity.id}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="seocrawlerApp.crawlLog.url">Url</Translate>
            </span>
          </dt>
          <dd>{crawlLogEntity.url}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="seocrawlerApp.crawlLog.status">Status</Translate>
            </span>
          </dt>
          <dd>{crawlLogEntity.status}</dd>
          <dt>
            <span id="errorMessage">
              <Translate contentKey="seocrawlerApp.crawlLog.errorMessage">Error Message</Translate>
            </span>
          </dt>
          <dd>{crawlLogEntity.errorMessage}</dd>
          <dt>
            <span id="crawledAt">
              <Translate contentKey="seocrawlerApp.crawlLog.crawledAt">Crawled At</Translate>
            </span>
          </dt>
          <dd>{crawlLogEntity.crawledAt ? <TextFormat value={crawlLogEntity.crawledAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="seocrawlerApp.crawlLog.tenant">Tenant</Translate>
          </dt>
          <dd>{crawlLogEntity.tenant ? crawlLogEntity.tenant.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/crawl-log" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crawl-log/${crawlLogEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CrawlLogDetail;
