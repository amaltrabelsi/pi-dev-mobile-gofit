<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Vangrg\ProfanityBundle\Validator\Constraints as ProfanityAssert;
use App\Repository\ActualiteRepository;
use JsonSerializable;

/**
 * Actualite
 *
 * @ORM\Table(name="actualite", indexes={@ORM\Index(name="FK_UserC_Id", columns={"FK_user_Id"})})
 * @ORM\Entity
 *
 * @ORM\Entity(repositoryClass=ActualiteRepository::class)
 */

class Actualite implements JsonSerializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="Actualite_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    public $actualiteId;

    /**
     * @var string
     *   @Assert\NotBlank(message="Le titre doit etre non vide!")
     * @Assert\Length(
     *      min = 10,
     *      max = 50,
     *      minMessage = "Veuillez saisir un titre valide >=10",
     *      maxMessage = "Veuillez saisir un titre valide <=50" )
     * @ORM\Column(name="Titre", type="string", length=191, nullable=false)
     */
    public $titre;

    /**
     * @var string
     *   @Assert\NotBlank(message="La description doit etre non vide!")
     * @Assert\Length(
     *      min = 10,
     *      max = 200,
     *      minMessage = "Veuillez saisir une description valide >=10",
     *      maxMessage = "Veuillez saisir une description valide <=200" )
     * @ORM\Column(name="Description", type="string", length=191, nullable=false)
     */
    public $description;

    /**
     * @var string
     *   @Assert\NotBlank(message="Le contenu doit etre non vide!")
     * @Assert\Length(
     *      min = 10,
     *      max = 6500,
     *      minMessage = "Veuillez saisir un contenu valide >=10",
     *      maxMessage = "Veuillez saisir un contenu valide <=6500" )
     * @ORM\Column(name="Contenu", type="text", length=191, nullable=false)
     */
    public $contenu;

    /**
     * @var string
     *
     * @ORM\Column(name="Categorie", type="string", length=191, nullable=false)
     */
    public $categorie;

    /**
     * @var string|null
     *
     * @ORM\Column(name="Path_Image", type="string", length=191, nullable=true, options={"default"="NULL"})
     */
    public $pathImage = 'NULL';

    /**
     * @var \Utilisateur
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FK_user_Id", referencedColumnName="Utilisateur_Id")
     * })
     */
    public $fkUser;
    public function getPathImage(): ?string
    {
        return $this->pathImage;
    }


    public function setPathImage($pathImage): self
    {
        $this->pathImage = $pathImage;
        return $this;
    }
    /**
     * @return int
     */
    public function getActualiteId(): int
    {
        return $this->actualiteId;
    }

    /**
     * @param int $actualiteId
     */
    public function setActualiteId(int $actualiteId): void
    {
        $this->actualiteId= $actualiteId;
    }

    /**
     * @return string
     */
    public function getContenu(): ?string
    {
        return $this->contenu;
    }

    /**
     * @param string $contenu
     */
    public function setContenu(string $contenu): void
    {
        $this->contenu = $contenu;
    }

    /**
     * @return string
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
     * @return string
     */
    public function getTitre(): ?string
    {
        return $this->titre;
    }

    /**
     * @param string $titre
     */
    public function setTitre(string $titre): void
    {
        $this->titre = $titre;
    }

    /**
     * @return string
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
     * @return \Utilisateur
     */
    public function getFkUser(): \Utilisateur
    {
        return $this->fkUser;
    }

    /**
     * @param \Utilisateur $fkUser
     */
    public function setFkUser(\Utilisateur $fkUser): void
    {
        $this->fkUser = $fkUser;
    }

    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->actualiteId,
            'titre' => $this->titre,
            'description' => $this->description,
            'contenu' => $this->contenu,
            'categorie' => $this->categorie,
            'image' => $this->pathImage,
            'utilisateur' => $this->fkUser

        );
    }

    public function setUp($titre, $description, $contenu, $categorie, $image, $utilisateur)
    {
        $this->titre = $titre;
        $this->description = $description;
        $this->contenu = $contenu;
        $this->categorie = $categorie;
        $this->pathImage = $image;
        $this->fkUser = $utilisateur;

    }
}
