<?php

namespace App\Controller;
use App\Repository\TerrainRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;
use phpDocumentor\Reflection\Types\Object_;
use Symfony\Bundle\FrameworkBundle\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\UX\Chartjs\Builder\ChartBuilderInterface;
use Symfony\UX\Chartjs\Model\Chart;
use App\Entity\Reclamation;
use App\Services\Mailer\MailerServiceReclamation;
use App\Controller\EmailControllerReclamation;
use App\Entity\Terrain;
use App\Entity\Utilisateur;
use App\Form\ReclamationAjoutType;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mime\Email;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Routing\Annotation\Route;
use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Writer\Xlsx;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;

class ReclamationController extends AbstractController
{
    private $mailerService;

    public function __construct(MailerServiceReclamation $mailerService){

        $this->mailerService = $mailerService;}

    /**
     * @Route("/reclamation", name="app_reclamation")
     */
    public function index(): Response
    {
        return $this->render('reclamation/stat.html.twig', [
            'controller_name' => 'ReclamationController',
        ]);
    }

    /**
     * @param ReclamationRepository $repository
     * @Route ("reclamation/AfficheRec",name="AfficheRec")
     */
    public function Affiche(ReclamationRepository $repository ,NormalizerInterface $normalizer)
    {
        $reclamation = $repository->findAll();
        $jsonContent = $normalizer ->normalize($reclamation,'json',['groups'=>'post:read']);
        return $this ->render('user/allusersJson.html.twig',[
            'data'=>$jsonContent,
        ]);

        return new Response(json_encode($jsonContent));

    }

    /**
     * @Route ("/Supprimer/{idreclamation}",name="suppp")
     */
    public function Supprimer($idreclamation)
    {
        $reclamation = $this->getDoctrine()->getRepository(Reclamation::class)->find($idreclamation);
        $em = $this->getDoctrine()->getManager();
        $em->remove($reclamation);
        $em->flush();
        return $this->redirectToRoute('AfficheRec');

    }

    /**
     * @Route ("reclamation/Modifier/{id}",name="modifier")
     */
    public function Modifier(Request $request, ReclamationRepository $repository , $id){

        $reclamation = $repository->find($id);
        $form = $this->createForm(ReclamationType::class,$reclamation);
        $form->add('Modifier', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('AfficheRec');
        }

        return $this->render('reclamation/ModifierReclamation.html.twig', ['modifreclamation' => $form->createView()]);
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("reclamation/Ajouter/{Idter}/{email}/{idUser}",name ="ajoutRec")
     */

    function Ajouter(Request $request ,$Idter ,$email,$idUser)
    {

        $reclamation = new reclamation();
        $reclamation->setFkterrainRecId($this->getDoctrine()->getRepository(Terrain::class)->find($Idter));
        $reclamation->setFkutilisateur($this->getDoctrine()->getRepository(Utilisateur::class)->find($idUser));

        $form = $this->createForm(ReclamationAjoutType::class, $reclamation);
        $form->add('Ajouter', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $this->mailerService->sendEmail($email, array( 'client' =>$client=$this->getDoctrine()->getRepository(Utilisateur::class)->find(90)));
            $em = $this->getDoctrine()->getManager();


            $em->persist($reclamation);
            $em->flush();
            return $this->redirect('/listterrain');
        }

        return $this->render('reclamation/AjouterReclamation.html.twig', ['fr' => $form->createView()]);
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("/Excel",name="excel")
     */
    public function Excel1(Request $request, ReclamationRepository $repository)
    {
        $spreadsheet = new Spreadsheet();
        $reclamation = $repository->findAll();
        /* @var $sheet \PhpOffice\PhpSpreadsheet\Writer\Xlsx\Worksheet */
        $sheet = $spreadsheet->getActiveSheet();
        $sheet->setTitle("Les reclamations des terrains");
        $sheet->setCellValue('A1', 'ID');
        $sheet->setCellValue('B1', 'Utilisateur');
        $sheet->setCellValue('C1', 'Terrain');
        $sheet->setCellValue('D1', 'Contenu');
        $counter = 2;
        foreach ($reclamation as $rec) {
            $sheet->setCellValue('A' . $counter, $rec->getReclamationId());
            $sheet->setCellValue('B' . $counter, $rec->getFkutilisateur());
            $sheet->setCellValue('C' . $counter, $rec->getFkterrainRecId());
            $sheet->setCellValue('D' . $counter, $rec->getContenu());
            $counter++;
        }

        // Create your Office 2007 Excel (XLSX Format)
        $writer = new Xlsx($spreadsheet);

        // Create a Temporary file in the system
        $fileName = 'Les Reclamations des terrains.xlsx';
        $temp_file = tempnam(sys_get_temp_dir(), $fileName);

        // Create the excel file in the tmp directory of the system
        $writer->save($temp_file);

        // Return the excel file as an attachment
        return $this->file($temp_file, $fileName, ResponseHeaderBag::DISPOSITION_INLINE);

    }


}