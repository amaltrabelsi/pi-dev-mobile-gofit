<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Abonnementbus
 *
 * @ORM\Table(name="abonnementbus", indexes={@ORM\Index(name="FK_business", columns={"FK_business"}), @ORM\Index(name="FK_user", columns={"FK_user"})})
 * @ORM\Entity
 */
class Abonnementbus
{
    /**
     * @var int
     *
     * @ORM\Column(name="AbonnementBus_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $abonnementbusId;

    /**
     * @var \Business
     *
     * @ORM\ManyToOne(targetEntity="Business")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_business", referencedColumnName="Business_Id")
     * })
     */
    private $fkBusiness;

    /**
     * @var \Utilisateur
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_user", referencedColumnName="Utilisateur_Id")
     * })
     */
    private $fkUser;


}
