import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { TenantStatus } from 'app/shared/model/enumerations/tenant-status.model';
import { createEntity, getEntity, reset, updateEntity } from './tenant.reducer';

export const TenantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tenantEntity = useAppSelector(state => state.tenant.entity);
  const loading = useAppSelector(state => state.tenant.loading);
  const updating = useAppSelector(state => state.tenant.updating);
  const updateSuccess = useAppSelector(state => state.tenant.updateSuccess);
  const tenantStatusValues = Object.keys(TenantStatus);

  const handleClose = () => {
    navigate(`/tenant${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.maxUsers !== undefined && typeof values.maxUsers !== 'number') {
      values.maxUsers = Number(values.maxUsers);
    }

    const entity = {
      ...tenantEntity,
      ...values,
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
          status: 'ACTIVE',
          ...tenantEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seocrawlerApp.tenant.home.createOrEditLabel" data-cy="TenantCreateUpdateHeading">
            <Translate contentKey="seocrawlerApp.tenant.home.createOrEditLabel">Create or edit a Tenant</Translate>
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
                  id="tenant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seocrawlerApp.tenant.code')}
                id="tenant-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.tenant.name')}
                id="tenant-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.tenant.contactEmail')}
                id="tenant-contactEmail"
                name="contactEmail"
                data-cy="contactEmail"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.tenant.contactPhone')}
                id="tenant-contactPhone"
                name="contactPhone"
                data-cy="contactPhone"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.tenant.maxUsers')}
                id="tenant-maxUsers"
                name="maxUsers"
                data-cy="maxUsers"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.tenant.status')}
                id="tenant-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {tenantStatusValues.map(tenantStatus => (
                  <option value={tenantStatus} key={tenantStatus}>
                    {translate(`seocrawlerApp.TenantStatus.${tenantStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tenant" replace color="info">
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

export default TenantUpdate;
