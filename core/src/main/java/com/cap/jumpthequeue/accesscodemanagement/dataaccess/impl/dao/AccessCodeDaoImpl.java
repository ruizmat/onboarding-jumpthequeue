package com.cap.jumpthequeue.accesscodemanagement.dataaccess.impl.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Named;

import com.cap.jumpthequeue.accesscodemanagement.dataaccess.api.AccessCodeEntity;
import com.cap.jumpthequeue.accesscodemanagement.dataaccess.api.dao.AccessCodeDao;
import com.cap.jumpthequeue.accesscodemanagement.logic.api.to.AccessCodeSearchCriteriaTo;
import com.cap.jumpthequeue.general.dataaccess.base.dao.ApplicationDaoImpl;
import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

import io.oasp.module.jpa.common.api.to.OrderByTo;
import io.oasp.module.jpa.common.api.to.OrderDirection;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * This is the implementation of {@link AccessCodeDao}.
 */
@Named
public class AccessCodeDaoImpl extends ApplicationDaoImpl<AccessCodeEntity> implements AccessCodeDao {

  /**
   * The constructor.
   */
  public AccessCodeDaoImpl() {

    super();
  }

  @Override
  public Class<AccessCodeEntity> getEntityClass() {

    return AccessCodeEntity.class;
  }

  @Override
  public PaginatedListTo<AccessCodeEntity> findAccessCodes(AccessCodeSearchCriteriaTo criteria) {

    AccessCodeEntity accesscode = Alias.alias(AccessCodeEntity.class);
    EntityPathBase<AccessCodeEntity> alias = Alias.$(accesscode);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    String code = criteria.getCode();
    if (code != null) {
      query.where(Alias.$(accesscode.getCode()).eq(code));
    }
    Timestamp dateAndTime = criteria.getDateAndTime();
    if (dateAndTime != null) {
      query.where(Alias.$(accesscode.getDateAndTime()).eq(dateAndTime));
    }
    Long visitor = criteria.getVisitorId();
    if (visitor != null) {
      if (accesscode.getVisitor() != null) {
        query.where(Alias.$(accesscode.getVisitor().getId()).eq(visitor));
      }
    }
    addOrderBy(query, alias, accesscode, criteria.getSort());

    return findPaginated(criteria, query, alias);
  }

  private void addOrderBy(JPAQuery query, EntityPathBase<AccessCodeEntity> alias, AccessCodeEntity accesscode,
      List<OrderByTo> sort) {

    if (sort != null && !sort.isEmpty()) {
      for (OrderByTo orderEntry : sort) {
        switch (orderEntry.getName()) {
          case "code":
            if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
              query.orderBy(Alias.$(accesscode.getCode()).asc());
            } else {
              query.orderBy(Alias.$(accesscode.getCode()).desc());
            }
            break;
          case "dateAndTime":
            if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
              query.orderBy(Alias.$(accesscode.getDateAndTime()).asc());
            } else {
              query.orderBy(Alias.$(accesscode.getDateAndTime()).desc());
            }
            break;
          case "visitor":
            if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
              query.orderBy(Alias.$(accesscode.getVisitor().getId()).asc());
            } else {
              query.orderBy(Alias.$(accesscode.getVisitor().getId()).desc());
            }
            break;
        }
      }
    }
  }

}