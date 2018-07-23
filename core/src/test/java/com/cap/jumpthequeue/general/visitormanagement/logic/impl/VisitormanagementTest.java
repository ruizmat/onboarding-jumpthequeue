package com.cap.jumpthequeue.general.visitormanagement.logic.impl;

/**
 * @author mruizand
 *
 */
import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cap.jumpthequeue.SpringBootApp;
import com.cap.jumpthequeue.visitormanagement.logic.api.Visitormanagement;
import com.cap.jumpthequeue.visitormanagement.logic.api.to.VisitorCto;
import com.cap.jumpthequeue.visitormanagement.logic.api.to.VisitorEto;
import com.cap.jumpthequeue.visitormanagement.logic.api.to.VisitorSearchCriteriaTo;

import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.test.common.base.ComponentTest;

@SpringBootTest(classes = SpringBootApp.class)
public class VisitormanagementTest extends ComponentTest {

  @Inject
  private Visitormanagement visitormanagement;

  @Test
  public void registerVisitorTest() {

    VisitorEto visitor = new VisitorEto();
    visitor.setName("Mary");
    visitor.setEmail("mary@mail.com");
    visitor.setPhone("123456789");
    VisitorCto result = this.visitormanagement.registerVisitor(visitor);
    assertThat(result).isNotNull();
    assertThat(result.getVisitor().getName()).isEqualTo("Mary");
    assertThat(result.getCode().getCode()).isNotEmpty();

  }

  @Test
  public void listVisitorsTest() {

    VisitorSearchCriteriaTo criteria = new VisitorSearchCriteriaTo();
    PaginatedListTo<VisitorCto> result = this.visitormanagement.findVisitorCtos(criteria);
    assertThat(result).isNotNull();
  }

}