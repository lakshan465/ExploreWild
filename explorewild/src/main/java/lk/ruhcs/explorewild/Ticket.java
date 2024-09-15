package lk.ruhcs.explorewild;

public class Ticket {
    private String firstName;
    private String lastName;
    private int numberOfParent;
    private int numberOfChild;

    private double total;
    private double payAmount;
    private double balance;

    public Ticket(String firstName, String lastName, int numberOfParent, int numberOfChild, double total, double payAmount, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfParent = numberOfParent;
        this.numberOfChild = numberOfChild;
        this.total = total;
        this.payAmount = payAmount;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNumberOfParent() {
        return numberOfParent;
    }

    public int getNumberOfChild() {
        return numberOfChild;
    }

    public double getTotal() {
        return total;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public double getBalance() {
        return balance;
    }
}
