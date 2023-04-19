-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Apr 05, 2023 alle 23:21
-- Versione del server: 10.4.25-MariaDB
-- Versione PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
USE ripetizioni;
--
-- Database: `ripetizioni`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `courses`
--

CREATE TABLE `courses` (
  `ID` varchar(42) NOT NULL,
  `title` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `courses`
--

INSERT INTO `courses` (`ID`, `title`, `deleted`) VALUES
('1', 'Programmazione 1', 0),
('2', 'Algoritmi', 0),
('3', 'Database', 0),
('ef8707be-45bd-4ce5-a592-a790e0f9b641', 'Culoooo', 1),
('f3094d50-f194-4a48-8927-969c49371ad5', 'asd', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `professors`
--

CREATE TABLE `professors` (
  `ID` varchar(42) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `professors`
--

INSERT INTO `professors` (`ID`, `name`, `surname`, `deleted`) VALUES
('1', 'Giovanni', 'Rossi', 1),
('2', 'Maria', 'Bianchi', 1),
('3', 'Carlo', 'Neri', 0),
('4', 'Lucia', 'Verdi', 0),
('5', 'Matteo', 'Grigi', 0),
('96b38eea-9ef6-40fb-9908-531d44bf2fa9', 'Maria', 'Bianchi', 1),
('f67d1ad6-3061-4cd0-87fa-bbdad55c2b71', 'Antonio', 'Capocchia', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `repetitions`
--

CREATE TABLE `repetitions` (
  `ID` varchar(42) NOT NULL,
  `IDCourse` varchar(42) NOT NULL,
  `IDProfessor` varchar(42) NOT NULL,
  `IDUser` varchar(42) NOT NULL,
  `date` date NOT NULL,
  `time` int(11) NOT NULL CHECK (`time` >= 0 and `time` <= 23),
  `status` varchar(255) NOT NULL CHECK (`status` = 'done' or `status` = 'pending' or `status` = 'deleted'),
  `note` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `repetitions`
--

INSERT INTO `repetitions` (`ID`, `IDCourse`, `IDProfessor`, `IDUser`, `date`, `time`, `status`, `note`) VALUES
('240b9df2-ce6f-48e0-9f90-850cffb0e38a', '1', '1', '1', '2023-03-26', 14, 'deleted', 'Ripetizione annullata per cancellazione professore'),
('4b5283bb-c998-4cc8-ad48-29a61347f858', '1', '1', '1', '2023-03-26', 18, 'deleted', 'Ripetizione annullata per cancellazione professore'),
('693aa269-53a7-42a1-a6aa-3d7a94e6b446', '1', '1', '1', '2023-03-26', 17, 'deleted', 'Ripetizione annullata per cancellazione professore'),
('ed56dc6b-97b4-48af-ab22-f8f0007382e5', '1', '1', '1', '2023-03-26', 16, 'deleted', 'Ripetizione annullata per cancellazione professore'),
('ff439e73-f69a-47b9-8713-37c02c17d4b5', '1', '1', '1', '2023-03-26', 13, 'deleted', 'Ripetizione annullata per cancellazione professore');

-- --------------------------------------------------------

--
-- Struttura della tabella `teachings`
--

CREATE TABLE `teachings` (
  `ID` varchar(42) NOT NULL,
  `IDCourse` varchar(42) NOT NULL,
  `IDProfessor` varchar(42) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `ID` varchar(42) NOT NULL,
  `account` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`ID`, `account`, `email`, `password`, `name`, `surname`, `role`) VALUES
('1', 'giovanni', 'giovanni@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Giovanni', 'Bianchi', 'User'),
('2', 'maria', 'maria@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Maria', 'Rossi', 'User'),
('3', 'carlo', 'carlo@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Carlo', 'Verdi', 'User'),
('4', 'lucia', 'lucia@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Lucia', 'Neri', 'User'),
('5', 'matteo', 'matteo@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Matteo', 'Bianchi', 'User'),
('6', 'francesca', 'francesca@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Francesca', 'Rossi', 'User'),
('7', 'luca', 'luca@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Luca', 'Verdi', 'User'),
('8', 'giorgia', 'giorgia@example.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Giorgia', 'Neri', 'User'),
('9', 'emme', 'emmedeveloper@gmail.com', 'D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1', 'Marco', 'Molica', 'Admin');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `title` (`title`);

--
-- Indici per le tabelle `professors`
--
ALTER TABLE `professors`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `repetitions`
--
ALTER TABLE `repetitions`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDCourse` (`IDCourse`),
  ADD KEY `IDProfessor` (`IDProfessor`),
  ADD KEY `IDUser` (`IDUser`);

--
-- Indici per le tabelle `teachings`
--
ALTER TABLE `teachings`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `IDCourse` (`IDCourse`),
  ADD KEY `IDProfessor` (`IDProfessor`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `account` (`account`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `repetitions`
--
ALTER TABLE `repetitions`
  ADD CONSTRAINT `repetitions_ibfk_1` FOREIGN KEY (`IDCourse`) REFERENCES `courses` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `repetitions_ibfk_2` FOREIGN KEY (`IDProfessor`) REFERENCES `professors` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `repetitions_ibfk_3` FOREIGN KEY (`IDUser`) REFERENCES `users` (`ID`) ON DELETE CASCADE;

--
-- Limiti per la tabella `teachings`
--
ALTER TABLE `teachings`
  ADD CONSTRAINT `teachings_ibfk_1` FOREIGN KEY (`IDCourse`) REFERENCES `courses` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `teachings_ibfk_2` FOREIGN KEY (`IDProfessor`) REFERENCES `professors` (`ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
