<?php
namespace App\Controller\Mobile;

use App\Entity\Reclamation;
use App\Repository\ReclamationRepository;
use App\Repository\TerrainRepository;
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
 * @Route("/mobile/reclamation")
 */
class ReclamationMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(ReclamationRepository $reclamationRepository): Response
    {
        $reclamations = $reclamationRepository->findAll();

        if ($reclamations) {
            return new JsonResponse($reclamations, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, TerrainRepository $terrainRepository, UtilisateurRepository $utilisateurRepository): JsonResponse
    {
        $reclamation = new Reclamation();

        return $this->manage($reclamation, $terrainRepository,  $utilisateurRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, ReclamationRepository $reclamationRepository, TerrainRepository $terrainRepository, UtilisateurRepository $utilisateurRepository): Response
    {
        $reclamation = $reclamationRepository->find((int)$request->get("id"));

        if (!$reclamation) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($reclamation, $terrainRepository, $utilisateurRepository, $request, true);
    }

    public function manage($reclamation, $terrainRepository, $utilisateurRepository, $request, $isEdit): JsonResponse
    {   
        $terrain = $terrainRepository->find((int)$request->get("terrain"));
        if (!$terrain) {
            return new JsonResponse("terrain with id " . (int)$request->get("terrain") . " does not exist", 203);
        }
        
        $utilisateur = $utilisateurRepository->find((int)$request->get("utilisateur"));
        if (!$utilisateur) {
            return new JsonResponse("utilisateur with id " . (int)$request->get("utilisateur") . " does not exist", 203);
        }
        
        
        $reclamation->setUp(
            $request->get("contenu"),
            $terrain,
            $utilisateur
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($reclamation);
        $entityManager->flush();

        return new JsonResponse($reclamation, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, ReclamationRepository $reclamationRepository): JsonResponse
    {
        $reclamation = $reclamationRepository->find((int)$request->get("id"));

        if (!$reclamation) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($reclamation);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, ReclamationRepository $reclamationRepository): Response
    {
        $reclamations = $reclamationRepository->findAll();

        foreach ($reclamations as $reclamation) {
            $entityManager->remove($reclamation);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
