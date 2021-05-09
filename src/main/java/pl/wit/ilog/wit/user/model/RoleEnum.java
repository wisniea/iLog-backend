package pl.wit.ilog.wit.user.model;

public enum RoleEnum {
    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

    public final String value;

    RoleEnum(final String value) {
        this.value = value;
    }
}
