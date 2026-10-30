package truonggg.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.model.Role;
import truonggg.repository.RoleRepository;
import truonggg.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	@Override
	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		return this.roleRepository.findAll();
	}

	@Override
	public Role save(Role newRole) {
		// TODO Auto-generated method stub
		return this.roleRepository.save(newRole);
	}

	@Override
	public Optional<Role> findById(Integer id) {
		// TODO Auto-generated method stub
		return this.roleRepository.findById(id);
	}

}
