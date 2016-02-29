package com.portea.cpnen.domain;

/**
 * Defines constants to depict various types of coupons handled by Coupon Management System
 */
public enum CouponApplicationType {


    /**
     *Denotes that coupon code is applicable on every nth purchase of the same product by a user.
     */
    NTH_TIME,

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
     * the coupon code can be used by first 10 users; after which the code becomes unavailable
     */
    ONE_TIME_PER_USER_FIFO,

    /**
     * Denotes that a coupon code is available for use by any user any number of times within
     * the validity period of the coupon
     */
    MANY_TIMES,

    /**
     * Denotes that the coupon code can be used every nth subscription by a user. As an example
     * if n is 3 then code can be used 1st, 3rd, 6th .. 3 * n th time of subscription.
     */
    NTH_TIME_PER_SUBSCRIPTION,

    /**
     * Denotes that the coupon code can be used nth time and beyond. As an example if n is 3
     * then code can be used on 1st, 3rd, 4th, 5th .. n+1 th time of subscription.
     */
    NTH_TIME_AB_PER_SUBSCRIPTION,

    ;

}