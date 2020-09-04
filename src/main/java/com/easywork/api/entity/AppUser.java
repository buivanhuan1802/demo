package com.easywork.api.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "app_user")
public class AppUser implements Serializable, IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "user_id", insertable = false, nullable = false, updatable = false, length = 38)
	private String userId;

	@Column(name = "user_name", insertable = true, length = 50, updatable = false)
	private String userName;

	@Column(name = "encrypted_password", insertable = true, length = 50)
	private String encryptedPasword;

	@Column(name = "active", insertable = true)
	private boolean active;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Customer> customer;

	public AppUser() {

	}

	public AppUser(String userId, String userName, String encryptedPasword, boolean active) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.encryptedPasword = encryptedPasword;
		this.active = active;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncryptedPasword() {
		return encryptedPasword;
	}

	public void setEncryptedPasword(String encryptedPasword) {
		this.encryptedPasword = encryptedPasword;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		try {
			JSONObject object = new JSONObject();
			object.put("user_id", this.userId).put("user_name", this.userName);
			return object.toString();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
