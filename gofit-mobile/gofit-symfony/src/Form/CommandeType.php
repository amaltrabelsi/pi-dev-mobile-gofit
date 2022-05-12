<?php

namespace App\Form;

use App\Entity\Commande;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CommandeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

            ->add('total',NumberType::class,[
                'label'=>"Le total",
                'invalid_message' => 'Il faut entrer un double ou entier',
                'attr'=>[
                    "placeholder"=>"Exemple :587.5
                    ",
                    'class'=>"form-control"
                ]
            ])
            ->add('nbProduit',IntegerType::class,[
                'label'=>"Nombre de produits",
                'invalid_message' => 'Il faut entrer un entier',
                'attr'=>[


                    'class'=>"form-control"
                ]
            ])
            ->add('modePaiement', ChoiceType::class, [
                'choices'  => [
                    'Carte Bancaire' => 'Carte Bancaire',
                    'Chèque' => 'Chèque',
                    'En espèces' => 'En espèces',
                    'PayPal' => 'PayPal'],
                    'attr'=>[


                        'class'=>"form-control"
                    ]

            ])
            ->add('fkClientpan')
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
            'data_class' => Commande::class,
        ]);
    }
}
