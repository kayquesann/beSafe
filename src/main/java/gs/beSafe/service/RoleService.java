package gs.beSafe.service;

import gs.beSafe.model.Role;
import gs.beSafe.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> listarTodasRoles() {
        return roleRepository.findAll();
    }
}
