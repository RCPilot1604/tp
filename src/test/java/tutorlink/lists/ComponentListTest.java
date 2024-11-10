//@@author jinzihan2002
package tutorlink.lists;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import tutorlink.component.Component;
import tutorlink.exceptions.ComponentNotFoundException;
import tutorlink.exceptions.DuplicateComponentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComponentListTest {
    private ComponentList componentList;
    private Component comp1;
    private Component comp2;
    private Component comp3;

    @BeforeEach
    void setup() {
        componentList = new ComponentList();
        comp1 = new Component("Homework 1", 30, 10);
        comp2 = new Component("Homework 2", 30, 10);
        comp3 = new Component("Finals", 100, 50);
    }

    @Test
    void addComponent_addOneComponent_success() {
        componentList.addComponent(comp1);
        assertEquals(1, componentList.getComponentArrayList().size());
    }

    @Test
    void addComponent_addDuplicateComponent_exceptionThrown() {
        componentList.addComponent(comp1);
        Component duplicateComp = new Component("Homework 1", 30, 10);
        assertThrows(DuplicateComponentException.class, () -> componentList.addComponent(duplicateComp));
    }

    @Test
    void deleteComponent_deleteOneComponent_success() {
        componentList.addComponent(comp3);
        componentList.deleteComponent(comp3);
        assertEquals(0, componentList.getComponentArrayList().size());
    }

    @Test
    void deleteComponent_deleteUnknownComponent_exceptionThrown() {
        componentList.addComponent(comp2);
        assertThrows(ComponentNotFoundException.class, () -> componentList.deleteComponent(comp1));
    }

    @Test
    void findComponent_findExisting_success() {
        componentList.addComponent(comp1);
        componentList.addComponent(comp2);
        componentList.addComponent(comp3);
        ComponentList filteredList = componentList.findComponent("homework");
        assertEquals(2, filteredList.size());
        String expectedResult = "\t1: Homework 1 (maxScore: 30.0, weight: 10%)\n" +
                "\t2: Homework 2 (maxScore: 30.0, weight: 10%)";
        assertEquals(expectedResult, filteredList.toString());
    }

    @Test
    void findComponent_findNotInList_exceptionThrown() {
        componentList.addComponent(comp1);
        componentList.addComponent(comp2);
        componentList.addComponent(comp3);
        assertThrows(ComponentNotFoundException.class, () -> componentList.findComponent("midterm"));
    }

    @Test
    void componentList_toString_success() {
        componentList.addComponent(comp1);
        componentList.addComponent(comp3);
        String expectedResult = "\t1: Homework 1 (maxScore: 30.0, weight: 10%)\n" +
                "\t2: Finals (maxScore: 100.0, weight: 50%)";
        assertEquals(expectedResult, componentList.toString());
    }
}
//@@author
