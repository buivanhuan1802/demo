package com.easywork.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "customer")
public class Customer implements Serializable, IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "customer_id", insertable = false, updatable = false, nullable = false)
	@JsonProperty("customerId")
	private String customerId;

	@Column(name = "customer_name", insertable = true, updatable = true, length = 50)
	@JsonProperty("customerName")
	private String customerName;

	@Column(name = "phone_number", insertable = true, updatable = true, length = 20)
	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@Column(name = "address", insertable = true, updatable = true, length = 50)
	@JsonProperty("address")
	private String address;

	@Column(name = "active", insertable = true, updatable = true, length = 50)
	@JsonProperty("active")
	private boolean active;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "user_id")
	@JsonProperty("userId")
	private AppUser user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "areaId")
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "area_id")
	@JsonProperty("areaId")
	private Area area;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "invoiceId")
	@JsonIdentityReference(alwaysAsId = true)
	@JsonProperty("invoiceIds")
	private List<Invoice> invoices;

	public Customer() {
		super();
	}

	public Customer(String customerId, String customerName, String phoneNumber, String address, boolean active) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.active = active;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	@Override
	public String toString() {

		try {
			JSONObject object = new JSONObject();
			object.put("customer_id", this.customerId).put("customer_name", this.customerName)
					.put("phone_number", this.phoneNumber).put("address", this.address)
					.put("area", this.area.toString()).put("invoice", new JSONArray(this.invoices));
			return object.toString();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
