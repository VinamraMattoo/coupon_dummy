package com.portea.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanUtilTest {

    public class TestObject {

        private final int id;
        private final String desc;

        public TestObject(int id, String desc) {
            this.id = id;
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public String getDesc() {
            return desc;
        }
    }

    private List<TestObject> testObjectList;
    private TestObject[] testObjectArray;

    @Before
    public void setup() {
        
        TestObject toOne =  new TestObject(145, "First");
        TestObject toTwo = new TestObject(947, "Second");
        
        testObjectList = new ArrayList<>();
        testObjectList.add(toOne);
        testObjectList.add(toTwo);
        
        testObjectArray = new TestObject[2];
        testObjectArray[0] = toOne;
        testObjectArray[1] = toTwo;
    }

    @Test
    public void testSinglePropertyCollectFromList() {
        String expectedOutput = "145, 947";
        Collection resultCollection = BeanUtil.collect(testObjectList, "id");
        Assert.assertTrue(resultCollection.toString().contains(expectedOutput));
    }

    @Test
    public void testMultiplePropertyCollectFromList() {
        String expectedOutput = "145;First, 947;Second";
        Collection resultCollection = BeanUtil.collect(testObjectList, new String[] {"id", "desc"});
        Assert.assertTrue(resultCollection.toString().contains(expectedOutput));
    }

    @Test
    public void testMultiplePropertyCollectWNameFromList() {
        String expectedOutput = "id:145;desc:First, id:947;desc:Second";
        Collection resultCollection = BeanUtil.collect(testObjectList, new String[] {"id", "desc"}, true);
        Assert.assertTrue(resultCollection.toString().contains(expectedOutput));
    }
    
    @Test
    public void testSinglePropertyCollectFromArray() {
        String expectedOutput = "145, 947";
        Collection resultCollection = BeanUtil.collect(testObjectArray, "id");
        Assert.assertTrue(resultCollection.toString().contains(expectedOutput));
    }

    @Test
    public void testMultiplePropertyCollectFromArray() {
        String expectedOutput = "145;First, 947;Second";
        Collection resultCollection = BeanUtil.collect(testObjectArray, new String[] {"id", "desc"});
        Assert.assertTrue(resultCollection.toString().contains(expectedOutput));
    }

    @Test
    public void testMultiplePropertyCollectWNameFromArray() {
        String expectedOutput = "id:145;desc:First, id:947;desc:Second";
        Collection resultCollection = BeanUtil.collect(testObjectArray, new String[] {"id", "desc"}, true);
        Assert.assertTrue(resultCollection.toString().contains(expectedOutput));
    }
    
    /**
     * A hacked approach (and one that adds zero-value other than having applied something), to test
     * the constructor which leads to complete code coverage. Without this hack, code coverage tools
     * may report that class constructor has not been tested
     */
    @Test
    public void testBeanUtilConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<BeanUtil> constructor = BeanUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        BeanUtil instance = constructor.newInstance();
        Assert.assertNotNull(instance);
    }
}