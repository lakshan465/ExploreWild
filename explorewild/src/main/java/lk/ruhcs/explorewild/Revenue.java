package lk.ruhcs.explorewild;

public class Revenue {
    private int id;
    private String name;
    private double Amount;

    public Revenue(int id, String name, double Amount) {
        this.id = id;
        this.name = name;
        this.Amount = Amount;
        
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
        
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return Amount;
    }
    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    
}
