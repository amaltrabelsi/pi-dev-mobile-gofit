<?php

namespace App\Repository;

use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Entity\Panier;


/**
 * @method Panier|null find($id, $lockMode = null, $lockVersion = null)
 * @method Panier|null findOneBy(array $criteria, array $orderBy = null)
 * @method Panier[]    findAll()
 * @method Panier[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */

class PanierRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Panier::class);
    }

    public function findCartByClient($id)
    {
        return $this->createQueryBuilder('P')
            ->where('P.fkUserclient = :id')
            ->setParameter('id',$id)
            ->getQuery()
            ->getResult();
    }
    public function findDetailsByClient($id)
    {
        return $this->createQueryBuilder('c')
            ->join('c.produit', 'p')
            ->where('c.fkClientpan LIKE :id')
            ->setParameter('id', '%' .$id. '%')
            ->getQuery()
            ->getResult()
            ;
    }








}