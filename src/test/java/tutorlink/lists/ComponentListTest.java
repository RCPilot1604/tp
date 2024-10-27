//@@author jinzihan2002
package tutorlink.lists;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import tutorlink.model.component.Assignment;
import tutorlink.model.component.Component;
import tutorlink.model.component.Exam;
import tutorlink.model.exceptions.ComponentNotFoundException;
import tutorlink.model.exceptions.DuplicateComponentException;
import tutorlink.model.lists.ComponentList;

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
        comp1 = new Assignment("Homework 1", 30, 0.1);
        comp2 = new Assignment("Homework 2", 30, 0.1);
        comp3 = new Exam("Finals", 100, 0.5);
    }

    @Test
    void addComponent_addOneComponent_success() {
        componentList.addComponent(comp1);
        assertEquals(1, componentList.getComponentArrayList().size());
    }

    @Test
    void addComponent_addDuplicateComponent_exceptionThrown() {
        componentList.addComponent(comp1);
        Component duplicateComp = new Assignment("Homework 1", 30, 0.1);
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
        String expectedResult = "1: Assignment [name=homework 1, maxScore=30.0, weight=0.1]\n" +
                "\t2: Assignment [name=homework 2, maxScore=30.0, weight=0.1]";
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
    void toString_test() {
        componentList.addComponent(comp1);
        componentList.addComponent(comp3);
        String expectedResult = "1: Assignment [name=homework 1, maxScore=30.0, weight=0.1]\n" +
                "\t2: Exam [name=finals, maxScore=100.0, weight=0.5]";
        assertEquals(expectedResult, componentList.toString());
    }
}
//@@author
