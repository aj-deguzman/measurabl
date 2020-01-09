SELECT S.ID SITE_ID, S.NAME SITE_NAME, S.ADDRESS, S.CITY, 
S.STATE, S.ZIPCODE, T.ID TYPE_ID, U.DESCRIPTION, T.NAME TYPE
FROM SITES S
INNER JOIN SITE_USES U
 ON U.SITE_ID = S.ID
INNER JOIN USE_TYPES T
 ON T.ID = U.USE_TYPE_ID
WHERE S.CITY = 'San Diego'