<?php
namespace App\Controller\Mobile;

use App\Entity\Business;
use App\Repository\BusinessRepository;
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
 * @Route("/mobile/business")
 */
class BusinessMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(BusinessRepository $businessRepository): Response
    {
        $businesss = $businessRepository->findAll();

        if ($businesss) {
            return new JsonResponse($businesss, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, UtilisateurRepository $utilisateurRepository): JsonResponse
    {
        $business = new Business();

        return $this->manage($business, $utilisateurRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, BusinessRepository $businessRepository, UtilisateurRepository $utilisateurRepository): Response
    {
        $business = $businessRepository->find((int)$request->get("id"));

        if (!$business) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($business, $utilisateurRepository, $request, true);
    }

    public function manage($business, $utilisateurRepository, $request, $isEdit): JsonResponse
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
        
        $business->setUp(
            $request->get("nom"),
            $request->get("nomGerant"),
            $request->get("prenomGerant"),
            $request->get("region"),
            $request->get("adresse"),
            DateTime::createFromFormat("d-m-Y", $request->get("dateFondation")),
            $request->get("description"),
            $request->get("telPortable"),
            $request->get("telFix"),
            $imageFileName,
            (int)$request->get("occurence"),
            $utilisateur
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($business);
        $entityManager->flush();

        return new JsonResponse($business, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, BusinessRepository $businessRepository): JsonResponse
    {
        $business = $businessRepository->find((int)$request->get("id"));

        if (!$business) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($business);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, BusinessRepository $businessRepository): Response
    {
        $businesss = $businessRepository->findAll();

        foreach ($businesss as $business) {
            $entityManager->remove($business);
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
