import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntities as getTenants } from 'app/entities/tenant/tenant.reducer';
import { createEntity, getEntity, reset, updateEntity } from './app-user.reducer';

export const AppUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const tenants = useAppSelector(state => state.tenant.entities);
  const appUserEntity = useAppSelector(state => state.appUser.entity);
  const loading = useAppSelector(state => state.appUser.loading);
  const updating = useAppSelector(state => state.appUser.updating);
  const updateSuccess = useAppSelector(state => state.appUser.updateSuccess);

  const handleClose = () => {
    navigate(`/app-user${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
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
      ...appUserEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user?.toString()),
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
          ...appUserEntity,
          user: appUserEntity?.user?.id,
          tenant: appUserEntity?.tenant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seocrawlerApp.appUser.home.createOrEditLabel" data-cy="AppUserCreateUpdateHeading">
            <Translate contentKey="seocrawlerApp.appUser.home.createOrEditLabel">Create or edit a AppUser</Translate>
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
                  id="app-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seocrawlerApp.appUser.fullName')}
                id="app-user-fullName"
                name="fullName"
                data-cy="fullName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="app-user-user" name="user" data-cy="user" label={translate('seocrawlerApp.appUser.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="app-user-tenant"
                name="tenant"
                data-cy="tenant"
                label={translate('seocrawlerApp.appUser.tenant')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-user" replace color="info">
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

export default AppUserUpdate;
