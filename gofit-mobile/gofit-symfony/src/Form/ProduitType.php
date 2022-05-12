<?php

namespace App\Form;

use App\Entity\Produit;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class ProduitType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('refP',TextType::class,[
                'label'=>"Réfernce",
                'attr'=>[
                    "placeholder"=>"exemple:GJU47
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('prixUni',TextType::class,[
                'label'=>"Prix Unitaire",
                'attr'=>[
                    "placeholder"=>"Ex:1584.500
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('quantite',IntegerType::class,[
                'label'=>"Quantité",
                'attr'=>[                    "placeholder"=>"5
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('description',TextType::class,[
                'label'=>"Déscription",

                'attr'=>[
                    "placeholder"=>"Description detaillée du produit
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('nomProduit',TextType::class,[
                'label'=>"Nom du Produit",
                'attr'=>[
                    "placeholder"=>"Nom du produit
                    ",
                    'class'=>"form-control"
                ]
            ])


            ->add('categorie', ChoiceType::class, [
                'choices'  => [
                    'Sport' => 'sport',
                    'Equipements' => 'equipement',
                    'Stregth & conditionning' => 'Stregth & conditionning',
                    'Outdoors Sports' => 'Outdoors Sports',
                    'Confort' => 'fitness',
                    'Fitness Accessories' => 'Fitness Accessories',
                    'supplements & Food' => 'supplements & Food'
                ],
                'attr'=>[


                    'class'=>"form-control"
                ]
            ])

            ->add('pathImage',FileType::class,array('data_class' => null)

            )

            ->add('confirmer',SubmitType::class,[

                'attr'=>[

                    'class'=>"btn btn-primary"


                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Produit::class,
        ]);
    }
}
