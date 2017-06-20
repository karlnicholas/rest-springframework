INSERT INTO PRODUCT (ID, SKU, NAME, DESCRIPTION, CATEGORY) VALUES (default, '111-AA', 'Widget', 'Cool Widget', 'Widget');
INSERT INTO PRODUCT (ID, SKU, NAME, DESCRIPTION, CATEGORY) VALUES (default, '112-AB', 'Widget-2', 'Really Cool Widget', 'Widget');
INSERT INTO PRODUCT (ID, SKU, NAME, DESCRIPTION, CATEGORY) VALUES (default, '000-OO', 'Spam', 'Classic Canned Meat Gluten Free. Fully cooked, ready to eat--cold or hot.', 'Food');
INSERT INTO PRODUCT (ID, SKU, NAME, DESCRIPTION, CATEGORY) VALUES (default, '123-SF', 'Apple iPad Air 2', 'Apple iPad Air 2 tablet, featuring a thin Retina display and antireflective coating.', 'Electronics');
INSERT INTO PURCHASEORDER (ID, COMMENT, ORDERDATE) VALUES (default, 'First Order, Yea!', '2017-03-31');
INSERT INTO ORDERITEM (ID, PRODUCT_ID, QUANTITY, ITEMPRICE, PURCHASEORDER_ID, INDEX) VALUES (default, 1, 1, 1.99, 1, 1);
INSERT INTO ORDERITEM (ID, PRODUCT_ID, QUANTITY, ITEMPRICE, PURCHASEORDER_ID, INDEX) VALUES (default, 2, 1, 2.99, 1, 0);
INSERT INTO PURCHASEORDER (ID, COMMENT, ORDERDATE) VALUES (default, 'Second Order, Yea!', '2017-03-31');
