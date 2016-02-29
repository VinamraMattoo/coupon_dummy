package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_discount_req")
public class CouponDiscountRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    /**
     * A coupon discount is requested by a user for a beneficiary. A requester may be same as
     * beneficiary in cases where a customer itself is raising the request. A requester may be different from the
     * beneficiary user when coupon is being requested by someone on behalf of a customer user.
     */
    @JoinColumn(name = "requester_id")
    @ManyToOne
    private User requester;

    /**
     * The user who will gain from this coupon discount request
     */
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
     * @see ContextType
     */
    @Column(name = "client_context_type")
    @Enumerated(EnumType.STRING)
    private ContextType clientContextType;

    @Column(name = "total_cost")
    private Double totalCost;

	@Column(name = "latest_updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date latestUpdatedOn;

    @Column(name = "completed")
	private Boolean completed;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CouponDiscountRequestStatus status;

    @Column(name = "source_name", columnDefinition = "varchar(128)")
    private String sourceName;

    /**
     * If the discount request has this context type it indicates that the request is for an appointment
     * within a subscription.
     */
    @Column(name = "within_subscription")
    private Boolean withinSubscription;


    public CouponDiscountRequest() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getLatestUpdatedOn() {
        return latestUpdatedOn;
    }

    public void setLatestUpdatedOn(Date latestUpdatedOn) {
        this.latestUpdatedOn = latestUpdatedOn;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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

        CouponDiscountRequest that = (CouponDiscountRequest) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
