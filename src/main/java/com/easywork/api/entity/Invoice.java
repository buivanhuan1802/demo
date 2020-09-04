package com.easywork.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable, IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "invoice_id", insertable = false, updatable = true)
	@JsonProperty("invoiceId")
	private String invoiceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "customerId")
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "customer_id")
	@JsonProperty("customerId")
	private Customer customer;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	@JsonProperty("dateCreated")
	private Date dateCreated;

	@Column(name = "status")
	@JsonProperty("status")
	private int status;

	@Column(name = "total")
	@JsonProperty("total")
	private Double total;

	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "detailId")
	@JsonIdentityReference(alwaysAsId = true)
	@JsonProperty("detailIds")
	private List<InvoiceDetail> detail;

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<InvoiceDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<InvoiceDetail> detail) {
		this.detail = detail;
	}

	public Invoice(String invoiceId, AppUser user, Customer customer, Date dateCreated, int status, Double total,
			List<InvoiceDetail> detail) {
		super();
		this.invoiceId = invoiceId;
		this.customer = customer;
		this.dateCreated = dateCreated;
		this.status = status;
		this.total = total;
		this.detail = detail;
	}

	public Invoice() {
	}

}
