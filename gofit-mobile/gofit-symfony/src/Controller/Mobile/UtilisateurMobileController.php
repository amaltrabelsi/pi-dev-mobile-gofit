<?php

namespace App\Controller\Mobile;

use App\Entity\Utilisateur;
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
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

/**
 * @Route("/mobile/utilisateur")
 */
class UtilisateurMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(UtilisateurRepository $utilisateurRepository): Response
    {
        $utilisateurs = $utilisateurRepository->findAll();

        if ($utilisateurs) {
            return new JsonResponse($utilisateurs, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, UserPasswordEncoderInterface $passwordEncoder): JsonResponse
    {
        $utilisateur = new Utilisateur();

        return $this->manage($utilisateur, $request, false, $passwordEncoder);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, UtilisateurRepository $utilisateurRepository, UserPasswordEncoderInterface $passwordEncoder): Response
    {
        $utilisateur = $utilisateurRepository->find((int)$request->get("id"));

        if (!$utilisateur) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($utilisateur, $request, true, $passwordEncoder);
    }

    public function manage($utilisateur, $request, $isEdit, $passwordEncoder): JsonResponse
    {

        if (!$isEdit) {
            $checkEmail = $this->getDoctrine()->getRepository(Utilisateur::class)
                ->findOneBy(["email" => $request->get("email")]);

            if ($checkEmail) {
                return new JsonResponse("Email already exist", 203);
            }
        }

        $utilisateur->setUp(
            $request->get("email"),
            $request->get("role"),
            $request->get("password"),
            $request->get("num")
        );

        if (!$isEdit) {
            $email = $utilisateur->getEmail();

            $utilisateur->setPassword($passwordEncoder->encodePassword($utilisateur, $request->get("password")));

            if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
                try {
                    $transport = new \Swift_SmtpTransport('smtp.gmail.com', 465, 'ssl');
                    $transport->setUsername('pidev.app.esprit@gmail.com')->setPassword('pidev-cred');
                    $mailer = new \Swift_Mailer($transport);
                    $message = new \Swift_Message('Welcome');
                    $message->setFrom(array('pidev.app.esprit@gmail.com' => 'Bienvenu'))
                        ->setTo(array($email))
                        ->setBody("<h1>Bienvenu dans notre applictaion GoFit</h1>", 'text/html');
                    $mailer->send($message);
                } catch (\Exception $exception) {
                    return new JsonResponse(null, 405);
                }
            }
        }

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($utilisateur);
        $entityManager->flush();

        return new JsonResponse($utilisateur, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, UtilisateurRepository $utilisateurRepository): JsonResponse
    {
        $utilisateur = $utilisateurRepository->find((int)$request->get("id"));

        if (!$utilisateur) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($utilisateur);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, UtilisateurRepository $utilisateurRepository): Response
    {
        $utilisateurs = $utilisateurRepository->findAll();

        foreach ($utilisateurs as $utilisateur) {
            $entityManager->remove($utilisateur);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/verif", methods={"POST"})
     */
    public function verif(Request $request, UtilisateurRepository $userRepository, UserPasswordEncoderInterface $passwordEncoder): Response
    {
        $user = $userRepository->findOneBy(["email" => $request->get("email")]);

        if ($user) {
            if ($passwordEncoder->isPasswordValid($user, $request->get("password"))) {
                return new JsonResponse($user, 200);
            } else {
                return new JsonResponse("user found but pass wrong", 203);
            }
        } else {
            return new JsonResponse([], 204);
        }
    }

}
