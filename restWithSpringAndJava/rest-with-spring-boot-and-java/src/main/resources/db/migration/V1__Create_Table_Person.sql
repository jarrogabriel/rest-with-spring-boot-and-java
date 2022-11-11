

CREATE TABLE `tab_person`
(
   `id` bigint NOT NULL AUTO_INCREMENT,
   `tx_first_name` varchar (80) NOT NULL,
   `tx_last_name` varchar (80) NOT NULL,
   `tx_address` varchar (100) NOT NULL,
   `tx_gender` varchar (6) NOT NULL,
   PRIMARY KEY (`id`)
)
