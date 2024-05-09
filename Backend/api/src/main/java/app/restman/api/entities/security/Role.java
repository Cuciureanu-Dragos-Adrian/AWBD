package app.restman.api.entities.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Role {
    @Id
    private RoleEnum roleEnum;

    @OneToMany (mappedBy = "role")
    private Set<User> users;

    public Role(RoleEnum role) {
        this.roleEnum = role;
    }

    @Override
    public String toString() {
        return getRoleEnumAsString(roleEnum);
    }

    public List<? extends GrantedAuthority> getRoleAuthorities(){
        var roles = new ArrayList<String>();
        if (roleEnum == RoleEnum.User)
            roles.add(getRoleEnumAsString(roleEnum));
        else if(roleEnum == RoleEnum.Staff) {
            roles.add(getRoleEnumAsString(RoleEnum.User));
            roles.add(getRoleEnumAsString(RoleEnum.Staff));
        }
        else if(roleEnum == RoleEnum.Admin){
            roles.add(getRoleEnumAsString(RoleEnum.User));
            roles.add(getRoleEnumAsString(RoleEnum.Staff));
            roles.add(getRoleEnumAsString(RoleEnum.Admin));
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Create SimpleGrantedAuthority with "ROLE_" prefix
                .collect(Collectors.toList());
    }

    public static String getRoleEnumAsString(RoleEnum role){
        if (role == RoleEnum.User)
            return "USER";
        else if (role == RoleEnum.Staff)
            return "STAFF";
        else if (role == RoleEnum.Admin)
            return "ADMIN";
        else
            return "UNKNOWN";
    }
}
