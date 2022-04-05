package ma.ensias.winchesters.security;

public enum ApplicationUserPermission {

    REQUEST_RIDE("request_ride"),
    ACCEPT_RIDE("accept_ride"),
    BAN_USER("ban_user");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
