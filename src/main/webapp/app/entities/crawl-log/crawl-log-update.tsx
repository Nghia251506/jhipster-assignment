import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTenants } from 'app/entities/tenant/tenant.reducer';
import { CrawlStatus } from 'app/shared/model/enumerations/crawl-status.model';
import { createEntity, getEntity, reset, updateEntity } from './crawl-log.reducer';

export const CrawlLogUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tenants = useAppSelector(state => state.tenant.entities);
  const crawlLogEntity = useAppSelector(state => state.crawlLog.entity);
  const loading = useAppSelector(state => state.crawlLog.loading);
  const updating = useAppSelector(state => state.crawlLog.updating);
  const updateSuccess = useAppSelector(state => state.crawlLog.updateSuccess);
  const crawlStatusValues = Object.keys(CrawlStatus);

  const handleClose = () => {
    navigate(`/crawl-log${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTenants({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.crawledAt = convertDateTimeToServer(values.crawledAt);

    const entity = {
      ...crawlLogEntity,
      ...values,
      tenant: tenants.find(it => it.id.toString() === values.tenant?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          crawledAt: displayDefaultDateTime(),
        }
      : {
          status: 'NEW',
          ...crawlLogEntity,
          crawledAt: convertDateTimeFromServer(crawlLogEntity.crawledAt),
          tenant: crawlLogEntity?.tenant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seocrawlerApp.crawlLog.home.createOrEditLabel" data-cy="CrawlLogCreateUpdateHeading">
            <Translate contentKey="seocrawlerApp.crawlLog.home.createOrEditLabel">Create or edit a CrawlLog</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="crawl-log-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seocrawlerApp.crawlLog.url')}
                id="crawl-log-url"
                name="url"
                data-cy="url"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.crawlLog.status')}
                id="crawl-log-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {crawlStatusValues.map(crawlStatus => (
                  <option value={crawlStatus} key={crawlStatus}>
                    {translate(`seocrawlerApp.CrawlStatus.${crawlStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('seocrawlerApp.crawlLog.errorMessage')}
                id="crawl-log-errorMessage"
                name="errorMessage"
                data-cy="errorMessage"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.crawlLog.crawledAt')}
                id="crawl-log-crawledAt"
                name="crawledAt"
                data-cy="crawledAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="crawl-log-tenant"
                name="tenant"
                data-cy="tenant"
                label={translate('seocrawlerApp.crawlLog.tenant')}
                type="select"
              >
                <option value="" key="0" />
                {tenants
                  ? tenants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crawl-log" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CrawlLogUpdate;
