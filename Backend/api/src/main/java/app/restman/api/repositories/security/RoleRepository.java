package app.restman.api.repositories.security;

import app.restman.api.entities.security.RoleEnum;
import app.restman.api.entities.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Enum<RoleEnum>> {
}
