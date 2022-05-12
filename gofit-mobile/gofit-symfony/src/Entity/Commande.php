<?php

namespace App\Entity;

use DateTime;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\NotBlank;
use JsonSerializable;

/**
 * Commande
 *
 * @ORM\Table(name="commande", indexes={@ORM\Index(name="FK_Panier_Id", columns={"FK_ClientPan_Id"})})
 * @ORM\Entity
 */
class Commande implements JsonSerializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="Commande_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $commandeId;

    /**
     * @var DateTime  | NotBlank(message="date doit etre non vide")
     *
     * @ORM\Column(name="Date_C", type="date", nullable=false)
     */
    private $dateC;

    /**
     * @var float  | NotBlank(message="total doit etre non vide")
     *
     * @ORM\Column(name="Total", type="float", precision=10, scale=0, nullable=false)
     */
    private $total;

    /**
     * @var int  | NotBlank(message="nb de produit doit etre non vide")
     *
     * @ORM\Column(name="Nb_Produit", type="integer", nullable=false)
     */
    private $nbProduit;

    /**
     * @var string | NotBlank(message="payment doit etre non vide")
     *
     * @ORM\Column(name="Mode_Paiement", type="string", length=191, nullable=false)
     */
    private $modePaiement;

    /**
     * @var \Utilisateur |
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_ClientPan_Id", referencedColumnName="Utilisateur_Id")
     * })
     */
    private $fkClientpan;

    /**
    *@ORM\Column(type="boolean", options={"default":"0"})
     */
    private $Statut=false;

    /**
     * @return int
     */
    public function getCommandeId(): int
    {
        return $this->commandeId;
    }

    /**
     * @param int $commandeId
     */
    public function setCommandeId(int $commandeId): void
    {
        $this->commandeId = $commandeId;
    }


    /**
     * @return float|null
     */
    public function getTotal(): ?float
    {
        return $this->total;
    }

    /**
     * @param float|null $total
     */
    public function setTotal(?float $total): void
    {
        $this->total = $total;
    }

    /**
     * @return int|null
     */
    public function getNbProduit(): ?int
    {
        return $this->nbProduit;
    }

    /**
     * @param int|null $nbProduit
     */
    public function setNbProduit(?int $nbProduit): void
    {
        $this->nbProduit = $nbProduit;
    }


    public function getModePaiement(): ?string
    {
        return $this->modePaiement;
    }


    public function setModePaiement(?string $modePaiement): void
    {
        $this->modePaiement = $modePaiement;
    }

    /**
     * @return \Utilisateur|null
     */
    public function getFkClientpan(): ?Utilisateur
    {
        return $this->fkClientpan;
    }


    public function setFkClientpan(?Utilisateur $fkClientpan): self
    {
        $this->fkClientpan = $fkClientpan;
        return $this;
    }

    /**
     * @return DateTime|NotBlank
     */
    public function getDateC()
    {
        return $this->dateC;
    }

    /**
     * @param DateTime|NotBlank $dateC
     */
    public function setDateC($dateC): void
    {
        $this->dateC = $dateC;
    }

    public function getStatut(): ?bool
    {
        return $this->Statut;
    }

    public function setStatut(?bool $Statut): self
    {
        $this->Statut = $Statut;

        return $this;
    }


    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->commandeId,
            'dateC' => $this->dateC->format("d-m-Y"),
            'total' => $this->total,
            'nbProduit' => $this->nbProduit,
            'modePaiement' => $this->modePaiement,
            'statut' => $this->Statut ? 1 : 0,
            'utilisateur' => $this->fkClientpan

        );
    }

    public function setUp($dateC, $total, $nbProduit, $modePaiement, $statut, $utilisateur)
    {
        $this->dateC = $dateC;
        $this->total = $total;
        $this->nbProduit = $nbProduit;
        $this->modePaiement = $modePaiement;
        $this->Statut = $statut;
        $this->fkClientpan = $utilisateur;

    }


}
