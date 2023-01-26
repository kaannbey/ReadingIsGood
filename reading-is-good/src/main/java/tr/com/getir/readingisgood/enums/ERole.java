package tr.com.getir.readingisgood.enums;

public enum ERole {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String role;

    ERole(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
