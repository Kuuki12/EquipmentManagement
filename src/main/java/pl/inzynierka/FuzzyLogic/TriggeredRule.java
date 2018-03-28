package pl.inzynierka.FuzzyLogic;

public class TriggeredRule {
    private Integer number;
    private String text;

    public TriggeredRule(Integer number, String text) {
        this.number = number+1;
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }
}
