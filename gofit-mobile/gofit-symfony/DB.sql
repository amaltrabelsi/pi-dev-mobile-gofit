-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 09 mai 2022 à 15:49
-- Version du serveur : 10.4.22-MariaDB
-- Version de PHP : 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gofit`
--

-- --------------------------------------------------------

--
-- Structure de la table `actualite`
--

CREATE TABLE `actualite` (
  `Actualite_Id` int(11) NOT NULL,
  `Titre` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `Description` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `Contenu` text COLLATE utf8mb4_bin NOT NULL,
  `Categorie` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `Path_Image` varchar(450) COLLATE utf8mb4_bin DEFAULT '''NULL''',
  `FK_user_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Déchargement des données de la table `actualite`
--

INSERT INTO `actualite` (`Actualite_Id`, `Titre`, `Description`, `Contenu`, `Categorie`, `Path_Image`, `FK_user_Id`) VALUES
(1, 'teeestteeestte', 'teeestteeestteeestteeest', 'teeestteeesttee', 'Fitness', 'C:\\xampp\\tmp\\php2344.tmp', 190);

-- --------------------------------------------------------

--
-- Structure de la table `business`
--

CREATE TABLE `business` (
  `Business_Id` int(11) NOT NULL,
  `Nom_Business` varchar(30) NOT NULL,
  `Nom_Gerant` varchar(30) NOT NULL,
  `Prenom_Gerant` varchar(30) NOT NULL,
  `Region` varchar(30) NOT NULL,
  `Adresse` varchar(100) NOT NULL,
  `Date_Fondation` date NOT NULL,
  `Description` text NOT NULL,
  `Tel_Portable` varchar(10) NOT NULL,
  `Tel_Fix` varchar(10) DEFAULT '''NULL''',
  `Path_Image` text NOT NULL,
  `Note` int(11) DEFAULT NULL,
  `totalnote` int(11) NOT NULL,
  `occurence` int(11) NOT NULL,
  `User_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `business`
--

INSERT INTO `business` (`Business_Id`, `Nom_Business`, `Nom_Gerant`, `Prenom_Gerant`, `Region`, `Adresse`, `Date_Fondation`, `Description`, `Tel_Portable`, `Tel_Fix`, `Path_Image`, `Note`, `totalnote`, `occurence`, `User_Id`) VALUES
(1, 'B and L', 'nour', 'nour', 'Ben Arous', '05 bis rue de maroc megrine', '2017-02-20', 'i love it', '97326877', '71425178', 'C:\\Users\\ASUS\\Pictures\\GL\\amal.png', 20, 5, 5, 190);

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `Commande_Id` int(11) NOT NULL,
  `Date_C` date NOT NULL,
  `Total` double NOT NULL,
  `Nb_Produit` int(11) NOT NULL,
  `Mode_Paiement` varchar(50) NOT NULL,
  `FK_ClientPan_Id` int(11) DEFAULT NULL,
  `statut` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`Commande_Id`, `Date_C`, `Total`, `Nb_Produit`, `Mode_Paiement`, `FK_ClientPan_Id`, `statut`) VALUES
(12, '2022-04-16', 10, 10, 'En espèces', 189, 1),
(15, '2022-04-17', 685, 5, 'En espèces', 189, 1),
(16, '2022-04-24', 81.99, 3, 'chèque', 189, 1),
(17, '2022-04-24', 81.99, 3, 'chèque', 189, 1),
(25, '2022-04-25', 130.8, 2, 'chèque', 189, 1);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE `commentaire` (
  `Commentaire_Id` int(11) NOT NULL,
  `Contenu` text NOT NULL,
  `Date_Comm` date NOT NULL,
  `Fk_utilisateur_id` int(11) DEFAULT NULL,
  `FK_act` int(11) DEFAULT NULL,
  `FK_Prod` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `doctrine_migration_versions`
--

CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) COLLATE utf8_unicode_ci NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `doctrine_migration_versions`
--

INSERT INTO `doctrine_migration_versions` (`version`, `executed_at`, `execution_time`) VALUES
('DoctrineMigrations\\Version20220430005114', '2022-05-06 21:13:02', 817),
('DoctrineMigrations\\Version20220501101855', '2022-05-06 22:12:31', 405);

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE `panier` (
  `PanierAjout_Id` int(11) NOT NULL,
  `Fk_ProduitP_Id` int(11) DEFAULT NULL,
  `FK_UserClient` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`PanierAjout_Id`, `Fk_ProduitP_Id`, `FK_UserClient`) VALUES
(4, 23, NULL),
(5, 25, NULL),
(6, 23, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `Produit_Id` int(11) NOT NULL,
  `Ref_P` varchar(50) NOT NULL,
  `Prix_Uni` double NOT NULL,
  `Quantite` int(11) NOT NULL,
  `Description` text NOT NULL,
  `Nom_Produit` varchar(50) NOT NULL,
  `Categorie` varchar(50) NOT NULL,
  `Path_Image` varchar(150) DEFAULT '''NULL''',
  `Note` int(11) DEFAULT NULL,
  `totalnote` int(11) DEFAULT NULL,
  `occurence` int(11) DEFAULT NULL,
  `FK_Businees` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`Produit_Id`, `Ref_P`, `Prix_Uni`, `Quantite`, `Description`, `Nom_Produit`, `Categorie`, `Path_Image`, `Note`, `totalnote`, `occurence`, `FK_Businees`) VALUES
(23, 'DDO87', 54, 23, '60 pills to help you lose weight effortlessly.These pills will raise the bar and eveything.', 'HD Supplements', 'supplements & Food', 'ff6d0a6512361aa9b180a46c13755bb3.jpeg', NULL, NULL, NULL, 1),
(25, 'DGUEO87', 9.8, 562, 'Natural, drug-free way to reduce pain and promote faster healing without medication Specially woven design provides breathability and can be worn comfortably for 1 to 3 days', 'KT Tape Recovery Patch', 'fitness', '292e1682437429e080c56eed723dc6d3.jpeg', NULL, NULL, NULL, 1),
(26, 'DGUE79', 49.72, 100, 'SAFE & STABLE WITH LOWER HEIGHT- New model with wider base and heavy duty frame for increased stability and support during cycle. Designed with a lower height to better fit under desks and tables.', 'Vaunn Medical Under Desk Bike Pedal Exerciser', 'equipement', 'bd84db8d40d194f297c5341a2c0d1add.jpeg', NULL, NULL, NULL, 1),
(27, 'DDO898', 7.99, 8, 'PERFECT SEAT FOR ACTIVE KIDS: Stay N Play Balance Ball provides a flexible seat for high energy and active kids and includes 5 soft stabilizing legs assuring the ball stays in place when not in use', 'Gaiam Kids Stay-N-Play', 'equipement', '7bae4c182ad0ae7a1c0c7134834605f7.jpeg', NULL, NULL, NULL, 1),
(28, 'SSPKFP', 1230, 10, '30-Day iFIT Family Membership Included; Stream live & on-demand workouts on your equipment with Global Workouts & Studio Classes; Add up to 5 users; Elite trainers adjust your equipment (39 Dollar value)', 'NordicTrack Cycle', 'equipement', 'ddc4314db11557a847c6b831c4b2df7f.jpeg', NULL, NULL, NULL, 1),
(29, 'YOHP58', 20, 17, '68\" long 24\" wide ensures comfort. With high density eco friendly material, the 1/4\'\' thick premium mat comfortably cushions spine, hips, knees and elbows on hard floors while keeps you balanced', 'BalanceFrom GoYoga', 'equipement', '0302bd4f5ec95cd2d5ed953c98e2d4fc.jpeg', NULL, NULL, NULL, 1),
(30, 'DJEO858', 342.64, 50, 'Two in One  Easily turn two dumbbells into one barbell with connector, and rubber handles for better grip and increased safety during exercises.Its fitness function is not only barbells and dumbbells. When you cooperate with different fitness actions, its role is also equivalent to a push-up board, a grip, a back training device and so on. Just as a home gym.', 'ZENOVA Adjustable Dumbbells', 'Stregth & conditionning', '9e00113521400a5e080b20c14997b659.webp', NULL, NULL, NULL, 1),
(31, 'DJNR898', 6.1, 70, 'LEATHER and SILICONE PADDING for EXTRA GRIP - Say goodbye to calluses, tears and breaking blisters! Made from high quality breathable neoprene fabric and reinforced with split leather and a silicone layer, the PRIM8 ULTIMATE GRIP WORKOUT GLOVES bring you the best palm padding possible', 'Leosportz Gloves', 'Fitness Accessories', '1dd3ef6dd3a9fa4cdc7eadacc83fa215.jpeg', NULL, NULL, NULL, 1),
(32, 'GIUM897', 40.56, 65, 'LIGHTWEIGHT & DURABLE: Speed Jump Rope is steel wire coated in PVC sleeve for excellent durability while ensuring the maximum service life to avoid breaking.', 'TWFO Jump Rope', 'sport', '34bbf9f380f8ac7d5f3bb722d3bc945b.jpeg', NULL, NULL, NULL, 1),
(33, 'FJISI58', 70.56, 80, 'Perfect Home Fitness Device- High Density Fitness Foam Kneepad is included for comfort workout at home. Strengthens and Tones your entire body by hitting your Abs, Core, Torso, Shoulders, Arms, Upper Back & Lower Back.', 'Chhogli  Back Sit', 'fitness', '3c0220727d9d297fa96cd726bbb26642.jpeg', NULL, NULL, NULL, 1),
(34, 'BILQZ98', 29.9, 95, 'Stable and Safe - Hexagon stable design dumbbell head, keep stable without rolling, no matter which side will give you enough sense of security. At the same time, you can also use it as push-up stand, it\'s not just a pair of dumbbells', 'Aurion PVC 1 to 5 Kg', 'Stregth & conditionning', '2d9c2ea958dd4efb2da8bd291bc7d676.jpeg', NULL, NULL, NULL, 1),
(35, 'GLCE874', 38.8, 64, 'Thumb loops with extra length hook and loop to ensure shifting does not occur when in use. Constructed from a heavy Blend cotton-eglantine Polyester for maximum support.', 'Kobo WTA-04', 'Fitness Accessories', '2b4f5ce563a1723ebab67695c424c8e3.jpeg', NULL, NULL, NULL, 1),
(36, 'QIKNR85', 70.5, 7, 'Material / Dimension : Outer Material - Premium leatherette Fabric, Inner Material – Soft Polyester, Size: 43 cm x 23 cm x 23 cm. (L x B x H), Color : Black.', 'AUXTER Gym Bag', 'Fitness Accessories', 'e4a4686bc92d2bef1e9f3494226be5d9.jpeg', NULL, NULL, NULL, 1),
(37, 'FINE99', 43.5, 50, 'Roomy Zip Closure Compartment. 2 side chain pockets. Adjustable Shoulder Strap. Material : Superior Faux Leather. High Quality Zipper.', 'FAXINER Bag', 'Fitness Accessories', '54cc2c9bdaae6837a486752b0847cb44.jpeg', NULL, NULL, NULL, 1),
(38, 'FINE87', 43.5, 88, 'Roomy Zip Closure Compartment. 2 side chain pockets. Adjustable Shoulder Strap. Material : Superior Faux Leather. High Quality Zipper.', 'CRINDS PU Leather Gym Bag', 'Fitness Accessories', '90a7261952b0e283f046380813835de7.jpeg', NULL, NULL, NULL, 1),
(39, 'DGEO74', 70.8, 90, 'Chocolate Slim Shake is enriched with the goodness of 6 ayurvedic herbs. Garcinia, Green Coffee bean, and Green Tea break down excess fats in the body. Cinnamon prevents blood sugar spike, Apple Cider Vinegar boosts metabolism and Boswellia prevents high inflammation.', 'Milford Slim Shake', 'supplements & Food', 'f9cd22af86b86bb6ce86327d4b4ed9ce.jpeg', NULL, NULL, NULL, 1),
(40, 'FINE877', 45.5, 80, 'Stable and Safe - Hexagon stable design dumbbell head, keep stable without rolling, no matter which side will give you enough sense of security. At the same time, you can also use it as push-up stand, it\'s not just a pair of dumbbells', 'Aurion PVC Encase', 'Stregth & conditionning', '767989654b039518d68e9992ca87de90.jpeg', NULL, NULL, NULL, 1),
(41, 'DDO523', 10.6, 42, '100% ORGANIC & VEGAN: Pintola Rice Cake is made with Organic Wholegrain Brown Rice. There is No Added Sugar, No Added Salt or Any kind of Stabilisers. It is Ideal for anyone to comsume', 'Pintola Organic bread', 'supplements & Food', '4b6d689582390501d0c1e145c7092632.jpeg', NULL, NULL, NULL, 1),
(42, 'DDO956', 5.9, 140, '50g packs of millet pancakes – blueberry, classic, choco-chip and chocolate plus dosa mixes – Spinach and Beetroot 100% vegetarian and eggless. The dosas are also vegan!', 'Slurrp Farmpancake', 'supplements & Food', '8bb191be9431084566148a3dbfa65331.jpeg', NULL, NULL, NULL, 1),
(44, 'AQDO566', 60, 60, 'NABL LAB CERTIFIED, DIABETIC FRIENDLY DIET SNACKS FOR WEIGHT LOSS : Keeros Multiseed mix supersnack is Low GI, Gluten Free & Sugar free snack backed by NABL Lab Reports that are Healthy for all & Diabetic friendly too.', 'Keeros Multi Seeds Mix', 'supplements & Food', '8fbf0a311b2bf7a11c2cc43855264b50.jpeg', NULL, NULL, NULL, 1),
(45, 'DDO47', 32.2, 99, 'BAR FOR EVERYONE: With 10gms of protein in each bar and 5gms of fiber and goodness of almond in every bite. Max Protein Choco Almond Bar is the perfect bar for your daily lifestyle with no preservatives and no artificial sweeteners with a balance of taste and health and 100% vegetarian', 'RiteBite  Protein Bar', 'supplements & Food', 'cec2548dafe09daf2bb9b5e9f5dd5305.jpeg', NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `Reclamation_Id` int(11) NOT NULL,
  `Contenu` text NOT NULL,
  `Fkutilisateur` int(11) DEFAULT NULL,
  `FkterrainRecId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`Reclamation_Id`, `Contenu`, `Fkutilisateur`, `FkterrainRecId`) VALUES
