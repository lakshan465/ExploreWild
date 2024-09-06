package lk.ruhcs.explorewild;

public class AnimalData {

    //encapsulation
    private Integer AnimalId;
    private Integer CageId;
    private String Sex;
    private String Type;

    public AnimalData(int  animalId,  String type, int cageId, String sex  ) {
        AnimalId = animalId;
        CageId = cageId;
        Sex = sex;
        Type = type;
    }

    public Integer getAnimalId() {
        return AnimalId;
    }

    public Integer getCageId() {
        return CageId;
    }

    public String getSex() {
        return Sex;
    }

    public String getType() {
        return Type;
    }
}
