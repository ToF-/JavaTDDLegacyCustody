public class Lags {
    static final boolean debug = true;

    // ==================
    // fonction principale
    // ===================

    public static void main(String[] args) {
        LagsService service = new LagsService();
        service.getFichierOrder("ORDRES.CSV");
        boolean flag = false;
        // tant que ce n'est pas la fin du programme
        // while it's not the end of the program
        while (!flag) {
            // met la commande à Z
            // set command to Z
            char commande = 'Z';
            while (commande != 'A' && commande != 'L' && commande != 'S' && commande != 'Q' && commande != 'C') {
                System.out.println("A)JOUTER UN ORDRE  L)ISTER   C)ALCULER CA  S)UPPRIMER  Q)UITTER");
                try {
                    char keyInfo = (char) System.in.read();
                    commande = Character.toUpperCase(keyInfo);
                    System.out.print(commande);
                } catch (java.io.IOException e) {
                    System.out.print("IO Exception");
                }
                switch (commande) {
                    case 'Q': {
                        flag = true;
                        break;
                    }
                    case 'L': {
                        service.liste();
                        break;
                    }
                    case 'A': {
                        service.ajouterOrdre();
                        break;
                    }
                    case 'S': {
                        service.suppression();
                        break;
                    }
                    case 'C': {
                        service.computeIncome(debug);
                        break;
                    }
                }

            }
        }
    }
}