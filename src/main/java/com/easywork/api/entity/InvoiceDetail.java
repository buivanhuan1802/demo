package com.easywork.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "invoice_detail")

public class InvoiceDetail implements Serializable, IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", insertable = false, updatable = false)
	private String detailId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "quantity")
	private String quantity;

	@Column(name = "price")
	private Double price;

	@Column(name = "total")
	private Double total;

	 
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getQuantity() {
		
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public InvoiceDetail(String detailId, Invoice invoice, String productName, String quantity, Double price, Double total) {
		super();
		this.detailId = detailId;
		this.invoice = invoice;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
	}

	public InvoiceDetail() {
	}

	

}
