package de.pentasys.backend.converter;

import de.pentasys.backend.model.CreatePartner;
import de.pentasys.backend.model.Partner;
import de.pentasys.backend.model.Partner.GenderEnum;
import de.pentasys.backend.model.PersistentPartner;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequestScope
public class PartnerConverter {

	public Partner convertToPartner(PersistentPartner persistentPartner) {
		Partner partner = new Partner();
		partner.setFirstName(persistentPartner.getFirstname());
		partner.setLastName(persistentPartner.getLastname());
		partner.setId((int) persistentPartner.getId());
		partner.setGender(persistentPartner.getGender());
		return partner;
	}

	public PersistentPartner converToPersistentPartner(@Valid CreatePartner partner) {
		PersistentPartner persistentPartner = new PersistentPartner();
		persistentPartner.setFirstname(partner.getFirstName());
		persistentPartner.setLastname(partner.getLastName());
		persistentPartner.setGender(GenderEnum.valueOf(partner.getGender().name()));
		return persistentPartner;
	}
}
