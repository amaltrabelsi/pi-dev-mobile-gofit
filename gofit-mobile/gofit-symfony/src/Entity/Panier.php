<?php

namespace App\Entity;
use App\Repository\PanierRepository;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Utilisateur;
use App\Entity\Produit;
use JsonSerializable;


/**
 * Panier
 *
 * @ORM\Table(name="panier", indexes={@ORM\Index(name="Fk_ProduitP_Id", columns={"Fk_ProduitP_Id"}), @ORM\Index(name="FK_userClient", columns={"FK_UserClient"})})
 * @ORM\Entity(repositoryClass=PanierRepository::class)
 */
class Panier implements JsonSerializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="PanierAjout_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $panierajoutId;

    /**
     * @var \Utilisateur
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_UserClient", referencedColumnName="Utilisateur_Id")
     * })
     */
    private $fkUserclient;

    /**
     * @var \Produit
     *
     * @ORM\ManyToOne(targetEntity="Produit")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="Fk_ProduitP_Id", referencedColumnName="Produit_Id")
     * })
     */
    private $fkProduitp;

    /**
     * @return int
     */
    public function getPanierajoutId(): int
    {
        return $this->panierajoutId;
    }

    /**
     * @param int $panierajoutId
     */
    public function setPanierajoutId(int $panierajoutId): void
    {
        $this->panierajoutId = $panierajoutId;
    }

    /**
     * @return \Utilisateur|null
     */
    public function getFkUserclient(): ?Utilisateur
    {
        return $this->fkUserclient;
    }



    public function setFkUserclient(?Utilisateur $fkUserclient): self
    {
        $this->fkUserclient = $fkUserclient;
        return $this;
    }
    /**
     * @return \Produit|null
     */
    public function getFkProduitp(): ?Produit
    {
        return $this->fkProduitp;
    }


    public function setFkProduitp(?Produit $fkProduitp): self
    {
        $this->fkProduitp = $fkProduitp;
        return $this;
    }

    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->panierajoutId,
            'utilisateur' => $this->fkUserclient,
            'produit' => $this->fkProduitp

        );
    }

    public function setUp($utilisateur, $produit)
    {
        $this->fkUserclient = $utilisateur;
        $this->fkProduitp = $produit;

    }


}
