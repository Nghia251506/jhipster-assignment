import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTenants } from 'app/entities/tenant/tenant.reducer';
import { getEntities as getSources } from 'app/entities/source/source.reducer';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { PostStatus } from 'app/shared/model/enumerations/post-status.model';
import { createEntity, getEntity, reset, updateEntity } from './post.reducer';

export const PostUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tenants = useAppSelector(state => state.tenant.entities);
  const sources = useAppSelector(state => state.source.entities);
  const categories = useAppSelector(state => state.category.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const postEntity = useAppSelector(state => state.post.entity);
  const loading = useAppSelector(state => state.post.loading);
  const updating = useAppSelector(state => state.post.updating);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const postStatusValues = Object.keys(PostStatus);

  const handleClose = () => {
    navigate(`/post${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTenants({}));
    dispatch(getSources({}));
    dispatch(getCategories({}));
    dispatch(getTags({}));
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
    values.publishedAt = convertDateTimeToServer(values.publishedAt);
    if (values.viewCount !== undefined && typeof values.viewCount !== 'number') {
      values.viewCount = Number(values.viewCount);
    }

    const entity = {
      ...postEntity,
      ...values,
      tenant: tenants.find(it => it.id.toString() === values.tenant?.toString()),
      source: sources.find(it => it.id.toString() === values.source?.toString()),
      category: categories.find(it => it.id.toString() === values.category?.toString()),
      tags: mapIdList(values.tags),
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
          publishedAt: displayDefaultDateTime(),
        }
      : {
          status: 'PENDING',
          ...postEntity,
          publishedAt: convertDateTimeFromServer(postEntity.publishedAt),
          tenant: postEntity?.tenant?.id,
          source: postEntity?.source?.id,
          category: postEntity?.category?.id,
          tags: postEntity?.tags?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seocrawlerApp.post.home.createOrEditLabel" data-cy="PostCreateUpdateHeading">
            <Translate contentKey="seocrawlerApp.post.home.createOrEditLabel">Create or edit a Post</Translate>
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
                  id="post-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seocrawlerApp.post.originUrl')}
                id="post-originUrl"
                name="originUrl"
                data-cy="originUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('seocrawlerApp.post.title')} id="post-title" name="title" data-cy="title" type="text" />
              <ValidatedField label={translate('seocrawlerApp.post.slug')} id="post-slug" name="slug" data-cy="slug" type="text" />
              <ValidatedField
                label={translate('seocrawlerApp.post.summary')}
                id="post-summary"
                name="summary"
                data-cy="summary"
                type="textarea"
              />
              <ValidatedField
                label={translate('seocrawlerApp.post.content')}
                id="post-content"
                name="content"
                data-cy="content"
                type="textarea"
              />
              <ValidatedField
                label={translate('seocrawlerApp.post.contentRaw')}
                id="post-contentRaw"
                name="contentRaw"
                data-cy="contentRaw"
                type="textarea"
              />
              <ValidatedField
                label={translate('seocrawlerApp.post.thumbnail')}
                id="post-thumbnail"
                name="thumbnail"
                data-cy="thumbnail"
                type="text"
              />
              <ValidatedField label={translate('seocrawlerApp.post.status')} id="post-status" name="status" data-cy="status" type="select">
                {postStatusValues.map(postStatus => (
                  <option value={postStatus} key={postStatus}>
                    {translate(`seocrawlerApp.PostStatus.${postStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('seocrawlerApp.post.publishedAt')}
                id="post-publishedAt"
                name="publishedAt"
                data-cy="publishedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('seocrawlerApp.post.viewCount')}
                id="post-viewCount"
                name="viewCount"
                data-cy="viewCount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField id="post-tenant" name="tenant" data-cy="tenant" label={translate('seocrawlerApp.post.tenant')} type="select">
                <option value="" key="0" />
                {tenants
                  ? tenants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="post-source" name="source" data-cy="source" label={translate('seocrawlerApp.post.source')} type="select">
                <option value="" key="0" />
                {sources
                  ? sources.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="post-category"
                name="category"
                data-cy="category"
                label={translate('seocrawlerApp.post.category')}
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
              <ValidatedField label={translate('seocrawlerApp.post.tags')} id="post-tags" data-cy="tags" type="select" multiple name="tags">
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post" replace color="info">
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

export default PostUpdate;
