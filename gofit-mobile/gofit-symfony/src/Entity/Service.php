<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Service
 *
 * @ORM\Table(name="service", indexes={@ORM\Index(name="FK_userC", columns={"FK_UserC"})})
 * @ORM\Entity
 */
class Service
{
    /**
     * @var int
     *
     * @ORM\Column(name="Service_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $serviceId;

    /**
     * @var string
     *
     * @ORM\Column(name="Ref_S", type="string", length=50, nullable=false)
     */
    private $refS;

    /**
     * @var string
     *
     * @ORM\Column(name="Type_S", type="string", length=50, nullable=false)
     */
    private $typeS;

    /**
     * @var string
     *
     * @ORM\Column(name="Nom_Service", type="string", length=50, nullable=false)
     */
    private $nomService;

    /**
     * @var string
     *
     * @ORM\Column(name="Description", type="text", length=65535, nullable=false)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="Disponibilite", type="string", length=50, nullable=false)
     */
    private $disponibilite;

    /**
     * @var string
     *
     * @ORM\Column(name="Horaire", type="string", length=100, nullable=false)
     */
    private $horaire;

    /**
     * @var string
     *
     * @ORM\Column(name="Categorie", type="string", length=50, nullable=false)
     */
    private $categorie;

    /**
     * @var \Utilisateur
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_UserC", referencedColumnName="Utilisateur_Id")
     * })
     */
    private $fkUserc;


}
