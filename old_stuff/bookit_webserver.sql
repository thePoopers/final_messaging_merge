-- phpMyAdmin SQL Dump
-- version 4.2.6deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 30, 2015 at 05:21 PM
-- Server version: 5.5.41-0ubuntu0.14.10.1
-- PHP Version: 5.5.12-2ubuntu4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bookit_webserver`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
`post_id` int(11) NOT NULL,
  `username` varchar(64) DEFAULT NULL,
  `title` varchar(120) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`post_id`, `username`, `title`, `message`) VALUES
(1, 'testing', 'title', 'message'),
(2, 'mariya', 'another title', 'this is a post message'),
(3, 'another', 'titlez', 'messz'),
(4, 'mariya', 'yo', 'testing'),
(5, 'gab', 'www', 'dfjdjdjd'),
(6, 'gab', 'asd', 'ddd'),
(7, 'gab', 'long', 'sjdjckrk jdj jejdnd d dj jck jd e d  kd dk ks  ks wkwovi hsbe dj jw  eici j jdbxhc jd esk  bsj jd   ksb.i beb  jsjc idh '),
(8, 'gab', 'super long', '19292wbd d doeb3 e fbxne eb3jejejeekdiidididifi  i i j h h h u i i i u u u u h h h hr e ek i  jebewi  j bej je eebei i  uebebebeebebeneidjenjeejjsvie 3bfjdjekejene d  k kcnewnenenenek'),
(9, 'new guy', 'testing123', 'asdf'),
(10, 'mariya', 'a title', 'need some timestamps, notifications, user pictures, etc\n'),
(11, 'carlos', 'asdf', 'testing'),
(12, 'mariya', 'g', 'asd');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(64) NOT NULL,
  `password` varchar(256) NOT NULL,
`id` int(11) NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `id`) VALUES
('bro', 'test', 10),
('mariya', 'poop', 2),
('mouseplay', 'manny', 3),
('fresh', 'test', 4),
('gab', '123', 5),
('new guy', 'test', 6),
('mannym', 'password1', 9),
('carlos', '123', 8);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
 ADD UNIQUE KEY `post_id` (`post_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `username` (`username`,`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
