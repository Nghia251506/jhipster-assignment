import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTenants } from 'app/entities/tenant/tenant.reducer';
import { createEntity, getEntity, reset, updateEntity } from './site-setting.reducer';

export const SiteSettingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tenants = useAppSelector(state => state.tenant.entities);
  const siteSettingEntity = useAppSelector(state => state.siteSetting.entity);
  const loading = useAppSelector(state => state.siteSetting.loading);
  const updating = useAppSelector(state => state.siteSetting.updating);
  const updateSuccess = useAppSelector(state => state.siteSetting.updateSuccess);

  const handleClose = () => {
    navigate(`/site-setting${location.search}`);
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

    const entity = {
      ...siteSettingEntity,
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
      ? {}
      : {
          ...siteSettingEntity,
          tenant: siteSettingEntity?.tenant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seocrawlerApp.siteSetting.home.createOrEditLabel" data-cy="SiteSettingCreateUpdateHeading">
            <Translate contentKey="seocrawlerApp.siteSetting.home.createOrEditLabel">Create or edit a SiteSetting</Translate>
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
                  id="site-setting-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seocrawlerApp.siteSetting.siteTitle')}
                id="site-setting-siteTitle"
                name="siteTitle"
                data-cy="siteTitle"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.siteSetting.siteDescription')}
                id="site-setting-siteDescription"
                name="siteDescription"
                data-cy="siteDescription"
                type="textarea"
              />
              <ValidatedField
                label={translate('seocrawlerApp.siteSetting.siteKeywords')}
                id="site-setting-siteKeywords"
                name="siteKeywords"
                data-cy="siteKeywords"
                type="textarea"
              />
              <ValidatedField
                label={translate('seocrawlerApp.siteSetting.gaTrackingId')}
                id="site-setting-gaTrackingId"
                name="gaTrackingId"
                data-cy="gaTrackingId"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.siteSetting.bannerTop')}
                id="site-setting-bannerTop"
                name="bannerTop"
                data-cy="bannerTop"
                type="textarea"
              />
              <ValidatedField
                label={translate('seocrawlerApp.siteSetting.bannerSidebar')}
                id="site-setting-bannerSidebar"
                name="bannerSidebar"
                data-cy="bannerSidebar"
                type="textarea"
              />
              <ValidatedField
                id="site-setting-tenant"
                name="tenant"
                data-cy="tenant"
                label={translate('seocrawlerApp.siteSetting.tenant')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/site-setting" replace color="info">
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

export default SiteSettingUpdate;
