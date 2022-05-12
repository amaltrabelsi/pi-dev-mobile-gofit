<?php

namespace App\Repository;

use App\Entity\Commande;

use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;



/**
     * @method Commande|null find($id, $lockMode = null, $lockVersion = null)
     * @method Commande|null findOneBy(array $criteria, array $orderBy = null)
     * @method Commande[]    findAll()
     * @method Commande[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
     */

class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

    public function findCommandsByClient($id)
    {
        return $this->createQueryBuilder('P')
            ->where('P.fkClientpan = :id')
            ->setParameter('id', $id)
            ->getQuery()
            ->getResult();

    }


    public function findByFkClientpan($id)
    {
        return $this->createQueryBuilder('c')
            ->join('c.utilisateur', 'u')
            ->where('c.fkClientpan LIKE :id')
            ->setParameter('id', '%' .$id. '%')
            ->getQuery()
            ->getResult()
        ;
    }


    public function findCommmandsByStatut($status)
    {
        return $this->createQueryBuilder('P')
            ->where('P.statut = :statut')
            ->setParameter('statut',$status)
            ->getQuery()
            ->getResult();
    }




}