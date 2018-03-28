package pl.inzynierka.User.Command;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordOwnAccountCommand {

    private String username;
    @NotEmpty(message = "Pole nie może być puste")
    private String newPassword;
    @NotEmpty(message = "Pole nie może być puste")
    private String repeatedNewPassword;

    public ChangePasswordOwnAccountCommand() {
    }

    public ChangePasswordOwnAccountCommand(String newPassword, String repeatedNewPassword) {
        this.newPassword = newPassword;
        this.repeatedNewPassword = repeatedNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

    public void setRepeatedNewPassword(String repeatedNewPassword) {
        this.repeatedNewPassword = repeatedNewPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
