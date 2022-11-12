public class process {
    private String name;
    private int bt;
    private int at;
    private int id;

    public process(int num, String n, int burst, int arrival) {
        name = n;
        bt = burst;
        at = arrival;
        id = num;
    }

    String getName() {
        return name;
    }

    int getBurst() {
        return bt;
    }

    int getArrive() {
        return at;
    }

    int getID() {
        return id;
    }
}
