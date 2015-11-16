-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 16, 2015 at 03:29 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `shoppingcart`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
  `CAT_ID` int(11) NOT NULL,
  `CAT_NAME` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`CAT_ID`, `CAT_NAME`) VALUES
(1, 'Clothes'),
(2, 'Shoes');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `PRO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRO_NAME` varchar(255) NOT NULL,
  `CAT_ID` int(11) NOT NULL,
  `PRICE` double NOT NULL,
  `STOCK` int(11) NOT NULL,
  PRIMARY KEY (`PRO_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`PRO_ID`, `PRO_NAME`, `CAT_ID`, `PRICE`, `STOCK`) VALUES
(1, 'Polo Shirt Black', 1, 1000, 10),
(2, 'Quick Silver Shirt', 1, 2000, 8),
(3, 'Eiger Red', 2, 2000, 12),
(4, 'Montana Black', 2, 2000, 7),
(5, 'The Mountain Blue', 2, 2000, 8),
(6, 'Airwalk White', 2, 2000, 5);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
