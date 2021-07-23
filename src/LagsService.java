import com.opencsv.CSVWriter;

import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

class LagsService {
    private ArrayList<Order> listOrder = new ArrayList<Order>();
    private boolean debug;

    // lit le fihier des orders et calcule le CA
    public void getFichierOrder(String fileName)
    {
        try{

            for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
                String[] champs = line.split(";");
                String chp1 = champs[0];
                int chp2 = Integer.parseInt(champs[1]);
                int champ3 = Integer.parseInt(champs[2]);
                double chp4 = Double.parseDouble(champs[3]);
                Order order = new Order(chp1, chp2, champ3, chp4);
                listOrder.add(order);

            }
        }
        catch (IOException e)
        {
            System.out.println("FICHIER ORDRES.CSV NON TROUVE. CREATION FICHIER.");
            writeOrdres(fileName);
        }
    }

    // écrit le fichier des orders
    void writeOrdres(String nomFich)
    {
        List<String> lines = new ArrayList<String>();
        for(int i = 0; i< listOrder.size(); i++) {
            Order order = listOrder.get(i);
            String ligneCSV = new String();
            ligneCSV = order.getId() + ";" + Integer.toString(order.getStart()) +";"+Integer.toString(order.getDuration())+";"+Double.toString(order.getPrice());
            lines.add(ligneCSV);
        }
        try{
            // bug here : CREATE or APPEND mode for file ?
            Files.write(Paths.get(nomFich), lines,StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        }
        catch (IOException e)
        {
            System.out.println("PROBLEME AVEC FICHIER");
        }
    }


    // affiche la liste des orders
    public void liste()
    {
        Collections.sort(listOrder, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getStart() - o2.getStart(); // use your logic, Luke
            }
        });
        System.out.println("LISTE DES ORDRES\n");
        System.out.format("%8s %8s %5s %13s", "ID", "DEBUT", "DUREE", "PRIX\n");
        System.out.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
        for(int i = 0; i< listOrder.size(); i++) {
            Order order = listOrder.get(i);
            afficherOrdre(order);
        }
        System.out.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
    }

    public void afficherOrdre(Order order)
    {
        System.out.format("%8s %8d %5d %10.2f\n", order.getId(), order.getStart(), order.getDuration(), order.getPrice());

    }
    // Ajoute un ordre; le CA est recalculé en conséquence
    public void ajouterOrdre()
    {
        System.out.println("AJOUTER UN ORDRE");
        // should be FORMAT = ID;DEBUT;DUREE;PRIX
        System.out.println("FORMAT = ID;DEBUT;FIN;PRIX");
        String line = System.console().readLine().toUpperCase();
        String[] champs = line.split(";");
        String chp1 = champs[0];
        int chp2 = Integer.parseInt(champs[1]);
        int champ3 = Integer.parseInt(champs[2]);
        double chp4 = Double.parseDouble(champs[3]);
        Order order = new Order(chp1, chp2, champ3, chp4);
        listOrder.add(order);
        writeOrdres("orders.csv");
    }
    // MAJ du fichier
    public void suppression()
    {
        System.out.println("SUPPRIMER UN ORDRE");
        System.out.println("ID:");
        String id = System.console().readLine().toUpperCase();
        for (Iterator<Order> iter = listOrder.listIterator(); iter.hasNext(); ) {
            Order o = iter.next();
            if (o.getId().equals(id)) {
                iter.remove();
            }
        }
        writeOrdres("ORDRES.CSV");
    }

    private double income(List<Order> orders)
    {
        if (orders.size() == 0)
            return 0.0;
        Order firstOrder = orders.get(0);
        double incomeWithCompatibleOrders = firstOrder.getPrice()+ income(getCompatibleOrders(firstOrder));
        double incomeWithFollowingOrders = income(getFollowingOrders(orders));
        if(debug) {
            System.out.format("%10.2f\n", Math.max(incomeWithCompatibleOrders, incomeWithFollowingOrders));
        }
        else
            System.out.print(".");
        return Math.max(incomeWithCompatibleOrders,  incomeWithFollowingOrders);
    }

    private List<Order> getFollowingOrders(List<Order> orders) {
        List<Order> followingOrders = new ArrayList<Order>();
        for(int i = 1; i< orders.size(); i++) {
             followingOrders.add(orders.get(i));
        }
        return followingOrders;
    }

    private List<Order> getCompatibleOrders(Order firstOrder) {
        List<Order> compatibleOrders = new ArrayList<Order>();
        for (Iterator<Order> iter = listOrder.listIterator(); iter.hasNext(); ) {
            Order order = iter.next();
            if (order.getStart() >= firstOrder.getEnd() ) {
                compatibleOrders.add(order);
            }
        }
        return compatibleOrders;
    }

    public void computeIncome(boolean debug)
    {
        this.debug = debug;
        System.out.println("CALCUL CA..");
        double ca = getIncome();
        System.out.format("CA: %10.2f\n", ca);
    }

    public double getIncome() {
        Collections.sort(listOrder, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getStart() - o2.getStart();
            }
        });
        return income(listOrder);
    }

    // testing purpose : getter to the internal list of orders
    public ArrayList<Order> getOrders() {
        return this.listOrder;
    }

    public void saveCSV(CSVWriter csvWriter) {
    }
}

