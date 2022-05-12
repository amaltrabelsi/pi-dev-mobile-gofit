<?php

namespace App\Repository;
use App\Entity\Terrain;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Terrain|null find($id, $lockMode = null, $lockVersion = null)
 * @method Terrain|null findOneBy(array $criteria, array $orderBy = null)
 * @method Terrain[]    findAll()
 * @method Terrain[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */

class TerrainRepository  extends ServiceEntityRepository
{

    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Terrain::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(Terrain $entity, bool $flush = true): void
    {
        $this->_em->persist($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function remove(Terrain $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }



    public function search($str)
    {
        return $this->getEntityManager()
            ->createQuery(
                'SELECT t
            FROM App:Terrain t
            WHERE t.nomTerrain LIKE :str'
            )->setParameter('str','%'.$str.'%')
            ->getResult();

    }
    public function findProductsByCategory($region)
    {
        return $this->createQueryBuilder('t')
            ->where('t.region = :region')
            ->setParameter('region',$region)
            ->getQuery()
            ->getResult();
    }



    // /**
    //  * @return Terrain[] Returns an array of Terrain objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('r')
            ->andWhere('r.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('r.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Terrain
    {
        return $this->createQueryBuilder('r')
            ->andWhere('r.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */

}