package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_disc_req_audit")
public class CouponDiscountRequestAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
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

    @Column(name = "client_context_id", columnDefinition = "varchar(32)")
    private String clientContextId;

    @Column(name = "client_context_type")
    @Enumerated(EnumType.STRING)
    private ContextType clientContextType;

    @Column(name = "total_cost")
    private Double totalCost;

	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CouponDiscountRequestStatus status;

    @Column(name = "source_name", columnDefinition = "varchar(128)")
    private String sourceName;

    @Column(name = "within_subscription")
    private Boolean withinSubscription;

	public CouponDiscountRequestAudit() {}

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public CouponDiscountRequestStatus getStatus() {
        return status;
    }

    public void setStatus(CouponDiscountRequestStatus status) {
        this.status = status;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Boolean getWithinSubscription() {
        return withinSubscription;
    }

    public void setWithinSubscription(Boolean withinSubscription) {
        this.withinSubscription = withinSubscription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponDiscountRequestAudit that = (CouponDiscountRequestAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
