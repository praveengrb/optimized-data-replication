/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praveen.odr.model;

/**
 *
 * @author Praveen
 */
public class ServerNode {

    private int id;
    private String dist;
    private String capacity;
    private String color;

    public ServerNode() {
        super();
    }

    public ServerNode(String dist, String capacity, String color) {
        this.dist = dist;
        this.capacity = capacity;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
