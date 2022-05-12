<?php

namespace App\Form;

use App\Entity\Actualite;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;

use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;



class ActualiteType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre',TextType::class,[
                'label'=>"Titre",
                'attr'=>[
                    "placeholder"=>"Titre de votre article
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('description',TextType::class,[
                'label'=>"Description",
                'attr'=>[
                    "placeholder"=>"Sur quoi porte votre article
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('contenu',TextareaType::class,[
                'label'=>"Contenu",
                'attr'=>[
                    "placeholder"=>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('categorie',ChoiceType::class,array(
                'choices'=> array(
                    'Sport'=>"Sport",
                    'Fitness'=>"Fitness",
                    'Football'=>"Football",
                    'Bien-Etre'=> "Bien-etre",
                    'Hygiene de vie' =>"Hygiene de vie",
                    'Shopping'=>"Shopping",

                )))


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
            'data_class' => Actualite::class,
        ]);
    }
}
