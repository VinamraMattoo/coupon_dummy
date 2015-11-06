package com.portea.cpnen.domain;

/**
 * Defines constants to depict various types of coupons handled by Coupon Management System
 */
public enum CouponApplicationType {
    /**
     * Denotes that coupon code is available to first-time buyers of any product
     */
    FIRST_TIME,

    /**
     * Denotes that a coupon code can be used by any one user. After a code has been used,
     * it is not available for use again. For such type of coupons, typically multiple
     * one-time codes will be generated.
     */
    ONE_TIME,

    /**
     * Denotes that a coupon code can be used only once by any user. Once a code has been
     * used by a user, the code is not available to the same user again.
     */
    ONE_TIME_PER_USER,

    /**
     * Denotes that a coupon code can be used by any users in first-come-first-serve. As
     * an example is the value of 10 has been set on the coupon as the max usage count, then
     * the coupon code can be used by first 10 users; after which the code becomes available
     */
    ONE_TIME_PER_USER_FIFO,

    /**
     * Denotes that a coupon code is available for use by any user any numner of times within
     * the validity period of the coupon
     */
    MANY_TIMES;
}