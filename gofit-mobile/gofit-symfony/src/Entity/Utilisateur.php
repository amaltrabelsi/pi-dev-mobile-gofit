<?php

namespace App\Entity;

use JsonSerializable;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass="App\Repository\UtilisateurRepository")
 * @UniqueEntity(fields={"email"}, message="There is already an account with this email")
 */
class Utilisateur implements JsonSerializable, UserInterface
{
    /**
     * @var int
     *
     * @ORM\Column(name="Utilisateur_Id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $utilisateurId;

    /**
     * @ORM\Column(type="string", length=180, unique=true)
     * @Groups("post:read")
     */
    private $email;

    /**
     * @ORM\Column(type="json")
     * @Groups("post:read")
     */
    private $roles = [];

    /**
     * @var string The hashed password
     * @ORM\Column(type="string")
     * @Groups("post:read")
     */
    private $password;


    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $activation_token;

    /**
     * @ORM\Column(type="string")
     * @Groups("post:read")
     */
    private $num;

    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $reset_token;

    protected $captchaCode;

    public function getCaptchaCode()
    {
        return $this->captchaCode;
    }

    public function setCaptchaCode($captchaCode)
    {
        $this->captchaCode = $captchaCode;
    }


    public function getUtilisateurId(): int
    {
        return $this->utilisateurId;
    }

    public function setUtilisateurId(int $utilisateurId): Utilisateur
    {
        $this->utilisateurId = $utilisateurId;
        return $this;
    }


    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    /**
     * @see UserInterface
     */
    public function getRoles(): array
    {
        $roles = $this->roles;
        // guarantee every user at least has ROLE_USER
        $roles[] = 'ROLE_USER';

        return array_unique($roles);
    }

    public function setRoles(array $roles): self
    {
        $this->roles = $roles;

        return $this;
    }

    /**
     * @see UserInterface
     */
    public function getPassword(): string
    {
        return (string)$this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    /**
     * @see UserInterface
     */
    public function getSalt()
    {
        // not needed when using the "bcrypt" algorithm in security.yaml
    }

    /**
     * @see UserInterface
     */
    public function eraseCredentials()
    {
        // If you store any temporary, sensitive data on the user, clear it here
        // $this->plainPassword = null;
    }

    public function getactivation_token(): ?string
    {
        return $this->activation_token;
    }

    public function setactivationToken($activation_token): self
    {
        $this->activation_token = $activation_token;

        return $this;
    }


    public function getNum(): ?int
    {
        return $this->num;
    }

    public function setNum(int $num): self
    {
        $this->num = $num;

        return $this;
    }

    public function getUsername()
    {
        // TODO: Implement getUsername() method.
    }

    public function getResetToken(): ?string
    {
        return $this->reset_token;
    }

    public function setResetToken(?string $reset_token): self
    {
        $this->reset_token = $reset_token;

        return $this;
    }

    public function __toString()
    {
        return (string)$this->getUtilisateurId();
    }

    public function jsonSerialize(): array
    {
        $roles = "";
        if ($this->roles != null ){
            if ($this->roles[0]){
                $roles = $this->roles[0];
            }
        }
        return array(
            'id' => $this->utilisateurId,
            'email' => $this->email,
            'role' => $roles,
            'password' => $this->password,
            'num' => $this->num

        );
    }

    public function setUp($email, $role, $password, $num)
    {
        $this->email = $email;
        $this->roles = [$role];
        $this->password = $password;
        $this->num = $num;

    }

}

