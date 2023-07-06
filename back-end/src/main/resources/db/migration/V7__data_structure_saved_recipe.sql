CREATE TABLE `saved_recipe` (
                                `recipe_id` bigint NOT NULL,
                                `user_id` bigint NOT NULL,
                                KEY `fk_recipe_has_user_user_id` (`user_id`),
                                KEY `fk_recipe_has_user_recipe_id` (`recipe_id`),
                                CONSTRAINT `fk_recipe_has_user_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                CONSTRAINT `fk_recipe_has_user_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
);
