package com.portea.cpnen.domain;

/**
 * An enum that specifies various constants to classify different types of products
 */
public enum ProductType {

    /**
     * Service denotes a product that may be bought by a customer. A service that is marked as a sub-service cannot be
     * bought independently.
     */
    SERVICE,

    /**
     * Package denotes a combination of services that can be bought as a whole by a customer.
     */
    PACKAGE,

    ;
}
