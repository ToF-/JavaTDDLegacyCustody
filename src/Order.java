public class Order implements Comparable<Order> {
    private String id;
    private int start;
    private int duration;

    private double price;

    public Order(String id, int start, int duree, double price)
    {
        this.id = id;
        this.start = start;  // au format AAAAJJJ par exemple 25 février 2015 = 2015056
        this.duration = duree;
        this.price = price;
    }
    //id de l'ordre 
    public String getId() {
       return this.id;
    }
    // debut
    public int getStart() {
        return this.start;
    }
    // duree
    public int getDuration() {
        return this.duration;
    }
    public double getPrice() {
        return this.price;
    }
    public int compareTo(Order other) {
        return this.start - other.getStart();
    }

    public int getEnd() {
        return this.getStart() + this.getDuration();
    }
}
