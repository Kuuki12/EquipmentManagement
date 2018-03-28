package pl.inzynierka.Priority;

public enum PriorityStatus {
    VERY_LOW("Bardzo niski"),
    LOW("Niski"),
    MEDIUM("Średni"),
    HIGH("Wysoki"),
    VERY_HIGH("Bardzo wysoki");

    private String name;

    PriorityStatus(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
