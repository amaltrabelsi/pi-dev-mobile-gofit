<?php

namespace App\Form;

use App\Entity\Terrain;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
class TerrainType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomTerrain')
            ->add('description')
            ->add('region' ,ChoiceType::class,
                array('choices'=> array(
                    'Ariana'=>'',
                    'Béja' =>' Béja',
                    'Ben Arous' =>'Ben Arous',
                    'Bizerte'  =>' Bizerte',
                    'Gabès'    =>'Gabès',
                    'Gafsa'    =>' Gafsa',
                    'Jendouba'=>'Jendouba',
                    'Kairouan' =>'Kairouan',
                    'Kasserine'    =>' Kasserine',
                    'Kébili'   =>'Kébili',
                    'Le Kef'   =>' Le Kef',
                    'Mahdia'   =>'Mahdia',
                    'La Manouba'   =>'La Manouba',
                    'Médenine' =>'Médenine',
                    'Monastir' =>'Monastir',
                    'Nabeul'   =>'Nabeul',
                    'Sfax' =>' Sfax',
                    'Sidi Bouzid'  =>'Sidi Bouzid',
                    'Siliana'  =>'Siliana',
                    'Sousse'   =>'Sousse',
                    'Tataouine'=>'Tataouine',
                    'Tozeur'   =>'Tozeur',
                    'Tunis'    =>'Tunis',
                    'Zaghouan'    =>'Zaghouan ',


                ) ))

            ->add('activitie' ,ChoiceType::class,
                array('choices'=> array(
                    'Terrain de football'=>'Terrain de football',
                    'Terrain de basket-ball' =>' Terrain de basket-ball',
                    'Terrain de cricket' =>'Terrain de cricket',
                    'Terrain de hockey sur gazon'  =>' Terrain de hockey sur gazon',
                    'Terrain de rugby'    =>'Terrain de rugby',
                    ' Terrain de tennis'    =>' Terrain de tennis',
                    'Terrain de vollyball'=>'Terrain de vollyball',   ) ))

            ->add('contact')
            ->add('prix')
            ->add('image', FileType::class,array('data_class' => null))
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Terrain::class,
        ]);
    }
}
