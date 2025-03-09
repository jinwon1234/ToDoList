package springpr.toyproject.domain;

public enum Status {
    DOING("진행중"), COMPLETED("완료");

    private final String display;

    Status(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
