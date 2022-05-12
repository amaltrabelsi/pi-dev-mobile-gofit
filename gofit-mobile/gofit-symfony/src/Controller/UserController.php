<?php

namespace App\Controller;

use App\Entity\Utilisateur;
use App\Form\ModifierContactType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
class UserController extends AbstractController
{
    /**
     * @Route("/accueil", name="display_welcome")
     */
    public function index2(): Response
    {
        return $this->render('accueil/welcome.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
    /**
     * @Route("/accueil", name="accueil")
     */
    public function index(): Response
    {
        return $this->render('user/index.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
    /**
     * @Route("/profil", name="modifierquoi")
     */
    public function profil(): Response
    {return $this->render('user/modifier.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
    /**
     * @Route("/profilinfo", name="profilinfo")
     */
    public function profilinfo(): Response
    {return $this->render('user/profileinfo.html.twig', [
        'controller_name' => 'UserController',
    ]);
    }
    /**
     * @Route("/accc", name="accc")
     */
    public function accc(): Response
    {return $this->render('acc/Afficher.html.twig', [
        'controller_name' => 'UserController',
    ]);
    }

    /**
     * @Route("/users/profil/modifier", name="users_profil_modifier")
     */
    public function editProfile(Request $request)
    {
        $user = $this->getUser();
        $form = $this->createForm(ModifierContactType::class, $user);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();

            $this->addFlash('message', 'Profil mis à jour');

        }

        return $this->render('user/profilcontactmodifier.html.twig', [
            'f' => $form->createView(),
        ]);
    }
    /**
     * @Route("/users/pass/modifier", name="users_pass_modifier")
     */
    public function editPass(Request $request, UserPasswordEncoderInterface $passwordEncoder)
    {
        if($request->isMethod('POST')){
            $em = $this->getDoctrine()->getManager();

            $user = $this->getUser();

            // On vérifie si les 2 mots de passe sont identiques
            if($request->request->get('pass') == $request->request->get('pass2')){
                $user->setPassword($passwordEncoder->encodePassword($user, $request->request->get('pass')));
                $em->flush();
                $this->addFlash('message', 'Mot de passe mis à jour avec succès');

            }else{
                $this->addFlash('error', 'Les deux mots de passe ne sont pas identiques');
            }
        }

        return $this->render('user/profilmotdepassemodifier.html.twig');
    }
    /**
     * @Route("/qui", name="qui")
     */
    public function qui(): Response
    {
        return $this->render('security/qui.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }


}
