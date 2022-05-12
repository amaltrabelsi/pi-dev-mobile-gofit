<?php

namespace App\Entity;
use JsonSerializable;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;


/**
 * Reclamation
 *
 * @ORM\Table(name="reclamation", indexes={@ORM\Index(name="Fk_terrainRec_Id", columns={"FkterrainRecId"}), @ORM\Index(name="Fk_utilisateurRec_Id", columns={"Fkutilisateur"})})
 * @ORM\Entity
 */
class Reclamation implements JsonSerializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="Reclamation_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("post:read")
     */
    private $reclamationId;

    /**
     * @var string
     *
     * @ORM\Column(name="Contenu", type="text", length=65535, nullable=false)
     * @Groups("post:read")
     */
    private $contenu;

    /**
     * @var \Terrain
     *
     * @ORM\ManyToOne(targetEntity="Terrain")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="FkterrainRecId", referencedColumnName="Terrain_Id")
     * })
     * @Groups("post:read")
     */
    private $fkterrainrecid;

    /**
     * @var \Utilisateur
     *
     * @ORM\ManyToOne(targetEntity="Utilisateur")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="Fkutilisateur", referencedColumnName="Utilisateur_Id")
     * })
     * @Groups("post:read")
     */
    private $fkutilisateur;
    /**
     * @return int
     */
    public function getReclamationId(): int
    {
        return $this->reclamationId;
    }

    /**
     * @param int $reclamationId
     * @return Reclamation
     */
    public function setReclamationId(int $reclamationId): Reclamation
    {
        $this->reclamationId = $reclamationId;
        return $this;
    }


    /**
     * @return string
     */
    public function getContenu(): ? string
    {
        return $this->contenu;
    }

    /**
     * @param string $contenu
     * @return Reclamation
     */
    public function setContenu(string $contenu): self
    {
        $this->contenu = $contenu;
        return $this;
    }



    public function getFkterrainRecId(): ?Terrain
    {
        return $this->fkterrainrecid;
    }

    public function setFkterrainRecId($fkterrainrecid) : self
    {
        $this->fkterrainrecid = $fkterrainrecid;
        return $this;
    }




    public function getFkutilisateur() :?Utilisateur
    {
        return $this->fkutilisateur;
    }

    public function setFkutilisateur( $fkutilisateur) :self
    {
        $this->fkutilisateur = $fkutilisateur;

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->reclamationId,
            'contenu' => $this->contenu,
            'terrain' => $this->fkterrainrecid,
            'utilisateur' => $this->fkutilisateur
        );
    }

    public function setUp($contenu, $terrain, $utilisateur)
    {
        $this->contenu = $contenu;
        $this->fkterrainrecid = $terrain;
        $this->fkutilisateur = $utilisateur;

    }


}
