package pl.inzynierka.Progress;

public enum ProgressStatus {

    WAITING("Oczekujące"),
    DOING("Aktualnie konserwowane"),
    DONE("Zakończona konserwacja");

    private String name;

    ProgressStatus(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
