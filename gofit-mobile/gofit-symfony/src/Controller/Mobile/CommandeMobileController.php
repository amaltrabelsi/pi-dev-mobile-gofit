<?php

namespace App\Controller\Mobile;

use App\Entity\Commande;
use App\Repository\CommandeRepository;
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
 * @Route("/mobile/commande")
 */
class CommandeMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(CommandeRepository $commandeRepository): Response
    {
        $commandes = $commandeRepository->findAll();

        if ($commandes) {
            return new JsonResponse($commandes, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, UtilisateurRepository $utilisateurRepository): JsonResponse
    {
        $commande = new Commande();

        return $this->manage($commande, $utilisateurRepository, $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, CommandeRepository $commandeRepository, UtilisateurRepository $utilisateurRepository): Response
    {
        $commande = $commandeRepository->find((int)$request->get("id"));

        if (!$commande) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($commande, $utilisateurRepository, $request, true);
    }

    public function manage($commande, $utilisateurRepository, $request, $isEdit): JsonResponse
    {
        $utilisateur = $utilisateurRepository->find((int)$request->get("utilisateur"));
        if (!$utilisateur) {
            return new JsonResponse("utilisateur with id " . (int)$request->get("utilisateur") . " does not exist", 203);
        }


        $commande->setUp(
            DateTime::createFromFormat("d-m-Y", $request->get("dateC")),
            (int)$request->get("total"),
            (int)$request->get("nbProduit"),
            $request->get("modePaiement"),
            (int)$request->get("statut"),
            $utilisateur
        );

        if (!$isEdit) {
            $email = $utilisateur->getEmail();
            if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
                try {
                    $transport = new \Swift_SmtpTransport('smtp.gmail.com', 465, 'ssl');
                    $transport->setUsername('pidev.app.esprit@gmail.com')->setPassword('pidev-cred');
                    $mailer = new \Swift_Mailer($transport);
                    $message = new \Swift_Message('Welcome');
                    $message->setFrom(array('pidev.app.esprit@gmail.com' => 'Bienvenu'))
                        ->setTo(array($email))
                        ->setBody("<h1>Bienvenu a notre application</h1>", 'text/html');
                    $mailer->send($message);
                } catch (\Exception $exception) {
                    return new JsonResponse(null, 405);
                }
            }
        }


        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($commande);
        $entityManager->flush();

        return new JsonResponse($commande, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public
    function delete(Request $request, EntityManagerInterface $entityManager, CommandeRepository $commandeRepository): JsonResponse
    {
        $commande = $commandeRepository->find((int)$request->get("id"));

        if (!$commande) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($commande);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public
    function deleteAll(EntityManagerInterface $entityManager, CommandeRepository $commandeRepository): Response
    {
        $commandes = $commandeRepository->findAll();

        foreach ($commandes as $commande) {
            $entityManager->remove($commande);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }

}
