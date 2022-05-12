<?php

namespace App\Form;

use App\Entity\Utilisateur;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class SearchFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

            ->add('roles',ChoiceType::class, [
        'choices' => [
            'Utilisateur' => 'ROLE_USER',
            'Vendeur' => 'ROLE_VENDEUR'
        ],
        'expanded' => true,
        'multiple' => true,
        'label' => 'Rôles'
    ])

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Utilisateur::class,
        ]);
    }
}
