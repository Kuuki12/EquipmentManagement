package pl.inzynierka.User.Command;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordCommand {

    private Long id;
    @NotEmpty(message = "Pole nie może być puste")
    private String newPassword;
    @NotEmpty(message = "Pole nie może być puste")
    private String repeatedNewPassword;

    public ChangePasswordCommand() {
    }

    public ChangePasswordCommand(Long id) {
        this.id = id;
    }

    public ChangePasswordCommand(String newPassword, String repeatedNewPassword) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
