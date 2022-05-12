<?php

namespace App\Form;

use App\Entity\Business;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;

use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TelType;




class BusinessType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomBusiness',TextType::class,[
                'label'=>"Business",
                'attr'=>[
                    "placeholder"=>"Nom de la marque
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('nomGerant',TextType::class,[
                'label'=>"Nom du Gérant",
                'attr'=>[
                    "placeholder"=>"Nom du gérant 
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('prenomGerant',TextType::class,[
                'label'=>"Prénom du Gérant",
                'attr'=>[
                    "placeholder"=>"Prenom du Gérant
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('region',ChoiceType::class,array(
                    'choices'=> array(
                        'tunis'=>"tunis",
                        'ariana'=>"ariana",
                        'nebel'=>"nebel",
                        'ben arous'=> "benarous",
                        'sousse' =>"sousse",
                        'monastir'=>"monastir",
                        'gafsa'=>"gafsa",
                    )
                )
            )

            ->add('adresse',TextType::class,[
                'label'=>"Adresse",
                'attr'=>[
                    "placeholder"=>"Adresse physique
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('dateFondation',DateType::class,[
                'label'=>"Date De Fondation",
                'attr'=>[
                    "placeholder"=>"
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('description',TextType::class,[
                'label'=>"Description",
                'attr'=>[
                    "placeholder"=>"Description de la boutique
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('telPortable',TextType::class,[
                'label'=>"Téléphone Portable",
                'attr'=>[
                    "placeholder"=>"+216 xxx xxx xx
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('telFix',TextType::class,[
                'label'=>"Teléphone Fix",
                'attr'=>[
                    "placeholder"=>"+216 xxx xxx xx
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('pathImage',FileType::class,array('data_class' => null)

            )
            ->add('Ajouter',SubmitType::class ,  [
                'attr' => ['class' => "btn btn-primary"
                ] ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Business::class,
        ]);
    }
}
