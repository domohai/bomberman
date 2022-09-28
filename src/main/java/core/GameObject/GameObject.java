package core.GameObject;

import core.GameObject.components.Component;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private List<Component> components;
    private Transform transform = null; // remember to set this after creating gameObject
    
    public GameObject() {
        this.components = new ArrayList<>();
    }
    
    /**
     * call start() function of all core.GameObject.components to initialize stuff
     */
    public void start() {
        for (Component c : this.components) {
            c.start();
        }
    }
    
    /**
     * loop through all the component and update it.
     * @param dt delta time.
     */
    public void update(double dt) {
        for (Component component : this.components) {
            component.update(dt);
        }
    }
    
    public void draw(Graphics2D g2D) {
        for (Component c : this.components) {
            c.draw(g2D);
        }
    }
    
    /**
     * This function looks a bit weird but all it does is
     * when you pass in the class type like Component.class
     * it will loop through the core.GameObject.components list and look for
     * an object of type Component and then return it
     * if there is no object of type Component, it'll return null.
     * if the explanation above didn't work
     * check the link below for more information
     * <a href="https://stackoverflow.com/questions/51979206/java-generic-getcomponent-method">...</a>
     * explanation at 14:00 in <a href="https://www.youtube.com/watch?v=HkG8ZdhoXhs">...</a>
     * @param componentClass class of type of object you are looking for.
     * @return object of type you're looking for or null if it doesn't exist
     * @param <T> specify that the class pass in must be a subclass of Component
     */
    //tl,dr : get the component of type needed
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : this.components) {
            if (componentClass.isAssignableFrom(c.getClass())) {//isAssignableFrom = isInstanceOf but might be extended
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    // for debugging
                    System.out.println("Error: Casting component!");
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    /**
     * we pass in the class of the component
     * which we want to remove from this gameObject,
     * and then it'll delete it for us.
     * @param componentClass e.c Sprite.class,...
     * @param <T> specify that the class pass in must be a subclass of Component
     */
    //tl,dr : remove the component of type needed
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < this.components.size(); i++) {
            Component c = this.components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }
    
    public void addComponent(Component newComponent) {
        this.components.add(newComponent);
        // after adding the component to this gameObject,
        // we'll set the reference gameObject of the component to this gameObject
        newComponent.setGameObject(this);
    }
    
    public Transform getTransform() {
        return transform;
    }
    
    public void setTransform(Transform transform) {
        this.transform = transform;
    }
    
    public int getPositionX() {
        return (int) this.transform.getPosition().getX();
    }
    
    public int getPositionY() {
        return (int) this.transform.getPosition().getY();
    }
}
