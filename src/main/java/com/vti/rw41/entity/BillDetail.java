package com.vti.rw41.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class BillDetail extends BaseEntity implements Serializable {
    @Id
    private Integer productId;

    @Id
    private Integer billId;

    private Double productPrice;

    private  Integer quantity;
}
