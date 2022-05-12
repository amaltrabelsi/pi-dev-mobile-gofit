<?php

namespace App\Controller;

use App\Entity\Utilisateur;
use App\Form\RegistrationFormType;
use App\Repository\UtilisateurRepository;
use App\Security\EmailVerifier;
use App\Security\UtilisateurAuthenticator;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mime\Address;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Guard\GuardAuthenticatorHandler;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Contracts\Translation\TranslatorInterface;
use SymfonyCasts\Bundle\VerifyEmail\Exception\VerifyEmailExceptionInterface;
use Twilio\Exceptions\ConfigurationException;
use Twilio\Rest\Client;


class RegistrationController extends AbstractController
{
    private EmailVerifier $emailVerifier;

    public function __construct(EmailVerifier $emailVerifier)
    {
        $this->emailVerifier = $emailVerifier;
    }

    /**
     * @Route("/register", name="app_register")
     * @throws \Twilio\Exceptions\ConfigurationException
     */
    public function register(Request $request, UserPasswordEncoderInterface $userPasswordEncoder, GuardAuthenticatorHandler $guardHandler, UtilisateurAuthenticator $authenticator, EntityManagerInterface $entityManager ,\Swift_Mailer $mailer ): Response
    {
        $user = new Utilisateur();
        $form = $this->createForm(RegistrationFormType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setRoles(['ROLE_USER']);
            // encode the plain password
            $user->setPassword(
                $userPasswordEncoder->encodePassword(
                    $user,
                    $form->get('plainPassword')->getData()
                )
            );
            // On génère le token d'activation
            $user->setActivationToken(md5(uniqid()));

            $entityManager->persist($user);
            $entityManager->flush();
            $message = (new \Swift_Message("Activation de votre compte"))
                ->setFrom('gofit2022bythefive@gmail.com')
                ->setTo($user->getEmail())
                ->setBody(
                    $this->renderView('registration/confirmation_email.html.twig', ['token' => $user->getActivation_Token()]
                    ),
                    "text/html"
                );
            $mailer->send($message);

            $sid = 'AC7139fd47c36bc8a4a1193a03666469d3';
            $token = 'bf7416a7a0bf8b4969c11203dd17822b';
            $num=$user->getNum();
            $numc= '+216 '.$num.' ';
            $client = new Client($sid, $token);
            $client->messages->create(

                $user=$numc ,
                [
                    // A Twilio phone number you purchased at twilio.com/console
                    'from' => '+18624209790',
                    // the body of the text message you'd like to send
                    'body' => 'BienVenue Sur notre Site GoFit'
                ]
            );
            return $this->render('user/index.html.twig');
        }

        return $this->render('registration/register.html.twig', [
            'registrationForm' => $form->createView(),
        ]);
    }

    /**
     * @Route("/activation/{token}", name="activation")
     */
    public function activation($token, UtilisateurRepository $usersRepo){
        // On vérifie si un utilisateur a ce token
        $user = $usersRepo->findOneBy(['activation_token' => $token]);

        // Si aucun utilisateur n'existe avec ce token
        if(!$user){
            // Erreur 404
            throw $this->createNotFoundException('Cet utilisateur n\'existe pas');
        }

        // On supprime le token
        $user->setActivationToken(null);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($user);
        $entityManager->flush();

        // On envoie un message flash
        $this->addFlash('message', 'Vous avez bien activé votre compte');

        // On retoure à l'accueil
        return $this->redirect('/login');
    }
    /**
     * @Route("/register/vendeur", name="app_register_vendeur")
     */
    public function registerVENDEUR(Request $request, UserPasswordEncoderInterface $userPasswordEncoder, GuardAuthenticatorHandler $guardHandler, UtilisateurAuthenticator $authenticator, EntityManagerInterface $entityManager ,\Swift_Mailer $mailer ): Response
    {
        $user = new Utilisateur();
        $form = $this->createForm(RegistrationFormType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setRoles(['ROLE_VENDEUR']);
            // encode the plain password
            $user->setPassword(
                $userPasswordEncoder->encodePassword(
                    $user,
                    $form->get('plainPassword')->getData()
                )
            );
            // On génère le token d'activation
            $user->setActivationToken(md5(uniqid()));

            $entityManager->persist($user);
            $entityManager->flush();
            $message = (new \Swift_Message("Activation de votre compte"))
                ->setFrom('gofit2022bythefive@gmail.com')
                ->setTo($user->getEmail())
                ->setBody(
                    $this->renderView('registration/confirmation_email.html.twig', ['token' => $user->getActivation_Token()]
                    ),
                    "text/html"
                );
            $mailer->send($message);

            return $this->render('user/index.html.twig');
        }

        return $this->render('registration/register.html.twig', [
            'registrationForm' => $form->createView(),
        ]);
    }


}
