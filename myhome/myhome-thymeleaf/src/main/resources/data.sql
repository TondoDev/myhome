INSERT INTO EXPENSE(ID, DATE, AMOUNT, EXPENSE_TYPE, NOTE, CREATED) VALUES(1, '2016-09-27',  4, 'FOOD', 'it was tasty!', CURRENT_DATE());
INSERT INTO EXPENSE(ID, DATE, AMOUNT, EXPENSE_TYPE, NOTE, CREATED) VALUES(2, '2017-09-27', 51, 'FUN', 'Enemy KIA', CURRENT_DATE());

INSERT INTO WATER_USAGE(MEASURED, COLD_USAGE, WARM_USAGE) VALUES ('2019-01-20', 58.126, 0.792);
INSERT INTO WATER_USAGE(MEASURED, COLD_USAGE, WARM_USAGE) VALUES ('2019-01-28', 58.475, 0.916);
INSERT INTO WATER_USAGE(MEASURED, COLD_USAGE, WARM_USAGE) VALUES ('2019-02-05', 58.909, 1.054);
INSERT INTO WATER_USAGE(MEASURED, COLD_USAGE, WARM_USAGE) VALUES ('2019-02-18', 59.513, 1.204);
INSERT INTO WATER_USAGE(MEASURED, COLD_USAGE, WARM_USAGE) VALUES ('2019-03-04', 60.116, 1.369);
INSERT INTO WATER_USAGE(MEASURED, COLD_USAGE, WARM_USAGE) VALUES ('2019-03-26', 60.988, 1.618);


INSERT INTO INVESTMENT(ID, NAME, ESTABLISHING_DATE, AMOUNT_OF_PAY, FEE_PCT) VALUES(1, 'Nevyhodne sporenie', '2017-11-27', 30, 0.02);
INSERT INTO saving_account(ID, INTEREST) VALUES (1, 0.5);


INSERT INTO INVESTMENT(ID, NAME, ESTABLISHING_DATE, AMOUNT_OF_PAY, FEE_PCT) VALUES(2, 'Prvy fond', '2019-11-27', 80, 0.02);
INSERT INTO FOND (ID, ISIN) VALUES (2, 'ISIN:8987');
INSERT INTO FOND_PAYMENT(ID, BUY_PRICE, FEE, DATE_OF_PURCHASE, PURCHASED_UNITS, PARENT_FOND_ID) VALUES (1, 50, 4.25, '2019-03-25', 20.0, 2);
INSERT INTO FOND_PAYMENT(ID, BUY_PRICE, FEE, DATE_OF_PURCHASE, PURCHASED_UNITS, PARENT_FOND_ID) VALUES (2, 40, 3.25, '2019-04-25', 4.0, 2);
