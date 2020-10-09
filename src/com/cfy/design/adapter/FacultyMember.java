package com.cfy.design.adapter;

import java.util.Objects;

public class FacultyMember implements IFacultyMember {

	private String identification;
	private String name;
	private String ContactTelephone;

	public FacultyMember() {
	}

	public FacultyMember(String identification, String name, String contactTelephone) {
		this.identification = identification;
		this.name = name;
		ContactTelephone = contactTelephone;
	}

	@Override
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getContactTelephone() {
		return ContactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		ContactTelephone = contactTelephone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FacultyMember that = (FacultyMember) o;
		return Objects.equals(identification, that.identification) && Objects.equals(name, that.name)
				&& Objects.equals(ContactTelephone, that.ContactTelephone);
	}

	@Override
	public String toString() {
		return "FacultyMember{" + "identification='" + identification + '\'' + ", name='" + name + '\''
				+ ", ContactTelephone='" + ContactTelephone + '\'' + '}';
	}
}