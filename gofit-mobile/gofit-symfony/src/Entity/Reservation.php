<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reservation
 *
 * @ORM\Table(name="reservation", indexes={@ORM\Index(name="FKservice", columns={"FK_Service"}), @ORM\Index(name="FK_ClientUser_Id", columns={"FK_user_Id"})})
 * @ORM\Entity
 */
class Reservation
{
    /**
     * @var int
     *
     * @ORM\Column(name="Reservation_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $reservationId;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="Date_Debut", type="date", nullable=false)
     */
    private $dateDebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="Date_Fin", type="date", nullable=false)
     */
    private $dateFin;

    /**
     * @var string
     *
     * @ORM\Column(name="Duree", type="string", length=20, nullable=false)
     */
    private $duree;

    /**
     * @var string
     *
     * @ORM\Column(name="Infos_Supp", type="text", length=65535, nullable=false)
     */
    private $infosSupp;

    /**
     * @var \Utilisateur
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_user_Id", referencedColumnName="Utilisateur_Id")
     * })
     */
    private $fkUser;

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
