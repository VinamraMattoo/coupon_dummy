package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon")
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name",columnDefinition = "varchar(128)")
	private String name;
	
	@Column(name = "description",columnDefinition = "varchar(512)")
	private String description;
	
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@JoinColumn(name = "created_by")
    @ManyToOne
	private User createdBy;
	
	@Column(name = "deactivated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deactivatedOn;
	
	@JoinColumn(name = "deactivated_by")
    @ManyToOne
	private User deactivatedBy;
	
	@Column(name = "inclusive")
	private Boolean inclusive;
	
	@Column(name = "channel_name",columnDefinition = "varchar(128)")
	private String channelName;
		
	@Enumerated(EnumType.STRING)
    @Column(name = "application_type")
	private CouponApplicationType applicationType;
	
	@Column(name = "applicable_from")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applicableFrom;
	
	@Column(name = "applicable_till")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applicableTill;
	
	@Column(name = "applicable_use_count")
	private Integer applicableUseCount;
	
	@Column(name = "trans_val_min")
	private Integer transactionMinValue;
	
	@Column(name = "trans_val_max")
	private Integer transactionMaxValue;
	
	@Column(name = "prod_count_min")
	private Integer productMinCount;
	
	@Column(name = "prod_count_max")
	private Integer productMaxCount;
	
	@Column(name = "prod_count_span")
	private Boolean productCountSpanApplicable;

    @JoinColumn(name = "published_by")
    @ManyToOne
    private User publishedBy;

    @Column(name = "published_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedOn;
	
	public Coupon() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDeactivatedOn() {
        return deactivatedOn;
    }

    public void setDeactivatedOn(Date deactivatedOn) {
        this.deactivatedOn = deactivatedOn;
    }

    public User getDeactivatedBy() {
        return deactivatedBy;
    }

    public void setDeactivatedBy(User deactivatedBy) {
        this.deactivatedBy = deactivatedBy;
    }

    public Boolean isInclusive() {
        return inclusive;
    }

    public void setInclusive(Boolean inclusive) {
        this.inclusive = inclusive;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public CouponApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(CouponApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Date getApplicableFrom() {
        return applicableFrom;
    }

    public void setApplicableFrom(Date applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public Date getApplicableTill() {
        return applicableTill;
    }

    public void setApplicableTill(Date applicableTill) {
        this.applicableTill = applicableTill;
    }

    public Integer getApplicableUseCount() {
        return applicableUseCount;
    }

    public void setApplicableUseCount(Integer applicableUseCount) {
        this.applicableUseCount = applicableUseCount;
    }

    public Integer getTransactionMinValue() {
        return transactionMinValue;
    }

    public void setTransactionMinValue(Integer transactionMinValue) {
        this.transactionMinValue = transactionMinValue;
    }

    public Integer getTransactionMaxValue() {
        return transactionMaxValue;
    }

    public void setTransactionMaxValue(Integer transactionMaxValue) {
        this.transactionMaxValue = transactionMaxValue;
    }

    public Integer getProductMinCount() {
        return productMinCount;
    }

    public void setProductMinCount(Integer productMinCount) {
        this.productMinCount = productMinCount;
    }

    public Integer getProductMaxCount() {
        return productMaxCount;
    }

    public void setProductMaxCount(Integer productMaxCount) {
        this.productMaxCount = productMaxCount;
    }

    public Boolean isProductCountSpanApplicable() {
        return productCountSpanApplicable;
    }

    public void setProductCountSpanApplicable(Boolean productCountSpanApplicable) {
        this.productCountSpanApplicable = productCountSpanApplicable;
    }

    public User getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(User publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }
}