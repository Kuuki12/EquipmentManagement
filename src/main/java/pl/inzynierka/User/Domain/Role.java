package pl.inzynierka.User.Domain;

public enum Role {

    ROLE_CLIENT("Klient"),
    ROLE_ADMIN("Administrator"),
    ROLE_REPAIRMAN("Konserwator");

    private String name;

    Role(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
