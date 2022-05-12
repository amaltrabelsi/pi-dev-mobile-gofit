<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Commentaire
 *
 * @ORM\Table(name="commentaire", indexes={@ORM\Index(name="Fk_utilisateurCom_id", columns={"Fk_utilisateur_id"}), @ORM\Index(name="FK_Actu", columns={"FK_act"}), @ORM\Index(name="FKproduit", columns={"FK_Prod"})})
 * @ORM\Entity @ORM\Entity(repositoryClass="App\Repository\CommentaireRepository")
 */
class Commentaire
{
    /**
     * @var int
     *
     * @ORM\Column(name="Commentaire_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $commentaireId;

    /**
     * @var string
     *
     * @ORM\Column(name="Contenu", type="text", length=65535, nullable=false)
     */
    private $contenu;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="Date_Comm", type="date", nullable=false)
     */
    private $dateComm;

    /**
     * @var \Utilisateur
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="Fk_utilisateur_id", referencedColumnName="Utilisateur_Id")
     * })
     */
    private $fkUtilisateur;

    /**
     * @var \Produit
     *
     * @ORM\ManyToOne(targetEntity="Produit")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_Prod", referencedColumnName="Produit_Id")
     * })
     */
    private $fkProd;

    /**
     * @var \Actualite
     *
     * @ORM\ManyToOne(targetEntity="Actualite")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_act", referencedColumnName="Actualite_Id")
     * })
     */
    private $fkAct;


}
