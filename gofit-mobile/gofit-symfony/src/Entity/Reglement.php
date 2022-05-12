<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reglement
 *
 * @ORM\Table(name="reglement", indexes={@ORM\Index(name="FK_commande", columns={"FK_Commande"})})
 * @ORM\Entity
 */
class Reglement
{
    /**
     * @var int
     *
     * @ORM\Column(name="Reglement_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $reglementId;

    /**
     * @var int|null
     *
     * @ORM\Column(name="code", type="integer", nullable=true)
     */
    private $code;

    /**
     * @var \Commande
     *
     * @ORM\ManyToOne(targetEntity="Commande")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_Commande", referencedColumnName="Commande_Id")
     * })
     */
    private $fkCommande;


}
