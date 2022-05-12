<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Avis
 *
 * @ORM\Table(name="avis", indexes={@ORM\Index(name="FK_Service", columns={"FK_Service"}), @ORM\Index(name="Fk_utilisateurAvis_id", columns={"Fk_utilisateur_id"}), @ORM\Index(name="FK_Produit_Id", columns={"FK_Produit_Id"})})
 * @ORM\Entity
 */
class Avis
{
    /**
     * @var int
     *
     * @ORM\Column(name="Avis_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $avisId;

    /**
     * @var string
     *
     * @ORM\Column(name="Valeur", type="string", length=50, nullable=false)
     */
    private $valeur;

    /**
     * @var \Produit
     *
     * @ORM\ManyToOne(targetEntity="Produit")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_Produit_Id", referencedColumnName="Produit_Id")
     * })
     */
    private $fkProduit;

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
     * @var \Service
     *
     * @ORM\ManyToOne(targetEntity="Service")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_Service", referencedColumnName="Service_Id")
     * })
     */
    private $fkService;


}
