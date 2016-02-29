package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_discount")
public class CouponDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "coupon_disc_req_id")
    private CouponDiscountRequest couponDiscountRequest;

    @JoinColumn(name = "requester_id")
    @ManyToOne
    private User requester;

    @JoinColumn(name = "beneficiary_id")
    @ManyToOne
    private User beneficiary;

    @JoinColumn(name = "patient_brand_id")
    @ManyToOne
    private Brand patientBrand;

    @JoinColumn(name = "area_id")
    @ManyToOne
    private Area areaId;

    @JoinColumn(name = "referrer_id")
    @ManyToOne
    private Referrer referrerId;

    /**
     * A coupon discount request is in a given client context or a transaction. This field captures the identifier
     * of this context. An example context is subscription-creation which will result in creation of a subscription
     * which will have an id
     */
    @Column(name = "client_context_id", columnDefinition = "varchar(32)")
    private String clientContextId;

    /**
     * A coupon discount request is in a given client context or a transaction. This field captures the type of this
     * context. Example context types is {@link ContextType#APPOINTMENT}
     *
     * @see ContextType
     */
    @Column(name = "client_context_type")
    @Enumerated(EnumType.STRING)
    private ContextType clientContextType;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    public CouponDiscount() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponDiscountRequest getCouponDiscountRequest() {
        return couponDiscountRequest;
    }

    public void setCouponDiscountRequest(CouponDiscountRequest couponDiscountRequest) {
        this.couponDiscountRequest = couponDiscountRequest;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(User beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getClientContextId() {
        return clientContextId;
    }

    public void setClientContextId(String clientContextId) {
        this.clientContextId = clientContextId;
    }

    public ContextType getClientContextType() {
        return clientContextType;
    }

    public void setClientContextType(ContextType clientContextType) {
        this.clientContextType = clientContextType;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Area getAreaId() {
        return areaId;
    }

    public void setAreaId(Area areaId) {
        this.areaId = areaId;
    }

    public Referrer getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(Referrer referrerId) {
        this.referrerId = referrerId;
    }

    public Brand getPatientBrand() {
        return patientBrand;
    }

    public void setPatientBrand(Brand patientBrand) {
        this.patientBrand = patientBrand;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponDiscount that = (CouponDiscount) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}