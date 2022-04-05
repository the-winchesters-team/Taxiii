package ma.ensias.winchesters.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


import static ma.ensias.winchesters.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Set.of()),
    TAXI_DRIVER(Set.of(ACCEPT_RIDE)),
    CLIENT(Set.of(REQUEST_RIDE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public static ApplicationUserRole fromName(String value){
        for(ApplicationUserRole role : ApplicationUserRole.values()){
            if(role.name().equals(value)){
                return role;
            }
        }
        return null;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> grantedAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return grantedAuthorities;
    }
}
