<?php
namespace App\Controller\Mobile;

use App\Entity\Actualite;
use App\Repository\ActualiteRepository;
use App\Repository\UtilisateurRepository;
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
 * @Route("/mobile/actualite")
 */
class ActualiteMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(ActualiteRepository $actualiteRepository): Response
    {
        $actualites = $actualiteRepository->findAll();

        if ($actualites) {
            return new JsonResponse($actualites, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, UtilisateurRepository $utilisateurRepository): JsonResponse
    {
        $actualite = new Actualite();

        return $this->manage($actualite, $utilisateurRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, ActualiteRepository $actualiteRepository, UtilisateurRepository $utilisateurRepository): Response
    {
        $actualite = $actualiteRepository->find((int)$request->get("id"));

        if (!$actualite) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($actualite, $utilisateurRepository, $request, true);
    }

    public function manage($actualite, $utilisateurRepository, $request, $isEdit): JsonResponse
    {   
        $utilisateur = $utilisateurRepository->find((int)$request->get("utilisateur"));
        if (!$utilisateur) {
            return new JsonResponse("utilisateur with id " . (int)$request->get("utilisateur") . " does not exist", 203);
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
        
        $actualite->setUp(
            $request->get("titre"),
            $request->get("description"),
            $request->get("contenu"),
            $request->get("categorie"),
            $imageFileName,
            $utilisateur
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($actualite);
        $entityManager->flush();

        return new JsonResponse($actualite, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, ActualiteRepository $actualiteRepository): JsonResponse
    {
        $actualite = $actualiteRepository->find((int)$request->get("id"));

        if (!$actualite) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($actualite);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, ActualiteRepository $actualiteRepository): Response
    {
        $actualites = $actualiteRepository->findAll();

        foreach ($actualites as $actualite) {
            $entityManager->remove($actualite);
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
