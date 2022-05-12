<?php

namespace App\Services\Mailer;

use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Mailer\MailerInterface;

class MailerServiceReclamation
{
    private $mailer;

    /**
     * @param MailerInterface $mailer
     */
    public function __construct(MailerInterface  $mailer)
    {
        $this->mailer = $mailer;
    }
    /**
     * @throws TransportExceptionInterface
     */

    public function sendEmailCommande(string $toMail, array $variables){
        $email=(new TemplatedEmail())
            ->from('gofitesprit@gmail.com')
            ->to($toMail)
            ->subject('votre commande est validée  ')
            ->htmlTemplate('email/order.html.twig')
            ->context($variables);
        $this->mailer->send($email);

    }

    /**
     * @throws TransportExceptionInterface
     */

        public function sendEmail(string $toMail, array $variables){
            $email=(new TemplatedEmail())
                ->from('gofit2022bythefive@gmail.com')
                ->to($toMail)
                ->subject('votre reclamation est envoyée  ')
                ->htmlTemplate('EmailReclamation/email.html.twig')
                ->context($variables);
            $this->mailer->send($email);

        }
}