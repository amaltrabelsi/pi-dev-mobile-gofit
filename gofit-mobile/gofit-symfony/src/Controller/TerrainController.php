<?php

namespace App\Controller;

use App\Entity\Terrain;
use App\Repository\TerrainRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\TerrainType;
use Knp\Component\Pager\PaginatorInterface;
class TerrainController extends AbstractController
{
    /**
     * @Route("/terrain", name="app_terrain")
     */
    public function index(): Response
    {
        return $this->render('terrain/index.html.twig', [
            'controller_name' => 'TerrainController',
        ]);
    }

    /**
     * @param Request $request
     * @Route ("/Terrain/Ajout" ,name="Ajouterterrain")
     */
    function Ajouter(Request $request)
    {
        $Terrain = new Terrain();
        $form = $this->createForm(TerrainType::class, $Terrain);
        $form->add('Ajouter', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('image')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            //stocké les images sous public uploads images (Config/service.yml)
            $file->move($this->getParameter('images_directory'), $fileName);
            $Terrain->setImage($fileName);
            $em = $this->getDoctrine()->getManager();
            $em->persist($Terrain);
            $em->flush();
            return $this->redirectToRoute('AfficheTer');
        }

        return $this->render('terrain/Ajouter.html.twig', ['ft' => $form->createView()]);

    }

    /**
     * @param TerrainRepository $repository
     * @Route ("/Terrain/ArricheTer",name="AfficheTer")
     */
    public function Affiche(TerrainRepository $repository)
    {
        $Terrain = $repository->findAll();
        return $this->render('terrain/affichageTerrain.html.twig', [
            'Terrain' => $Terrain
        ]);
    }

    /**
     * @Route ("/terrain/Supprimer/{Idsupp}",name="suppterrain")
     */
    public function Supprimer($Idsupp)
    {
        $Terrain = $this->getDoctrine()->getRepository(Terrain::class)->find($Idsupp);
        $em = $this->getDoctrine()->getManager();
        $em->remove($Terrain);
        $em->flush();
        return $this->redirectToRoute('AfficheTer');

    }

    /**
     * @Route ("/Modifier/{Id}",name="mod")
     */
    public function Modifier(Request $request, TerrainRepository $repository, $Id)
    {
        $terrain = $repository->find($Id);
        $form = $this->createForm(TerrainType::class, $terrain);
        $form->add('Modifier', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('image')->getData();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('images_directory'), $fileName);

            $em = $this->getDoctrine()->getManager();
            $terrain->setImage($fileName);

            $em->flush();

            return $this->redirectToRoute('AfficheTer');
        }

        return $this->render('terrain/modifier.html.twig', ['fmod' => $form->createView()]);
    }

    /**
     * @Route("/frontterrain/{id}", name="detail_terrain")
     */
    public function front($id, TerrainRepository $repository): Response
    {   $region='tunis';
        $terrain = $this->getDoctrine()->getRepository(terrain::class)->find($id);
        if($terrain!= null ){
            $region=$terrain->getRegion();
        }
        $terrainregion=$this->getDoctrine()->getRepository(terrain::class)->
        findBy(['region'=>$region],['region' => 'desc']);
         return $this->render('TerrainHome/hometerrain.html.twig', [

            'detail' => $terrain,'terrain'=>$terrainregion,


        ]);
    }
    /**
     * @Route("/terrain/{id}", name="detailterrain")
     */
    public function detail_terrain($id): Response
    {
        $terrain= $this->getDoctrine()->getRepository(Terrain::class)->find($id);

        return $this->render('terrain/detailsTerrain.html.twig', [

            'detail' => $terrain,


        ]);
    }
    /**
     * @Route("/listterrain", name="terrainacc")
     */
    public function Accu(Request $request, PaginatorInterface $paginator): Response
    {
        $terrainlist= $this->getDoctrine()->getRepository(Terrain::class)->findAll();
        $terrain= $paginator->paginate(
            $terrainlist,
            $request->query->getInt('page',1),
            8

        );

        return $this->render('TerrainHome/index.html.twig', [

            'p' => $terrain,


        ]);


    }

    //////// search  with ajax
    /**
     * @Route("/ajaxSearch", name="searchAjax")
     */

    public function searchAction (Request $request,TerrainRepository $repository): Response
    {

        $requestString =$request->get('q');
        $posts = $repository->search($requestString);
        if(!$posts)
        {
            $result['posts']['error']="Terrain non trouvé";
        }
        else {
            $result['posts']=$this->getRealEntities($posts);

        }
        return new Response(json_encode($result));

    }

    public function getRealEntities($posts)
    {
        foreach ($posts as $post){
            $realEntities[$post->getterrainId()]=[$post->getnomTerrain()];
        }
        return $realEntities;

    }
}
