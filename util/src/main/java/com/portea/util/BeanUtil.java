package com.portea.util;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Contains utility methods to work on POJO beans
 */
public final class BeanUtil {

    private BeanUtil() {}

    /**
     * Extracts the property for the given property name from the objects contained in the passed
     * collection
     * @param collection Collection that contains the objects from which property value needs to be extracted
     * @param propNameInContainedObj the name of the property for which value needs to be extracted
     * @return Collection of extracted values
     */
    public static Collection collect(final Collection collection, final String propNameInContainedObj) {
        return CollectionUtils.collect(collection, new BeanToPropertyValueTransformer(propNameInContainedObj));
    }
        
    /**
     * Extracts values for given property names from all objects in the specified collection
     * @param collection Collection that contains the objects from where property value needs to be extracted
     * @param propNamesInContainedObj the name of the property for which value needs to be extracted
     * @return Collection of extracted values
     */
    public static Collection collect(final Collection collection, final String[] propNamesInContainedObj) {
        return BeanUtil.collect(collection, propNamesInContainedObj, false);
    }

    /**
     * Extracts values for given property names from all objects in the specified collection
     * @param collection Collection that contains the objects from where property value needs to be extracted
     * @param propNamesInContainedObj the name of the property for which value needs to be extracted
     * @param appendPropName if <code>true</code>, property names will be appended to the output
     * @return Collection of extracted values
     */
    public static Collection collect(final Collection collection, final String[] propNamesInContainedObj,
                                     final boolean appendPropName) {

        final char PROPERTY_SEPARATOR = ';';
        final char PROPERTY_VALUE_SEPARATOR = ':';

        int propNum = propNamesInContainedObj.length;
        ArrayList[] valueLists = new ArrayList[propNamesInContainedObj.length];

        for (int i = 0; i < propNum; i++) {
            valueLists[i] = new ArrayList(); // Initialize

            CollectionUtils.collect(collection,
                    new BeanToPropertyValueTransformer(propNamesInContainedObj[i]),
                    valueLists[i]);
        }

        ArrayList collatedList = new ArrayList();
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < collection.size(); j++) {
            for (int i = 0; i < propNum; i++) {
                if (i != 0) {
                    sb.append(PROPERTY_SEPARATOR);
                }
                if (appendPropName) {
                    sb.append(propNamesInContainedObj[i]);
                    sb.append(PROPERTY_VALUE_SEPARATOR);
                }
                sb.append(valueLists[i].get(j));
            }
            collatedList.add(sb.toString());
            sb.delete(0, sb.length());
        }

        return collatedList;
    }
    
    /**
     * Extracts the property for the given property name from the objects contained in the passed
     * array
     * @param objArray the array that contains the objects from which property value needs to be extracted
     * @param propNameInContainedObj the name of the property for which value needs to be extracted
     * @return Collection of extracted values
     */
    public static Collection collect(final Object[] objArray, final String propNameInContainedObj) {
        return CollectionUtils.collect(Arrays.asList(objArray), new BeanToPropertyValueTransformer(propNameInContainedObj));
    }
    
    /**
     * Extracts values for given property names from all objects in the specified array
     * @param objArray the array that contains the objects from where property value needs to be extracted
     * @param propNamesInContainedObj the name of the property for which value needs to be extracted
     * @return Collection of extracted values
     */
    public static Collection collect(final Object[] objArray, final String[] propNamesInContainedObj) {
        return BeanUtil.collect(Arrays.asList(objArray), propNamesInContainedObj, false);
    }

    /**
     * Extracts values for given property names from all objects in the specified array
     * @param objArray the array that contains the objects from where property value needs to be extracted
     * @param propNamesInContainedObj the name of the property for which value needs to be extracted
     * @param appendPropName if <code>true</code>, property names will be appended to the output
     * @return Collection of extracted values
     */
    public static Collection collect(final Object[] objArray, final String[] propNamesInContainedObj,
                                     final boolean appendPropName) {
        return BeanUtil.collect(Arrays.asList(objArray), propNamesInContainedObj, appendPropName);
    }
}