package de.pentasys.backend.model;

import de.pentasys.backend.model.Partner.GenderEnum;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "partner")
public class PersistentPartner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstname;

	private String lastname;

	private GenderEnum gender;

	@Override
	public String toString() {
		return "persistentPartner [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", gender="
				+ gender + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

}
