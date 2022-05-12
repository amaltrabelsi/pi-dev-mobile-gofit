<?php
namespace App\Controller\Mobile;

use App\Entity\Produit;
use App\Repository\ProduitRepository;
use App\Repository\BusinessRepository;
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
 * @Route("/mobile/produit")
 */
class ProduitMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(ProduitRepository $produitRepository): Response
    {
        $produits = $produitRepository->findAll();

        if ($produits) {
            return new JsonResponse($produits, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, BusinessRepository $businessRepository): JsonResponse
    {
        $produit = new Produit();

        return $this->manage($produit, $businessRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, ProduitRepository $produitRepository, BusinessRepository $businessRepository): Response
    {
        $produit = $produitRepository->find((int)$request->get("id"));

        if (!$produit) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($produit, $businessRepository, $request, true);
    }

    public function manage($produit, $businessRepository, $request, $isEdit): JsonResponse
    {   
        $business = $businessRepository->find((int)$request->get("business"));
        if (!$business) {
            return new JsonResponse("business with id " . (int)$request->get("business") . " does not exist", 203);
        }
        
        $file = $request->files->get("file");
        if ($file) {
            $imageFileName = md5(uniqid()) . '.' . $file->guessExtension();

            try {
                $file->move($this->getParameter('images_directory'), $imageFileName);
            } catch (FileException $e) {
                dd($e);
            }
        } else {
            if ($request->get("image")) {
                $imageFileName = $request->get("image");
            } else {
                $imageFileName = "null";
            }
        }
        
        $produit->setUp(
            $request->get("refP"),
            (int)$request->get("prixUni"),
            (int)$request->get("quantite"),
            $request->get("description"),
            $request->get("nomProduit"),
            $request->get("categorie"),
            $imageFileName,
            (int)$request->get("note"),
            (int)$request->get("totalnote"),
            (int)$request->get("occurence"),
            $business
        );

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($produit);
        $entityManager->flush();

        return new JsonResponse($produit, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, ProduitRepository $produitRepository): JsonResponse
    {
        $produit = $produitRepository->find((int)$request->get("id"));

        if (!$produit) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($produit);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, ProduitRepository $produitRepository): Response
    {
        $produits = $produitRepository->findAll();

        foreach ($produits as $produit) {
            $entityManager->remove($produit);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
    /**
     * @Route("/image/{image}", methods={"GET"})
     */
    public function getPicture(Request $request): BinaryFileResponse
    {
        return new BinaryFileResponse(
            $this->getParameter('images_directory') . "/" . $request->get("image")
        );
    }
    
}
