package models.bean;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable {
	private int id;
	private String familyName;
	private String givenName;
	private String postalCode;
	private String address;
	private String tel;
	private String email;
	private Date birthday;
	private Date subscribedAt;
	private Date unsubscribedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getSubscribedAt() {
		return subscribedAt;
	}

	public void setSubscribedAt(Date subscribedAt) {
		this.subscribedAt = subscribedAt;
	}

	public Date getUnsubscribedAt() {
		return unsubscribedAt;
	}

	public void setUnsubscribedAt(Date unsubscribedAt) {
		this.unsubscribedAt = unsubscribedAt;
	}
}
