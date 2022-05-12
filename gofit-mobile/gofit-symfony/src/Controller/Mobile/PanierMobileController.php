<?php
namespace App\Controller\Mobile;

use App\Entity\Panier;
use App\Repository\PanierRepository;
use App\Repository\UtilisateurRepository;
use App\Repository\ProduitRepository;
use DateTime;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/mobile/panier")
 */
class PanierMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(PanierRepository $panierRepository): Response
    {
        $paniers = $panierRepository->findAll();

        if ($paniers) {
            return new JsonResponse($paniers, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, UtilisateurRepository $utilisateurRepository, ProduitRepository $produitRepository): JsonResponse
    {
        $panier = new Panier();

        return $this->manage($panier, $utilisateurRepository,  $produitRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, PanierRepository $panierRepository, UtilisateurRepository $utilisateurRepository, ProduitRepository $produitRepository): Response
    {
        $panier = $panierRepository->find((int)$request->get("id"));

        if (!$panier) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($panier, $utilisateurRepository, $produitRepository, $request, true);
    }

    public function manage($panier, $utilisateurRepository, $produitRepository, $request, $isEdit): JsonResponse
    {   
        $utilisateur = $utilisateurRepository->find((int)$request->get("utilisateur"));
        if (!$utilisateur) {
            return new JsonResponse("utilisateur with id " . (int)$request->get("utilisateur") . " does not exist", 203);
        }
        
        $produit = $produitRepository->find((int)$request->get("produit"));
        if (!$produit) {
            return new JsonResponse("produit with id " . (int)$request->get("produit") . " does not exist", 203);
        }
        
        
        $panier->setUp(
            $utilisateur,
            $produit
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($panier);
        $entityManager->flush();

        return new JsonResponse($panier, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, PanierRepository $panierRepository): JsonResponse
    {
        $panier = $panierRepository->find((int)$request->get("id"));

        if (!$panier) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($panier);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, PanierRepository $panierRepository): Response
    {
        $paniers = $panierRepository->findAll();

        foreach ($paniers as $panier) {
            $entityManager->remove($panier);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
