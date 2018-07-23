package com.cap.jumpthequeue.accesscodemanagement.dataaccess.api;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.cap.jumpthequeue.accesscodemanagement.common.api.AccessCode;
import com.cap.jumpthequeue.general.dataaccess.api.ApplicationPersistenceEntity;
import com.cap.jumpthequeue.visitormanagement.dataaccess.api.VisitorEntity;

@Entity
@Table(name = "AccessCode")
public class AccessCodeEntity extends ApplicationPersistenceEntity implements AccessCode {

  private String code;

  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp dateAndTime;

  private VisitorEntity visitor;

  private static final long serialVersionUID = 1L;

  /**
   * @return code
   */
  public String getCode() {

    return this.code;
  }

  /**
   * @param code new value of {@link #getcode}.
   */
  public void setCode(String code) {

    this.code = code;
  }

  /**
   * @return dateAndTime
   */
  public Timestamp getDateAndTime() {

    return this.dateAndTime;
  }

  /**
   * @param dateAndTime new value of {@link #getdateAndTime}.
   */
  public void setDateAndTime(Timestamp dateAndTime) {

    this.dateAndTime = dateAndTime;
  }

  /**
   * @return visitor
   */
  @OneToOne
  @JoinColumn(name = "idVisitor")
  public VisitorEntity getVisitor() {

    return this.visitor;
  }

  /**
   * @param visitor new value of {@link #getvisitor}.
   */
  public void setVisitor(VisitorEntity visitor) {

    this.visitor = visitor;
  }

  @Override
  @Transient
  public Long getVisitorId() {

    if (this.visitor == null) {
      return null;
    }
    return this.visitor.getId();
  }

  @Override
  public void setVisitorId(Long visitorId) {

    if (visitorId == null) {
      this.visitor = null;
    } else {
      VisitorEntity visitorEntity = new VisitorEntity();
      visitorEntity.setId(visitorId);
      this.visitor = visitorEntity;
    }
  }

}
