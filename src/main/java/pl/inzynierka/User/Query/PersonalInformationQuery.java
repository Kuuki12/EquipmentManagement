package pl.inzynierka.User.Query;

public class PersonalInformationQuery {

    private String firstName;
    private String lastName;

    public PersonalInformationQuery(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
