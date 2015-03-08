/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package com.forest.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.forest.model.OrderDetailPK;

/**
 * 
 * @author markito
 */
@Embeddable
public class OrderDetailPKEntity extends OrderDetailPK {

	private static final long serialVersionUID = -1381453765352891148L;

	public OrderDetailPKEntity() {
	}

	public OrderDetailPKEntity(int orderId, int productId) {
		this.orderId = orderId;
		this.productId = productId;
	}

	@Basic(optional = false)
	@Column(name = "ORDER_ID")
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Basic(optional = false)
	@Column(name = "PRODUCT_ID")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) orderId;
		hash += (int) productId;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof OrderDetailPKEntity)) {
			return false;
		}
		OrderDetailPKEntity other = (OrderDetailPKEntity) object;
		if (this.orderId != other.orderId) {
			return false;
		}
		if (this.productId != other.productId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.forest.entity.OrderDetailPK[orderId=" + orderId
				+ ", productId=" + productId + "]";
	}

}
