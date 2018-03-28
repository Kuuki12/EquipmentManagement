package pl.inzynierka.Equipment.Query;

public class EquipmentsQuery {
    private Long id;
    private String name;
    private String owner;

    public EquipmentsQuery() {
    }

    public EquipmentsQuery(Long id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
