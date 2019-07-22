package de.pentasys.backend.controller;

import de.pentasys.backend.converter.PartnerConverter;
import de.pentasys.backend.exception.ResourceNotFoundException;
import de.pentasys.backend.model.CreatePartner;
import de.pentasys.backend.model.Partner;
import de.pentasys.backend.model.Partner.GenderEnum;
import de.pentasys.backend.model.PersistentPartner;
import de.pentasys.backend.repository.PartnerRespository;
import javax.validation.Valid;

import javax.xml.ws.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import de.pentasys.backend.api.PartnerApi;

@Controller
public class PartnerController implements PartnerApi {

	private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

	@Autowired
	PartnerRespository repository;

	@Autowired
	PartnerConverter converter;

	@Override
	public ResponseEntity<Partner> getPartnerById(@PathVariable("Id") Long id) {
		PersistentPartner searchedPartner = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Partner", "id", id));
		Partner responsePartner = converter.convertToPartner(searchedPartner);
		return new ResponseEntity<Partner>(responsePartner, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Long> addPartner(@Valid @RequestBody CreatePartner partner) {
		PersistentPartner createdPartner = converter.converToPersistentPartner(partner);
		PersistentPartner save = repository.save(createdPartner);
		if (save.getId() > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", UriComponentsBuilder.fromPath("/partners/" + save.getId()).build().toUriString());
			return new ResponseEntity<Long>(save.getId(), headers, HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<Void> updatePartner(@PathVariable("Id") Long id, @Valid @RequestBody CreatePartner partner) {
		PersistentPartner updatedPartner = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Partner", "id", id));
		updatedPartner.setFirstname(partner.getFirstName());
		updatedPartner.setGender(GenderEnum.valueOf(partner.getGender().name()));
		updatedPartner.setLastname(partner.getLastName());
		PersistentPartner save = repository.save(updatedPartner);
		return ResponseEntity.created(UriComponentsBuilder.fromPath("/partners/" + save.getId()).build().toUri()).build();
	}
}
