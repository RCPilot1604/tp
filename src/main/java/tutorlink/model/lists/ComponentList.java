package tutorlink.model.lists;

import tutorlink.model.component.Component;
import tutorlink.model.exceptions.ComponentNotFoundException;
import tutorlink.model.exceptions.DuplicateComponentException;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a list of components.
 */
public class ComponentList {
    private static final String ERROR_COMPONENT_NOT_FOUND = "Error! Component %s does not exist in the list!";
    private static final String ERROR_DUPLICATE_COMPONENT = "Error! Component already exists in the list!";

    private ArrayList<Component> componentArrayList;

    public ComponentList() {
        this.componentArrayList = new ArrayList<>();
    }

    public ComponentList findComponent(String name) throws ComponentNotFoundException {
        ComponentList filteredList = new ComponentList();
        filteredList.componentArrayList = componentArrayList
                .stream()
                .filter(comp -> comp.getName().toUpperCase().contains(name.toUpperCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filteredList.componentArrayList.isEmpty()) {
            throw new ComponentNotFoundException(String.format(ERROR_COMPONENT_NOT_FOUND, name));
        }
        return filteredList;
    }

    public void addComponent(Component component) throws DuplicateComponentException {
        for (Component comp : componentArrayList) {
            if (comp.equals(component)) {
                throw new DuplicateComponentException(ERROR_DUPLICATE_COMPONENT);
            }
        }
        componentArrayList.add(component);
    }

    public void deleteComponent(Component component) throws ComponentNotFoundException {
        for (Component comp : componentArrayList) {
            if (comp.equals(component)) {
                componentArrayList.remove(comp);
                return;
            }
        }
        throw new ComponentNotFoundException(String.format(ERROR_COMPONENT_NOT_FOUND, component));
    }

    @Override
    public String toString() {
        return IntStream.range(0, componentArrayList.size())
                .mapToObj(i -> (i + 1) + ": " + componentArrayList.get(i))
                .collect(Collectors.joining("\n\t"));
    }
   
    public ArrayList<Component> findAllComponents() {
        return new ArrayList<>(componentArrayList);
    }

    public ArrayList<Component> getComponentArrayList() {
        return componentArrayList;
    }

    public int size() {
        return componentArrayList.size();
    }
}
