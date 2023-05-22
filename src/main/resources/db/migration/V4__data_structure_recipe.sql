CREATE TABLE `recipe` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `created_at` datetime NOT NULL,
                          `updated_at` datetime NOT NULL,
                          `cook_time` int NOT NULL,
                          `recipe_name` varchar(100) NOT NULL,
                          `author_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `fk_recipe_user` (`author_id`),
                          CONSTRAINT `fk_recipe_user` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
);
