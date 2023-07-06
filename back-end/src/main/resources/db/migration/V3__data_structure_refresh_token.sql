CREATE TABLE `refresh_token` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `expiry_date` datetime(6) DEFAULT NULL,
                                 `token` varchar(255) DEFAULT NULL,
                                 `user_id` bigint DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `UQ_user_user_id` (`user_id`),
                                 CONSTRAINT `FK_user_refresh_token` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);