(1, 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', 189, 3),
(2, 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttestte', 189, 3),
(50, 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', 189, 3),
(51, 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', 189, 3),
(52, 'testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', 189, 3);

-- --------------------------------------------------------

--
-- Structure de la table `reglement`
--

CREATE TABLE `reglement` (
  `Reglement_Id` int(11) NOT NULL,
  `code` int(11) DEFAULT NULL,
  `FK_Commande` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `Reservation_Id` int(11) NOT NULL,
  `Date_Debut` date NOT NULL,
  `Date_Fin` date NOT NULL,
  `Duree` varchar(20) NOT NULL,
  `Infos_Supp` text NOT NULL,
  `FK_user_Id` int(11) DEFAULT NULL,
  `FK_Service` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`Reservation_Id`, `Date_Debut`, `Date_Fin`, `Duree`, `Infos_Supp`, `FK_user_Id`, `FK_Service`) VALUES
(1, '2022-02-09', '2022-02-25', 'test', 'test', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE `service` (
  `Service_Id` int(11) NOT NULL,
  `Ref_S` varchar(50) NOT NULL,
  `Type_S` varchar(50) NOT NULL,
  `Nom_Service` varchar(50) NOT NULL,
  `Description` text NOT NULL,
  `Disponibilite` varchar(50) NOT NULL,
  `Horaire` varchar(100) NOT NULL,
  `Categorie` varchar(50) NOT NULL,
  `FK_UserC` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `terrain`
--

CREATE TABLE `terrain` (
  `Terrain_Id` int(11) NOT NULL,
  `Nom_Terrain` varchar(30) CHARACTER SET utf8 NOT NULL,
  `Description` text CHARACTER SET utf8 NOT NULL,
  `Activitie` varchar(50) CHARACTER SET utf8 NOT NULL,
  `contact` varchar(50) CHARACTER SET utf8 NOT NULL,
  `prix` varchar(50) CHARACTER SET utf8 NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 NOT NULL,
  `Region` varchar(255) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `terrain`
--

INSERT INTO `terrain` (`Terrain_Id`, `Nom_Terrain`, `Description`, `Activitie`, `contact`, `prix`, `image`, `Region`) VALUES
(3, 'Football Soukra', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', 'Terrain de football', '+216 50 123 123', '7', 'c460b243c457e87d7292cf04f7f1f6bd.jpeg', 'Tunis'),
(4, 'IJA KAWER', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', 'Terrain de football', '+216 99 099 000', '6', 'c460b243c457e87d7292cf04f7f1f6bd.jpeg', 'Tunis'),
(13, 'MISE EN FORME', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', 'Terrain de football', '+216 99 099 099', '15', 'de2b19f88026ff73598301458eff3799.jpeg', 'Béja'),
(14, 'FOOT TIME', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', 'Terrain de football', '+216 99 300 400', '7', '8fb10bb6ebf2a1ac77a1ff7a765d960c.jpeg', 'Béja'),
(15, 'RUGBY TIME', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', 'Terrain de rugby', '+216 99 669 966', '8', 'c3c474036ed861bfcd0848ae54ba3ea4.jpeg', ' Béja'),
(16, 'KOORA', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', 'Terrain de football', '+216 99 669 966', '9', '5c11eb914cdf92e8b5a0d250004908fd.jpeg', 'Kef'),
(17, 'GOLF', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', ' Terrain de hockey sur gazon', '+216 99 777 888', '4', '7cf86d200058ea359067fe638ada1241.jpeg', 'Touzeur'),
(18, 'WKYET GOLF', 'Profitez des infrastructures modernes d’un terrain à la Soukra pour pratiquer votre grande passion : le football. Jouez entre amis, organisez des tournois en toute sécurité et allez droit au but !\r\n✔️Durée 1H15.\r\n✔️Guide (français).\r\n✔️Veuillez échanger vos bons de confirmation.\r\n✔️Les bons de confirmation imprimés ou sur mobile sont acceptés.\r\n✔️Groupe de 12 personnes.\r\n✔️Annulation gratuite jusqu’à 72 heures avant le début de l’activité.\r\n✔️Activité soumise aux conditions météorologiques. En cas de situation atmosphérique inadéquate, l\'activité sera reportée ou remboursée par mesure de sécurité.', ' Terrain de hockey sur gazon', '+216 99 777 888', '4', '2fa4df2dd7ba3311ad627c45bab5485d.jpeg', 'Béja'),
(19, 'NBA', 'La dimension du terrain de basket est de 28 mètres de long par 15 mètres de large mais les terrains de 26 mètres par 14 peuvent aussi être homologués. Il est important de préciser que les dimensions évoluent. Le règlement et les normes changent en fonction de l\'évolution, voulue ou constatée, du jeu. Ainsi la ligne de tir à trois points a reculé de 6,25 m à 6,75 m dans le règlement de la FIBA. Elle est a 7.23 m en NBA.', ' Terrain de basket-ball', '+216 53 445 554', '20', '2a705656ad06602a60df21cca80c61f5.jpeg', 'Kébili');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `Utilisateur_Id` int(11) NOT NULL,
  `email` varchar(180) NOT NULL,
  `roles` longtext NOT NULL COMMENT '(DC2Type:json)',
  `password` varchar(255) NOT NULL,
  `activation_token` varchar(50) DEFAULT NULL,
  `reset_token` varchar(50) DEFAULT NULL,
  `num` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`Utilisateur_Id`, `email`, `roles`, `password`, `activation_token`, `reset_token`, `num`) VALUES
(161, 'amaltr249@gmail.com', '[\"ROLE_ADMIN\"]', '$argon2id$v=19$m=65536,t=4,p=1$S0JtMWp0bDBIR0lrVkhoaw$KM6TncfxWw4FP8id6oWBoWkjg0+ffDHjjkum85j6Zug', NULL, NULL, 50122990),
(186, 'amaltr210@gmail.com', '[\"ROLE_USER\"]', '$argon2id$v=19$m=65536,t=4,p=1$SkxQVC41RFRFd2xsUzByVg$gR9jPU4xrGl4ZeRAEyNurbXOIKRXeSVAhLR9fj+rFQo', 'ca1e4ce8092d23c0ac3e702911f57556', NULL, 50122990),
(189, 'amaltr21@gmail.com', '[\"ROLE_USER\"]', '$argon2id$v=19$m=65536,t=4,p=1$eGprd1RYSmw5d2sySUI0Rw$2O2sSY5PK5NLA1k1F3oOCncxKlZvKNqA9nmx/eZgLI8', NULL, '3W9-043EyU_ruGLZuflGgADZgV9nVS4eH81w1JTBxzk', 50122990),
(190, 'amal.trabelsi@esprit.tn', '[\"ROLE_VENDEUR\"]', '$argon2id$v=19$m=65536,t=4,p=1$Zkg5MTYvUjE3Wk96SlpabA$kkxq8c2oip+rb8+ZGVYa2oDD4ksy/J1JezlA9+wVtX4', 'cff9808d9eeb963821e3b8573bbb12f0', NULL, 50122990);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `actualite`
--
ALTER TABLE `actualite`
  ADD PRIMARY KEY (`Actualite_Id`),
  ADD KEY `FK_UserC_Id` (`FK_user_Id`);

--
-- Index pour la table `business`
--
ALTER TABLE `business`
  ADD PRIMARY KEY (`Business_Id`),
  ADD KEY `CompteHer_Id` (`User_Id`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`Commande_Id`),
  ADD KEY `FK_Panier_Id` (`FK_ClientPan_Id`);

--
-- Index pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`Commentaire_Id`),
  ADD KEY `Fk_utilisateurCom_id` (`Fk_utilisateur_id`),
  ADD KEY `FK_Actu` (`FK_act`),
  ADD KEY `FKproduit` (`FK_Prod`);

--
-- Index pour la table `doctrine_migration_versions`
--
ALTER TABLE `doctrine_migration_versions`
  ADD PRIMARY KEY (`version`);

--
-- Index pour la table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`PanierAjout_Id`),
  ADD KEY `Fk_ProduitP_Id` (`Fk_ProduitP_Id`),
  ADD KEY `FK_userClient` (`FK_UserClient`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`Produit_Id`),
  ADD KEY `FK_businessPp` (`FK_Businees`);

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`Reclamation_Id`),
  ADD KEY `Fk_utilisateurRec_Id` (`Fkutilisateur`),
  ADD KEY `FkterrainRecId` (`FkterrainRecId`);

--
-- Index pour la table `reglement`
--
ALTER TABLE `reglement`
  ADD PRIMARY KEY (`Reglement_Id`),
  ADD KEY `FK_commande` (`FK_Commande`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`Reservation_Id`),
  ADD KEY `FK_ClientUser_Id` (`FK_user_Id`),
  ADD KEY `FKservice` (`FK_Service`);

--
-- Index pour la table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`Service_Id`),
  ADD KEY `FK_userC` (`FK_UserC`);

--
-- Index pour la table `terrain`
--
ALTER TABLE `terrain`
  ADD PRIMARY KEY (`Terrain_Id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`Utilisateur_Id`),
  ADD UNIQUE KEY `UNIQ_1D1C63B3E7927C74` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `actualite`
--
ALTER TABLE `actualite`
  MODIFY `Actualite_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `business`
--
ALTER TABLE `business`
  MODIFY `Business_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `Commande_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pour la table `commentaire`
--
ALTER TABLE `commentaire`
  MODIFY `Commentaire_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `panier`
--
ALTER TABLE `panier`
  MODIFY `PanierAjout_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `Produit_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `Reclamation_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT pour la table `reglement`
--
ALTER TABLE `reglement`
  MODIFY `Reglement_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `Reservation_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `service`
--
ALTER TABLE `service`
  MODIFY `Service_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `terrain`
--
ALTER TABLE `terrain`
  MODIFY `Terrain_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `Utilisateur_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=191;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `actualite`
--
ALTER TABLE `actualite`
  ADD CONSTRAINT `FK_UserC_Id` FOREIGN KEY (`FK_user_Id`) REFERENCES `utilisateur` (`Utilisateur_Id`);

--
-- Contraintes pour la table `business`
--
ALTER TABLE `business`
  ADD CONSTRAINT `FK_userid` FOREIGN KEY (`User_Id`) REFERENCES `utilisateur` (`Utilisateur_Id`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FK_Panier_Id` FOREIGN KEY (`FK_ClientPan_Id`) REFERENCES `utilisateur` (`Utilisateur_Id`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK_Actu` FOREIGN KEY (`FK_act`) REFERENCES `actualite` (`Actualite_Id`),
  ADD CONSTRAINT `FKproduit` FOREIGN KEY (`FK_Prod`) REFERENCES `produit` (`Produit_Id`),
  ADD CONSTRAINT `Fk_utilisateurCom_id` FOREIGN KEY (`Fk_utilisateur_id`) REFERENCES `utilisateur` (`Utilisateur_Id`);

--
-- Contraintes pour la table `panier`
--
ALTER TABLE `panier`
  ADD CONSTRAINT `FK_ProduitP_Id` FOREIGN KEY (`Fk_ProduitP_Id`) REFERENCES `produit` (`Produit_Id`),
  ADD CONSTRAINT `FK_userClient` FOREIGN KEY (`FK_UserClient`) REFERENCES `utilisateur` (`Utilisateur_Id`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FK_businessPp` FOREIGN KEY (`FK_Businees`) REFERENCES `business` (`Business_Id`);

--
-- Contraintes pour la table `reglement`
--
ALTER TABLE `reglement`
  ADD CONSTRAINT `FK_commande` FOREIGN KEY (`FK_Commande`) REFERENCES `commande` (`Commande_Id`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK_ClientUser_Id` FOREIGN KEY (`FK_user_Id`) REFERENCES `utilisateur` (`Utilisateur_Id`),
  ADD CONSTRAINT `FKservice` FOREIGN KEY (`FK_Service`) REFERENCES `service` (`Service_Id`);

--
-- Contraintes pour la table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `FK_userC` FOREIGN KEY (`FK_UserC`) REFERENCES `utilisateur` (`Utilisateur_Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
