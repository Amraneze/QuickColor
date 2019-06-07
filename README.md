# QuickColor
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RAH_Sab_Console.prj
{
    class Program
    {
        static void Main(string[] args)
        {
            //variables
            String nom;
            String prenom;
            int anneeNaissance;
            int age;
            int nbNotes;

            Console.WriteLine("Bonjour! ");
            Console.WriteLine("Vous êtes sur l'application de Rahhou Sabrine ");

            Console.WriteLine("Entrer votre nom : ");
            nom = Console.ReadLine();

            Console.WriteLine("Entrer votre prenom: ");
            prenom = Console.ReadLine();

            Console.WriteLine("Quelle est votre année de naissance ? ");
            String valeur = Console.ReadLine();

            bool validite = int.TryParse(valeur, out anneeNaissance);

            if (!validite)
            {
                Console.WriteLine("n'est pas une année valide");
                Console.ReadKey();
            }
            else
            {
                Console.WriteLine("anneeNaissance : " + anneeNaissance);

            }
            age = DateTime.Now.Year - anneeNaissance;
            Console.WriteLine("age" + age);

            if (age < 21)
            {
                String s = String.Format("{0} ans?! Qu'est-ce que tu es ...récent :) ", age);
                Console.WriteLine(s);
            }
            else if (age == 21 || age == 22)
            {
                String s = String.Format("{0} ans!", age);
                Console.WriteLine(s);
            }
            else if (age > 22)
            {
                String s = String.Format("vous avez {0} ans!", age);
                Console.WriteLine(s);
                Console.ReadKey();
            }
            bool valide1 = false;
            do
            {
                Console.WriteLine("Veuillez entrer un nombre entiére comprise entre 3 et 5 inclus");
                String nombre = Console.ReadLine();
                int.TryParse(nombre, out nbNotes);
                valide1 = nbNotes >= 3 && nbNotes <= 5;
                if(!valide1) {
                    Console.WriteLine("Échec de saisie, merci de saisir un chiffre compris entre 3 et 5");
                }
            } while (!valide1);

            Console.ReadKey();
            double[] notes = new double[nbNotes];

        }

    }
}
