INSERT INTO `users` (`id`, `created_at`, `name`, `password`, `username`, `role`) VALUES
(1, '2014-12-22 13:19:42', 'Admin', '$2a$10$1XSwotJ5ysdtW1urRkQXaeEsT88Th.8ZmB/jFARj6SJEZdSJwSOa6', 'admin', 'ROLE_ADMIN'),
(2, '2014-12-23 10:52:45', 'Manager', '$2a$10$hLF/zU/.iblyUNSA15tWXOd6oCsH8Z.enMhWJ7zPzNGlaZFjMv6pK', 'manager', 'ROLE_MANAGER'),
(11, '2014-12-23 14:45:51', 'User', '$2a$10$OQhl2B2KsDPd3w9udZhIt.nvggP.ackcnNHerY2dbv15vLiW7qvB2', 'user', 'ROLE_USER');

INSERT INTO `posts` (`id`, `title`, `text`, `created_at`, `user_id`) VALUES
(12, 'Admins post', 'Text for post111111', '2014-12-23 13:31:53', 1),
(13, 'Post2', 'text for post2', '2014-12-23 13:32:27', 2),
(18, 'Users post', 'test', '2014-12-29 13:04:09', 11);

INSERT INTO `tags` (`id`, `created_at`, `name`) VALUES
(29, '2014-12-25 12:49:26', 'tag1'),
(30, '2014-12-25 12:49:46', 'tag3'),
(32, '2014-12-25 12:52:26', 'tag12'),
(33, '2014-12-25 12:53:36', 'tag4'),
(34, '2014-12-25 12:54:45', 'tag2'),
(37, '2014-12-29 13:03:51', 'tag1'),
(38, '2014-12-29 13:03:51', 'tag4'),
(39, '2014-12-29 13:04:09', 'user'),
(41, '2014-12-29 14:06:32', 'tag1'),
(42, '2014-12-29 14:06:32', 'tag3');

INSERT INTO `post_tags` (`tag_id`, `post_id`) VALUES
(29, 12),
(29, 13),
(30, 13),
(33, 12),
(39, 18);
