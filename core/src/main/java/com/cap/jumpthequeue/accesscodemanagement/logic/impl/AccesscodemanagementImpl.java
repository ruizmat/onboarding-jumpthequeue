package com.cap.jumpthequeue.accesscodemanagement.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.cap.jumpthequeue.accesscodemanagement.dataaccess.api.AccessCodeEntity;
import com.cap.jumpthequeue.accesscodemanagement.dataaccess.api.dao.AccessCodeDao;
import com.cap.jumpthequeue.accesscodemanagement.logic.api.Accesscodemanagement;
import com.cap.jumpthequeue.accesscodemanagement.logic.api.to.AccessCodeCto;
import com.cap.jumpthequeue.accesscodemanagement.logic.api.to.AccessCodeEto;
import com.cap.jumpthequeue.accesscodemanagement.logic.api.to.AccessCodeSearchCriteriaTo;
import com.cap.jumpthequeue.general.logic.base.AbstractComponentFacade;
import com.cap.jumpthequeue.visitormanagement.logic.api.to.VisitorEto;

import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationResultTo;

/**
 * Implementation of component interface of accesscodemanagement
 */
@Named
@Transactional
public class AccesscodemanagementImpl extends AbstractComponentFacade implements Accesscodemanagement {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(AccesscodemanagementImpl.class);

  /** @see #getAccessCodeDao() */
  @Inject
  private AccessCodeDao accessCodeDao;

  /**
   * The constructor.
   */
  public AccesscodemanagementImpl() {

    super();
  }

  @Override
  public AccessCodeEto findAccessCode(Long id) {

    LOG.debug("Get AccessCode with id {} from database.", id);
    return getBeanMapper().map(getAccessCodeDao().findOne(id), AccessCodeEto.class);
  }

  @Override
  public PaginatedListTo<AccessCodeEto> findAccessCodeEtos(AccessCodeSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<AccessCodeEntity> accesscodes = getAccessCodeDao().findAccessCodes(criteria);
    return mapPaginatedEntityList(accesscodes, AccessCodeEto.class);
  }

  @Override
  public boolean deleteAccessCode(Long accessCodeId) {

    AccessCodeEntity accessCode = getAccessCodeDao().find(accessCodeId);
    getAccessCodeDao().delete(accessCode);
    LOG.debug("The accessCode with id '{}' has been deleted.", accessCodeId);
    return true;
  }

  @Override
  public AccessCodeEto saveAccessCode(AccessCodeEto accessCode) {

    Objects.requireNonNull(accessCode, "accessCode");
    AccessCodeEntity accessCodeEntity = getBeanMapper().map(accessCode, AccessCodeEntity.class);

    // initialize, validate accessCodeEntity here if necessary
    AccessCodeEntity resultEntity = getAccessCodeDao().save(accessCodeEntity);
    LOG.debug("AccessCode with id '{}' has been created.", resultEntity.getId());

    return getBeanMapper().map(resultEntity, AccessCodeEto.class);
  }

  /**
   * Returns the field 'accessCodeDao'.
   *
   * @return the {@link AccessCodeDao} instance.
   */
  public AccessCodeDao getAccessCodeDao() {

    return this.accessCodeDao;
  }

  @Override
  public AccessCodeCto findAccessCodeCto(Long id) {

    LOG.debug("Get AccessCodeCto with id {} from database.", id);
    AccessCodeEntity entity = getAccessCodeDao().findOne(id);
    AccessCodeCto cto = new AccessCodeCto();
    cto.setAccessCode(getBeanMapper().map(entity, AccessCodeEto.class));
    cto.setVisitor(getBeanMapper().map(entity.getVisitor(), VisitorEto.class));

    return cto;
  }

  @Override
  public PaginatedListTo<AccessCodeCto> findAccessCodeCtos(AccessCodeSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<AccessCodeEntity> accesscodes = getAccessCodeDao().findAccessCodes(criteria);
    List<AccessCodeCto> ctos = new ArrayList<>();
    for (AccessCodeEntity entity : accesscodes.getResult()) {
      AccessCodeCto cto = new AccessCodeCto();
      cto.setAccessCode(getBeanMapper().map(entity, AccessCodeEto.class));
      cto.setVisitor(getBeanMapper().map(entity.getVisitor(), VisitorEto.class));
      ctos.add(cto);

    }
    PaginationResultTo pagResultTo = new PaginationResultTo(criteria.getPagination(), (long) ctos.size());
    PaginatedListTo<AccessCodeCto> pagListTo = new PaginatedListTo(ctos, pagResultTo);
    return pagListTo;
  }

  @Override
  public String generateCode(Random rng, int length) {

    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    char[] text = new char[length];
    for (int i = 0; i < length; i++) {
      text[i] = characters.charAt(rng.nextInt(characters.length()));
    }
    return new String(text);
  }

}