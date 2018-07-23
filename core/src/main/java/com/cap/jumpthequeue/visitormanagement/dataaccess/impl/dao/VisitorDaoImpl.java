package com.cap.jumpthequeue.visitormanagement.dataaccess.impl.dao;

import java.util.List;

import javax.inject.Named;

import com.cap.jumpthequeue.general.dataaccess.base.dao.ApplicationDaoImpl;
import com.cap.jumpthequeue.visitormanagement.dataaccess.api.VisitorEntity;
import com.cap.jumpthequeue.visitormanagement.dataaccess.api.dao.VisitorDao;
import com.cap.jumpthequeue.visitormanagement.logic.api.to.VisitorSearchCriteriaTo;
import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

import io.oasp.module.jpa.common.api.to.OrderByTo;
import io.oasp.module.jpa.common.api.to.OrderDirection;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * This is the implementation of {@link VisitorDao}.
 */
@Named
public class VisitorDaoImpl extends ApplicationDaoImpl<VisitorEntity> implements VisitorDao {

  /**
   * The constructor.
   */
  public VisitorDaoImpl() {

    super();
  }

  @Override
  public Class<VisitorEntity> getEntityClass() {

    return VisitorEntity.class;
  }

  @Override
  public PaginatedListTo<VisitorEntity> findVisitors(VisitorSearchCriteriaTo criteria) {

    VisitorEntity visitor = Alias.alias(VisitorEntity.class);
    EntityPathBase<VisitorEntity> alias = Alias.$(visitor);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    String name = criteria.getName();
    if (name != null) {
      query.where(Alias.$(visitor.getName()).eq(name));
    }
    String email = criteria.getEmail();
    if (email != null) {
      query.where(Alias.$(visitor.getEmail()).eq(email));
    }
    String phone = criteria.getPhone();
    if (phone != null) {
      query.where(Alias.$(visitor.getPhone()).eq(phone));
    }
    Long code = criteria.getCodeId();
    if (code != null) {
      if (visitor.getCode() != null) {
        query.where(Alias.$(visitor.getCode().getId()).eq(code));
      }
    }
    addOrderBy(query, alias, visitor, criteria.getSort());

    return findPaginated(criteria, query, alias);
  }

  private void addOrderBy(JPAQuery query, EntityPathBase<VisitorEntity> alias, VisitorEntity visitor,
      List<OrderByTo> sort) {

    if (sort != null && !sort.isEmpty()) {
      for (OrderByTo orderEntry : sort) {
        switch (orderEntry.getName()) {
          case "name":
            if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
              query.orderBy(Alias.$(visitor.getName()).asc());
            } else {
              query.orderBy(Alias.$(visitor.getName()).desc());
            }
            break;
          case "email":
            if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
              query.orderBy(Alias.$(visitor.getEmail()).asc());
            } else {
              query.orderBy(Alias.$(visitor.getEmail()).desc());
            }
            break;
          case "phone":
            if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
              query.orderBy(Alias.$(visitor.getPhone()).asc());
            } else {
              query.orderBy(Alias.$(visitor.getPhone()).desc());
            }
            break;
          case "code":
            if (OrderDirection.ASC.equals(orderEntry.getDirection())) {
              query.orderBy(Alias.$(visitor.getCode().getId()).asc());
            } else {
              query.orderBy(Alias.$(visitor.getCode().getId()).desc());
            }
            break;
        }
      }
    }
  }

}