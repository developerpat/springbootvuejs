package de.pentasys.backend.repository;

import de.pentasys.backend.model.PersistentPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface PartnerRespository extends JpaRepository<PersistentPartner, Long>{
}
