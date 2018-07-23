CREATE TABLE Visitor(
  id BIGINT NOT NULL AUTO_INCREMENT,
  modificationCounter INTEGER NOT NULL,
  name VARCHAR(255),
  email VARCHAR(255),
  phone VARCHAR(255),
  idCode BIGINT,
  CONSTRAINT PK_Visitor PRIMARY KEY(id)
);

CREATE TABLE AccessCode(
  id BIGINT NOT NULL AUTO_INCREMENT,
  modificationCounter INTEGER NOT NULL,
  code VARCHAR(5),
  dateAndTime TIMESTAMP,
  idVisitor BIGINT,
  CONSTRAINT PK_AccessCode PRIMARY KEY(id),
  CONSTRAINT FK_AccessCode_idVisitor FOREIGN KEY(idVisitor) REFERENCES Visitor(id)
);