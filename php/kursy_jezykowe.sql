-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 23 Cze 2020, 15:08
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `kursy_jezykowe`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `kursy`
--

CREATE TABLE IF NOT EXISTS `kursy` (
  `ID_kursu` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `poziom` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `cena` int(11) NOT NULL,
  `termin` date NOT NULL,
  PRIMARY KEY (`ID_kursu`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Zrzut danych tabeli `kursy`
--

INSERT INTO `kursy` (`ID_kursu`, `nazwa`, `poziom`, `cena`, `termin`) VALUES
(1, 'Angielski', 'A2', 3200, '2023-06-20'),
(2, 'niemiecki', 'B2', 3500, '2023-06-20'),
(3, 'mandaryński', 'C2', 4000, '2020-06-23'),
(4, 'Suachili', 'C1', 10000, '2020-07-02'),
(5, 'Portugalski', 'B1', 1200, '2020-08-13');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uzytkownicy`
--

CREATE TABLE IF NOT EXISTS `uzytkownicy` (
  `ID_uzytkownika` int(11) NOT NULL AUTO_INCREMENT,
  `rodzaj` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `imie` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `nazwisko` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `wiek` int(11) NOT NULL,
  `login` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `haslo` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`ID_uzytkownika`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Zrzut danych tabeli `uzytkownicy`
--

INSERT INTO `uzytkownicy` (`ID_uzytkownika`, `rodzaj`, `imie`, `nazwisko`, `wiek`, `login`, `haslo`) VALUES
(1, 'gość', 'Andzej', 'Rura', 34, 'andrzejrura@abcd.pl', '1234'),
(2, 'gość', 'Kuba', 'Luks', 25, 'kubaluks@abcd.pl', '1234'),
(3, 'gość', 'Aga', 'Bonk', 65, 'agabonk@abcd.pl', '1234'),
(4, 'administrator', 'Piotr', 'Fajek', 42, 'piotrfajek@abcd.pl', '1234'),
(5, 'user', 'Grzegorz', 'Wons', 39, 'grzegorzwons@abcd.pl', '1234');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `zapisy`
--

CREATE TABLE IF NOT EXISTS `zapisy` (
  `ID_zapisu` int(11) NOT NULL AUTO_INCREMENT,
  `imie` text NOT NULL,
  `nazwisko` text NOT NULL,
  `ID_kursu` int(11) NOT NULL,
  `ID_uzytkownika` int(11) NOT NULL,
  PRIMARY KEY (`ID_zapisu`),
  KEY `ID_kursu` (`ID_kursu`,`ID_uzytkownika`),
  KEY `ID_uzytkownika` (`ID_uzytkownika`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `zapisy`
--

INSERT INTO `zapisy` (`ID_zapisu`, `imie`, `nazwisko`, `ID_kursu`, `ID_uzytkownika`) VALUES
(1, 'Grzegorz', 'wons', 2, 5),
(2, 'Kuba', 'Luks', 4, 2);

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `zapisy`
--
ALTER TABLE `zapisy`
  ADD CONSTRAINT `zapisy_ibfk_2` FOREIGN KEY (`ID_uzytkownika`) REFERENCES `uzytkownicy` (`ID_uzytkownika`) ON DELETE NO ACTION,
  ADD CONSTRAINT `zapisy_ibfk_1` FOREIGN KEY (`ID_kursu`) REFERENCES `kursy` (`ID_kursu`) ON DELETE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
