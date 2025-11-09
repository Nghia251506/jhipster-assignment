import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTenants } from 'app/entities/tenant/tenant.reducer';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { createEntity, getEntity, reset, updateEntity } from './source.reducer';

export const SourceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tenants = useAppSelector(state => state.tenant.entities);
  const categories = useAppSelector(state => state.category.entities);
  const sourceEntity = useAppSelector(state => state.source.entity);
  const loading = useAppSelector(state => state.source.loading);
  const updating = useAppSelector(state => state.source.updating);
  const updateSuccess = useAppSelector(state => state.source.updateSuccess);

  const handleClose = () => {
    navigate(`/source${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTenants({}));
    dispatch(getCategories({}));
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
      ...sourceEntity,
      ...values,
      tenant: tenants.find(it => it.id.toString() === values.tenant?.toString()),
      category: categories.find(it => it.id.toString() === values.category?.toString()),
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
          ...sourceEntity,
          tenant: sourceEntity?.tenant?.id,
          category: sourceEntity?.category?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seocrawlerApp.source.home.createOrEditLabel" data-cy="SourceCreateUpdateHeading">
            <Translate contentKey="seocrawlerApp.source.home.createOrEditLabel">Create or edit a Source</Translate>
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
                  id="source-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seocrawlerApp.source.name')}
                id="source-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.baseUrl')}
                id="source-baseUrl"
                name="baseUrl"
                data-cy="baseUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.listUrl')}
                id="source-listUrl"
                name="listUrl"
                data-cy="listUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.listItemSelector')}
                id="source-listItemSelector"
                name="listItemSelector"
                data-cy="listItemSelector"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.linkAttr')}
                id="source-linkAttr"
                name="linkAttr"
                data-cy="linkAttr"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.titleSelector')}
                id="source-titleSelector"
                name="titleSelector"
                data-cy="titleSelector"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.contentSelector')}
                id="source-contentSelector"
                name="contentSelector"
                data-cy="contentSelector"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.thumbnailSelector')}
                id="source-thumbnailSelector"
                name="thumbnailSelector"
                data-cy="thumbnailSelector"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.authorSelector')}
                id="source-authorSelector"
                name="authorSelector"
                data-cy="authorSelector"
                type="text"
              />
              <ValidatedField
                label={translate('seocrawlerApp.source.isActive')}
                id="source-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('seocrawlerApp.source.note')} id="source-note" name="note" data-cy="note" type="text" />
              <ValidatedField
                id="source-tenant"
                name="tenant"
                data-cy="tenant"
                label={translate('seocrawlerApp.source.tenant')}
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
              <ValidatedField
                id="source-category"
                name="category"
                data-cy="category"
                label={translate('seocrawlerApp.source.category')}
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/source" replace color="info">
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

export default SourceUpdate;
