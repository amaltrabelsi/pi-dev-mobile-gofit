<?php



namespace App\Repository;
use App\Entity\Produit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;


/**
 * @method Produit|null find($id, $lockMode = null, $lockVersion = null)
 * @method Produit|null findOneBy(array $criteria, array $orderBy = null)
 * @method Produit[]    findAll()
 * @method Produit[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProduitRepository extends ServiceEntityRepository

{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Produit::class);
    }


    public function findEntitiesByString($str)
    {
        return $this->getEntityManager()
        ->createQuery(
            'SELECT p
            FROM App:Produit p
            WHERE p.nomProduit LIKE :str'
        )->setParameter('str','%'.$str.'%')
            ->getResult();

}
    public function findProductsByCategory($categoriee)
    {
        return $this->createQueryBuilder('P')
            ->where('P.categorie = :categorie')
            ->setParameter('categorie',$categoriee)
            ->getQuery()
            ->getResult();
    }
    public function findProductsByBusiness($id)
    {
        return $this->createQueryBuilder('P')
            ->where('P.fkBusinees = :id')
            ->setParameter('id', $id)
            ->getQuery()
            ->getResult();

    }




    // /**
    //  * @return Produit[] Returns an array of Classeroom objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('c.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Classeroom
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */

//
//    public function findOneByIdJoinedToCategory(int $productId): ?Product
//    {
//        $entityManager = $this->getEntityManager();
//
//        $query = $entityManager->createQuery(
//            'SELECT p, c
//            FROM App\Entity\Product p
//            INNER JOIN p.category c
//            WHERE p.id = :id'
//        )->setParameter('id', $productId);
//
//        return $query->getOneOrNullResult();
  //  }
}