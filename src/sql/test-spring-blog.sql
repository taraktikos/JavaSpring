-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 31, 2014 at 11:54 AM
-- Server version: 5.5.40-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `spring-blog`
--

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE IF NOT EXISTS `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `text` text NOT NULL,
  `created_at` datetime NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pn7a4a2mjksl19jlm7k106m7x` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `title`, `text`, `created_at`, `user_id`) VALUES
(12, 'Admins post', 'Text for post111111', '2014-12-23 13:31:53', 1),
(13, 'Post2', 'text for post2', '2014-12-23 13:32:27', 2),
(18, 'Users post', 'test', '2014-12-29 13:04:09', 11);

-- --------------------------------------------------------

--
-- Table structure for table `post_tags`
--

CREATE TABLE IF NOT EXISTS `post_tags` (
  `tag_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tag_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `post_tags`
--

INSERT INTO `post_tags` (`tag_id`, `post_id`) VALUES
(29, 12),
(29, 13),
(29, 16),
(29, 17),
(30, 13),
(32, 16),
(33, 12),
(39, 18);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE IF NOT EXISTS `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=45 ;

--
-- Dumping data for table `tags`
--

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

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_at`, `name`, `password`, `username`, `role`) VALUES
(1, '2014-12-22 13:19:42', 'Admin', '$2a$10$1XSwotJ5ysdtW1urRkQXaeEsT88Th.8ZmB/jFARj6SJEZdSJwSOa6', 'admin', 'ROLE_ADMIN'),
(2, '2014-12-23 10:52:45', 'Manager', '$2a$10$hLF/zU/.iblyUNSA15tWXOd6oCsH8Z.enMhWJ7zPzNGlaZFjMv6pK', 'manager', 'ROLE_MANAGER'),
(11, '2014-12-23 14:45:51', 'User', '$2a$10$OQhl2B2KsDPd3w9udZhIt.nvggP.ackcnNHerY2dbv15vLiW7qvB2', 'user', 'ROLE_USER');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `FK_pn7a4a2mjksl19jlm7k106m7x` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `post_tags`
--
ALTER TABLE `post_tags`
  ADD CONSTRAINT `FK_n8k2owli9ecanh4phj01mddvv` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
