<?php
namespace App\Controller\Mobile;

use App\Entity\Terrain;
use App\Repository\TerrainRepository;
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
 * @Route("/mobile/terrain")
 */
class TerrainMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(TerrainRepository $terrainRepository): Response
    {
        $terrains = $terrainRepository->findAll();

        if ($terrains) {
            return new JsonResponse($terrains, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request): JsonResponse
    {
        $terrain = new Terrain();

        return $this->manage($terrain, $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, TerrainRepository $terrainRepository): Response
    {
        $terrain = $terrainRepository->find((int)$request->get("id"));

        if (!$terrain) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($terrain, $request, true);
    }

    public function manage($terrain, $request, $isEdit): JsonResponse
    {   
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
        
        $terrain->setUp(
            $request->get("nom"),
            $request->get("description"),
            $request->get("activitie"),
            $request->get("contact"),
            $request->get("prix"),
            $imageFileName,
            $request->get("region")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($terrain);
        $entityManager->flush();

        return new JsonResponse($terrain, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, TerrainRepository $terrainRepository): JsonResponse
    {
        $terrain = $terrainRepository->find((int)$request->get("id"));

        if (!$terrain) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($terrain);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, TerrainRepository $terrainRepository): Response
    {
        $terrains = $terrainRepository->findAll();

        foreach ($terrains as $terrain) {
            $entityManager->remove($terrain);
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
