<?php

namespace App\Entity;
use App\Repository\ProduitRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use JsonSerializable;

/**
 * Produit
 *
 * @ORM\Table(name="produit", indexes={@ORM\Index(name="FK_businessPp", columns={"FK_Businees"})})
 * @ORM\Entity(repositoryClass=ProduitRepository::class)

 */
class Produit implements JsonSerializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="Produit_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $produitId;

    /**
     * @Assert\NotBlank(message=" La refernce doit etre non vide")
     * @Assert\Length(
     *      min = 5,
     *      minMessage=" Entrer une reference au mini de 5 caracteres"
     *
     *     )
     * @ORM\Column(name="Ref_P", type="string", length=191, nullable=false)
     */
    private $refP;

    /**
     * @var float
     *
     * @ORM\Column(name="Prix_Uni", type="float", precision=10, scale=0, nullable=false)
     */
    private $prixUni;

    /**
     * @var int
     *
     * @ORM\Column(name="Quantite", type="integer", nullable=false)
     */
    private $quantite;
    /**
     * @Assert\NotBlank(message="description  doit etre non vide")
     * @Assert\Length(
     *      min = 7,
     *      max =5000,
     *      minMessage = "doit etre >=7 ",
     *      maxMessage = "doit etre <=5000" )
     * @ORM\Column(name="Description", type="text", length=191, nullable=false)
     */

    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="Nom_Produit", type="string", length=191, nullable=false)
     */
    private $nomProduit;

    /**
     * @var string
     *
     * @ORM\Column(name="Categorie", type="string", length=191, nullable=false)
     */
    private $categorie;

    /**
     * @var string
     *
     * @ORM\Column(name="Path_Image", type="string", length=191, nullable=true, options={"default"="NULL"})
     */
    private $pathImage;

    /**
     * @var int|null
     *
     * @ORM\Column(name="Note", type="integer", nullable=true, options={"default"="NULL"})
     */
    private $note = NULL;

    /**
     * @var int|null
     *
     * @ORM\Column(name="totalnote", type="integer", nullable=true, options={"default"="NULL"})
     */
    private $totalnote = NULL;

    /**
     * @var int|null
     *
     * @ORM\Column(name="occurence", type="integer", nullable=true, options={"default"="NULL"})
     */
    private $occurence = NULL;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Business", inversedBy="produits")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_Businees", referencedColumnName="Business_Id")
     * })
     */
    private $business;

    public function getBusiness(): ?Business
    {
        return $this->business;
    }

    public function setBusiness(?business $business): self
    {
        $this->business = $business;

        return $this;
    }


    /**
     * @var \Business
     *
     * @ORM\ManyToOne(targetEntity="Business")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_Businees", referencedColumnName="Business_Id")
     * })
     */
    private $fkBusinees;

    /**
     * @return int
     */
    public function getProduitId(): int
    {
        return $this->produitId;
    }

    /**
     * @param int $produitId
     */
    public function setProduitId(int $produitId): void
    {
        $this->produitId = $produitId;
    }

    /**
     * @return string|null
     */
    public function getRefP(): ?string
    {
        return $this->refP;
    }

    /**
     * @param string $refP
     */
    public function setRefP(string $refP): void
    {
        $this->refP = $refP;
    }


    public function getPathImage()
    {
        return $this->pathImage;
    }


    public function setPathImage($pathImage): self
    {
        $this->pathImage = $pathImage;
        return $this;
    }

    /**
     * @return float|null
     */
    public function getPrixUni(): ?float
    {
        return $this->prixUni;
    }

    /**
     * @param float $prixUni
     */
    public function setPrixUni(float $prixUni): void
    {
        $this->prixUni = $prixUni;
    }

    /**
     * @return int|null
     */
    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    /**
     * @param int $quantite
     */
    public function setQuantite(int $quantite): void
    {
        $this->quantite = $quantite;
    }

    /**
     * @return string|null
     */
    public function getDescription(): ?string
    {
        return $this->description;
    }

    /**
     * @param string $description
     */
    public function setDescription(string $description): void
    {
        $this->description = $description;
    }

    /**
     * @return string|null
     */
    public function getNomProduit(): ?string
    {
        return $this->nomProduit;
    }

    /**
     * @param string $nomProduit
     */
    public function setNomProduit(string $nomProduit): void
    {
        $this->nomProduit = $nomProduit;
    }




    /**
     * @return string int|null
     */
    public function getCategorie(): ?string
    {
        return $this->categorie;
    }

    /**
     * @param string $categorie
     */
    public function setCategorie(string $categorie): void
    {
        $this->categorie = $categorie;
    }


    /**
     * @return int|null
     */
    public function getNote(): ?int
    {
        return $this->note;
    }

    /**
     * @param int|null $note
     */
    public function setNote(?int $note): void
    {
        $this->note = $note;
    }

    /**
     * @return int|null
     */
    public function getTotalnote(): ?int
    {
        return $this->totalnote;
    }

    /**
     * @param int|null $totalnote
     */
    public function setTotalnote(?int $totalnote): void
    {
        $this->totalnote = $totalnote;
    }

    /**
     * @return int|null
     */
    public function getOccurence(): ?int
    {
        return $this->occurence;
    }

    /**
     * @param int|null $occurence
     */
    public function setOccurence(?int $occurence): void
    {
        $this->occurence = $occurence;
    }

    /**
     * @return \Business|null
     */
    public function getFkBusinees(): ?Business
    {
        return $this->fkBusinees;
    }


    public function setFkBusinees(?Business $fkBusinees): self
    {
        $this->fkBusinees = $fkBusinees;
        return $this;
    }

    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->produitId,
            'refP' => $this->refP,
            'prixUni' => $this->prixUni,
            'quantite' => $this->quantite,
            'description' => $this->description,
            'nomProduit' => $this->nomProduit,
            'categorie' => $this->categorie,
            'image' => $this->pathImage,
            'note' => $this->note,
            'totalnote' => $this->totalnote,
            'occurence' => $this->occurence,
            'business' => $this->fkBusinees

        );
    }

    public function setUp($refP, $prixUni, $quantite, $description, $nomProduit, $categorie, $image, $note, $totalnote, $occurence, $business)
    {
        $this->refP = $refP;
        $this->prixUni = $prixUni;
        $this->quantite = $quantite;
        $this->description = $description;
        $this->nomProduit = $nomProduit;
        $this->categorie = $categorie;
        $this->pathImage = $image;
        $this->note = $note;
        $this->totalnote = $totalnote;
        $this->occurence = $occurence;
        $this->fkBusinees = $business;

    }
}
