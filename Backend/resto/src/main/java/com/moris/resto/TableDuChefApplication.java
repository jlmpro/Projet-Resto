package com.moris.resto;

import com.moris.resto.entity.Role;
import com.moris.resto.enums.TypeDeRole;
import com.moris.resto.enums.TypePermission;
import com.moris.resto.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.security.Permission;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaAuditing
public class TableDuChefApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;

    public TableDuChefApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args)  {
		SpringApplication.run(TableDuChefApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (TypeDeRole typeDeRole : TypeDeRole.values()) {
			roleRepository.findByLibelle(typeDeRole)
					.ifPresentOrElse(
							role -> System.out.println("🔹 Le rôle " + typeDeRole.name() + " existe déjà."),
							() -> {
								Role role = new Role();
								role.setLibelle(typeDeRole);
								roleRepository.save(role);
								System.out.println("✔ Rôle " + typeDeRole.name() + " créé avec succès !");
							}
					);
		}
	}




}

