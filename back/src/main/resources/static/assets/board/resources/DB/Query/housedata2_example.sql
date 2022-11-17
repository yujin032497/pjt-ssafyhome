USE HOUSEDATA2;

-- -----------------------------------------------------
-- USER 예시 추가
-- -----------------------------------------------------

INSERT INTO USER
VALUES ('ssafy1', '1234', '김싸피', '서울시 강남구 역삼동', '010-1111-2222', '0'),
       ('ssafy2', '1234', '박싸피', '서울시 강남구 역삼동', '010-2222-1111', '0'),
       ('admin', 'admin', '관리자', '역삼역', '010-1234-5678', '1');

-- -----------------------------------------------------
-- FAVORITE 예시 추가
-- -----------------------------------------------------
INSERT INTO FAVORITE(DONGCODE_DONGCODE, USER_ID, ID) VALUES ('2714010200', 'ssafy1', 1),
                                                            ('1150010500', 'ssafy1', 2),
                                                            ('2644011100', 'ssafy2', 3);

-- -----------------------------------------------------
-- 주소 조회
-- -----------------------------------------------------
SELECT U.ID
     , DONG.DONGCODE
     , CONCAT(DONG.SIDONAME,' ', DONG.GUGUNNAME, ' ', DONG.DONGNAME)
  FROM USER U
  JOIN (SELECT D.DONGCODE
             , D.DONGNAME
             , D.SIDONAME
             , D.GUGUNNAME
             , F.USER_ID
          FROM DONGCODE D
          JOIN FAVORITE F
            ON F.DONGCODE_DONGCODE = D.DONGCODE
        ) DONG
     ON DONG.USER_ID = U.ID;