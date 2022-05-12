<?php

namespace App\Controller;

use App\Repository\ActualiteRepository;
use App\Repository\BusinessRepository;
use App\Repository\ProduitRepository;
use App\Repository\ReclamationRepository;
use App\Repository\TerrainRepository;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\UtilisateurRepository;
use App\Entity\Utilisateur;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;

use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class AdminController extends AbstractController
{



    /**
     * @IsGranted("ROLE_ADMIN")
     * @param UtilisateurRepository $repository
     * @Route ("/AfficheU",name="Affiche")
     */
    public function Affiche(UtilisateurRepository $repository ,PaginatorInterface $paginator ,Request $request
    )
    {

        $utilisateur = $repository->findAll();

        return $this->render('admin/Afficher.html.twig', [
            'utilisateur' => $utilisateur
        ]);
    }
    /**
     * @Route ("admin/Supprimer/{id_utilisateur}",name="supp")
     */
    public function Supprimer($id_utilisateur) {
        $utilisateur=$this->getDoctrine()->getRepository(Utilisateur::class)->find($id_utilisateur);
        $em=$this->getDoctrine()->getManager();
        $em->remove($utilisateur);
        $em->flush();
        return $this->redirectToRoute('Affiche');

    }

    /**
     * @Route("/AllUsers",name="AllUsers")
     */
public function AllUsers (NormalizerInterface $normalizer)
{
    $repository =$this->getDoctrine()->getRepository(Utilisateur::class);
    $user = $repository ->findAll();

    $jsonContent = $normalizer ->normalize($user,'json',['groups'=>'post:read']);
    return $this ->render('user/allusersJson.html.twig',[
        'data'=>$jsonContent,
    ]);

    return new Response(json_encode($jsonContent));

}
    /**
     * @IsGranted("ROLE_ADMIN")
     * @param UtilisateurRepository $repository
     * @Route ("/find",name="Affichestat")
     */
    public function find(UtilisateurRepository $rep1,ActualiteRepository $rep2 , TerrainRepository $rep3 ,
                         ReclamationRepository $rep4 , ProduitRepository $rep5 , BusinessRepository $rep6
    )
    {

        $a = $rep1->findAll();
        $b= $rep2->findAll();
        $c = $rep3->findAll();
        $d= $rep4->findAll();
        $e =$rep5->findAll();
        $f= $rep6->findAll();

        return $this->render('admin/find.html.twig', [
            'rep1' => $a,'rep2' => $b,'rep3' => $c,'rep4' => $d,
            'rep5' => $e,'rep6' => $f
        ]);
    }


}
