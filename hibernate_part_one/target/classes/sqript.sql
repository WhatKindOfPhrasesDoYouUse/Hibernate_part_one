insert into customer
values(default, 'Dima_Oshuev'),
      (default, 'Egor_Domrachev'),
      (default, 'Oleg_Krotov'),
      (default, 'Vova_Arafaylov'),
      (default, 'Sasha_Nikulin'),
      (default, 'Sasha_Kuznetsov'),
      (default, 'Egor_Ivonin'),
      (default, 'Sasha_Khalturin'),
      (default, 'Vadim_Gimadiev'),
      (default, 'Natalya_Alexandrovna');

insert into product
values (default, 'Useless_Item', 100000, 10),
       (default, 'Diploma', 5, 11),
       (default, 'Herbert_Schildt', 1500, 13),
       (default, 'Lab', 500000,13),
       (default, 'Rtx_3090', 2,18),
       (default, 'Beer', 700,193),
       (default, 'Shaurma', 2,3),
       (default, 'Yes', 1500,1);

insert into customer_product
values (default, 13000, 1, 1),
       (default, 13000, 2, 1),
       (default, 13000, 3, 1),
       (default, 35499, 4, 2),
       (default, 35499, 5, 2),
       (default, 35499, 6, 2),
       (default, 15000, 7, 3),
       (default, 15000, 8, 3),
       (default, 15000, 9, 3),
       (default, 1000, 1, 4),
       (default, 1000, 2, 4),
       (default, 1000, 3, 4),
       (default, 50000, 4, 5),
       (default, 50000, 5, 5),
       (default, 50000, 6, 5),
       (default, 75499, 7, 6),
       (default, 75499, 8, 6),
       (default, 75499, 9, 6),
       (default, 2400, 1, 7),
       (default, 2400, 2, 7),
       (default, 2400, 3, 7),
       (default, 1500, 4, 8),
       (default, 1500, 5, 8),
       (default, 1500, 6, 8);

select * from customer_product;