<?php


namespace App\Repository;
use App\Entity\Actualite;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;





class ActualiteRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Actualite::class);


    }
    /**
     *
     *
     */
    public function findActualitesByString($str){
        return $this->getEntityManager()
            ->createQuery(
                'SELECT p
                FROM App:Actualite p
                WHERE p.titre LIKE :str'
            )
            ->setParameter('str', '%'.$str.'%')
            ->getResult();
    }


}