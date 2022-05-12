<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220512203454 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE abonnementbus (AbonnementBus_Id INT AUTO_INCREMENT NOT NULL, FK_business INT DEFAULT NULL, FK_user INT DEFAULT NULL, INDEX FK_business (FK_business), INDEX FK_user (FK_user), PRIMARY KEY(AbonnementBus_Id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE avis (Avis_Id INT AUTO_INCREMENT NOT NULL, Valeur VARCHAR(50) NOT NULL, FK_Produit_Id INT DEFAULT NULL, Fk_utilisateur_id INT DEFAULT NULL, FK_Service INT DEFAULT NULL, INDEX FK_Service (FK_Service), INDEX Fk_utilisateurAvis_id (Fk_utilisateur_id), INDEX FK_Produit_Id (FK_Produit_Id), PRIMARY KEY(Avis_Id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE abonnementbus ADD CONSTRAINT FK_B8C0EA4AF33AA1E1 FOREIGN KEY (FK_business) REFERENCES business (Business_Id)');
        $this->addSql('ALTER TABLE abonnementbus ADD CONSTRAINT FK_B8C0EA4AC9BC34DD FOREIGN KEY (FK_user) REFERENCES utilisateur (Utilisateur_Id)');
        $this->addSql('ALTER TABLE avis ADD CONSTRAINT FK_8F91ABF08ECC0317 FOREIGN KEY (FK_Produit_Id) REFERENCES produit (Produit_Id)');
        $this->addSql('ALTER TABLE avis ADD CONSTRAINT FK_8F91ABF0A0253A5C FOREIGN KEY (Fk_utilisateur_id) REFERENCES utilisateur (Utilisateur_Id)');
        $this->addSql('ALTER TABLE avis ADD CONSTRAINT FK_8F91ABF013A31F11 FOREIGN KEY (FK_Service) REFERENCES service (Service_Id)');
        $this->addSql('ALTER TABLE actualite CHANGE Titre Titre VARCHAR(191) NOT NULL, CHANGE Description Description VARCHAR(191) NOT NULL, CHANGE Contenu Contenu TINYTEXT NOT NULL, CHANGE Categorie Categorie VARCHAR(191) NOT NULL, CHANGE Path_Image Path_Image VARCHAR(191) DEFAULT \'NULL\'');
        $this->addSql('ALTER TABLE business CHANGE Nom_Business Nom_Business VARCHAR(191) NOT NULL, CHANGE Nom_Gerant Nom_Gerant VARCHAR(191) NOT NULL, CHANGE Prenom_Gerant Prenom_Gerant VARCHAR(191) NOT NULL, CHANGE Region Region VARCHAR(191) NOT NULL, CHANGE Adresse Adresse VARCHAR(191) NOT NULL, CHANGE Description Description TINYTEXT NOT NULL, CHANGE Tel_Portable Tel_Portable VARCHAR(191) NOT NULL, CHANGE Tel_Fix Tel_Fix VARCHAR(191) NOT NULL, CHANGE Path_Image Path_Image TINYTEXT NOT NULL');
        $this->addSql('ALTER TABLE commande CHANGE Mode_Paiement Mode_Paiement VARCHAR(191) NOT NULL');
        $this->addSql('ALTER TABLE produit CHANGE Ref_P Ref_P VARCHAR(191) NOT NULL, CHANGE Description Description TINYTEXT NOT NULL, CHANGE Nom_Produit Nom_Produit VARCHAR(191) NOT NULL, CHANGE Categorie Categorie VARCHAR(191) NOT NULL, CHANGE Path_Image Path_Image VARCHAR(191) DEFAULT \'NULL\'');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT FK_CE606404F493E6AD FOREIGN KEY (FkterrainRecId) REFERENCES terrain (Terrain_Id)');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT FK_CE6064043D4712AD FOREIGN KEY (Fkutilisateur) REFERENCES utilisateur (Utilisateur_Id)');
        $this->addSql('DROP INDEX fkterrainrecid ON reclamation');
        $this->addSql('CREATE INDEX Fk_terrainRec_Id ON reclamation (FkterrainRecId)');
        $this->addSql('ALTER TABLE terrain CHANGE Nom_Terrain Nom_Terrain VARCHAR(30) NOT NULL, CHANGE Description Description TEXT NOT NULL, CHANGE Activitie Activitie VARCHAR(50) NOT NULL, CHANGE contact contact VARCHAR(50) NOT NULL, CHANGE prix prix VARCHAR(50) NOT NULL, CHANGE image image VARCHAR(255) NOT NULL, CHANGE Region Region VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE utilisateur CHANGE num num VARCHAR(255) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE abonnementbus');
        $this->addSql('DROP TABLE avis');
        $this->addSql('ALTER TABLE actualite CHANGE Titre Titre VARCHAR(50) NOT NULL, CHANGE Description Description VARCHAR(200) NOT NULL, CHANGE Contenu Contenu TEXT NOT NULL, CHANGE Categorie Categorie VARCHAR(50) NOT NULL, CHANGE Path_Image Path_Image VARCHAR(450) DEFAULT \'\'\'NULL\'\'\'');
        $this->addSql('ALTER TABLE business CHANGE Nom_Business Nom_Business VARCHAR(30) NOT NULL, CHANGE Nom_Gerant Nom_Gerant VARCHAR(30) NOT NULL, CHANGE Prenom_Gerant Prenom_Gerant VARCHAR(30) NOT NULL, CHANGE Region Region VARCHAR(30) NOT NULL, CHANGE Adresse Adresse VARCHAR(100) NOT NULL, CHANGE Description Description TEXT NOT NULL, CHANGE Tel_Portable Tel_Portable VARCHAR(10) NOT NULL, CHANGE Tel_Fix Tel_Fix VARCHAR(10) DEFAULT \'\'\'NULL\'\'\', CHANGE Path_Image Path_Image TEXT NOT NULL');
        $this->addSql('ALTER TABLE commande CHANGE Mode_Paiement Mode_Paiement VARCHAR(50) NOT NULL');
        $this->addSql('ALTER TABLE produit CHANGE Ref_P Ref_P VARCHAR(50) NOT NULL, CHANGE Description Description TEXT NOT NULL, CHANGE Nom_Produit Nom_Produit VARCHAR(50) NOT NULL, CHANGE Categorie Categorie VARCHAR(50) NOT NULL, CHANGE Path_Image Path_Image VARCHAR(150) DEFAULT \'\'\'NULL\'\'\'');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY FK_CE606404F493E6AD');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY FK_CE6064043D4712AD');
        $this->addSql('ALTER TABLE reclamation DROP FOREIGN KEY FK_CE606404F493E6AD');
        $this->addSql('DROP INDEX fk_terrainrec_id ON reclamation');
        $this->addSql('CREATE INDEX FkterrainRecId ON reclamation (FkterrainRecId)');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT FK_CE606404F493E6AD FOREIGN KEY (FkterrainRecId) REFERENCES terrain (Terrain_Id)');
        $this->addSql('ALTER TABLE terrain CHANGE Nom_Terrain Nom_Terrain VARCHAR(30) CHARACTER SET utf8 NOT NULL COLLATE `utf8_general_ci`, CHANGE Description Description TEXT CHARACTER SET utf8 NOT NULL COLLATE `utf8_general_ci`, CHANGE Activitie Activitie VARCHAR(50) CHARACTER SET utf8 NOT NULL COLLATE `utf8_general_ci`, CHANGE contact contact VARCHAR(50) CHARACTER SET utf8 NOT NULL COLLATE `utf8_general_ci`, CHANGE prix prix VARCHAR(50) CHARACTER SET utf8 NOT NULL COLLATE `utf8_general_ci`, CHANGE image image VARCHAR(255) CHARACTER SET utf8 NOT NULL COLLATE `utf8_general_ci`, CHANGE Region Region VARCHAR(255) CHARACTER SET utf8 NOT NULL COLLATE `utf8_general_ci`');
        $this->addSql('ALTER TABLE utilisateur CHANGE num num INT NOT NULL');
    }
}
