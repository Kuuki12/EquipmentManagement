package pl.inzynierka.User.Command;

public class ChangePersonalSettingsCommand {

    private String firstName;
    private String lastName;

    public ChangePersonalSettingsCommand() {
    }

    public ChangePersonalSettingsCommand(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
