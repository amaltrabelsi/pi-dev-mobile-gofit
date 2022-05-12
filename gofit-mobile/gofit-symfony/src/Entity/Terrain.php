<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use JsonSerializable;

/**
 * Terrain
 *
 * @ORM\Table(name="terrain")
 * @ORM\Entity
 */
class Terrain implements JsonSerializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="Terrain_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $terrainId;

    /**
     * @var string
     *
     * @ORM\Column(name="Nom_Terrain", type="string", length=30, nullable=false)
     */
    private $nomTerrain;

    /**
     * @var string
     *
     * @ORM\Column(name="Description", type="text", length=65535, nullable=false)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="Activitie", type="string", length=50, nullable=false)
     */
    private $activitie;

    /**
     * @var string
     *
     * @ORM\Column(name="contact", type="string", length=50, nullable=false)
     */
    private $contact;

    /**
     * @var string
     *
     * @ORM\Column(name="prix", type="string", length=50, nullable=false)
     */
    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="image", type="string", length=255, nullable=false)
     */
    private $image;

    /**
     * @var string
     *
     * @ORM\Column(name="Region", type="string", length=255, nullable=false)
     */
    private $region;


    public function getTerrainId(): ?int
    {
        return $this->terrainId;
    }


    public function setTerrainId(int $terrainId): Self
    {
        $this->terrainId = $terrainId;
        return $this;
    }

    /**
     * @return string
     */
    public function getNomTerrain(): ?string
    {
        return $this->nomTerrain;
    }

    /**
     * @param string $nomTerrain
     * @return Terrain
     */
    public function setNomTerrain(string $nomTerrain): Self
    {
        $this->nomTerrain = $nomTerrain;
        return $this;
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
     * @return Terrain
     *
     */
    public function setDescription(string $description): Self
    {
        $this->description = $description;
        return $this;
    }

    /**
     * @return string
     */
    public function getActivitie(): ?string
    {
        return $this->activitie;
    }

    /**
     * @param string $activitie
     * @return Terrain
     */
    public function setActivitie(string $activitie): Self
    {
        $this->activitie = $activitie;
        return $this;
    }

    /**
     * @return string
     */
    public function getContact(): ?string
    {
        return $this->contact;
    }

    /**
     * @param string $contact
     * @return Terrain
     */
    public function setContact(string $contact): Self
    {
        $this->contact = $contact;
        return $this;
    }

    /**
     *
     * @return string
     */
    public function getPrix(): ?string
    {
        return $this->prix;
    }

    /**
     * @param string $prix
     * @return Terrain
     */
    public function setPrix(string $prix): Self
    {
        $this->prix = $prix;
        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage($image)
    {
        $this->image =$image;
        return $this;
    }



    /**
     * @return string
     */
    public function getRegion(): ?string
    {
        return $this->region;
    }

    /**
     * @param string $region
     * @return Terrain
     */
    public function setRegion(string $region): Self
    {
        $this->region = $region;
        return $this;
    }

    public function __toString()
    {
        return(string)$this->getTerrainId();
    }

    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->terrainId,
            'nom' => $this->nomTerrain,
            'description' => $this->description,
            'activitie' => $this->activitie,
            'contact' => $this->contact,
            'prix' => $this->prix,
            'image' => $this->image,
            'region' => $this->region

        );
    }

    public function setUp($nom, $description, $activitie, $contact, $prix, $image, $region)
    {
        $this->nomTerrain = $nom;
        $this->description = $description;
        $this->activitie = $activitie;
        $this->contact = $contact;
        $this->prix = $prix;
        $this->image = $image;
        $this->region = $region;

    }


}
