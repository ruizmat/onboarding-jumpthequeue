package com.cap.jumpthequeue.general.logic.impl;

import java.sql.Blob;

import javax.inject.Inject;
import javax.inject.Named;

import com.cap.jumpthequeue.general.dataaccess.api.BinaryObjectEntity;
import com.cap.jumpthequeue.general.dataaccess.api.dao.BinaryObjectDao;
import com.cap.jumpthequeue.general.logic.api.UcManageBinaryObject;
import com.cap.jumpthequeue.general.logic.api.to.BinaryObjectEto;
import com.cap.jumpthequeue.general.logic.base.AbstractUc;

/**
 * Implementation of {@link UcManageBinaryObject}.
 */
@Named
public class UcManageBinaryObjectImpl extends AbstractUc implements UcManageBinaryObject {

  private BinaryObjectDao binaryObjectDao;

  /**
   * @return binaryObjectDao
   */
  public BinaryObjectDao binaryObjectDao() {

    return this.binaryObjectDao;
  }

  /**
   * @param binaryObjectDao the binaryObjectDao to set
   */
  @Inject
  public void setBinaryObjectDao(BinaryObjectDao binaryObjectDao) {

    this.binaryObjectDao = binaryObjectDao;
  }

  @Override
  public BinaryObjectEto saveBinaryObject(Blob data, BinaryObjectEto binaryObjectEto) {

    BinaryObjectEntity binaryObjectEntity = getBeanMapper().map(binaryObjectEto, BinaryObjectEntity.class);
    binaryObjectEntity.setData(data);
    this.binaryObjectDao.save(binaryObjectEntity);
    return getBeanMapper().map(binaryObjectEntity, BinaryObjectEto.class);
  }

  @Override
  public void deleteBinaryObject(Long binaryObjectId) {

    this.binaryObjectDao.delete(binaryObjectId);

  }

  @Override
  public BinaryObjectEto findBinaryObject(Long binaryObjectId) {

    return getBeanMapper().map(this.binaryObjectDao.findOne(binaryObjectId), BinaryObjectEto.class);
  }

  @Override
  public Blob getBinaryObjectBlob(Long binaryObjectId) {

    return this.binaryObjectDao.findOne(binaryObjectId).getData();
  }

}
