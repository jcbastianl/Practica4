/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;



/**
 *
 * @author jsbal
 */
public class Auto {
    private Integer id;
    private String color;
    private Double precio;
    private String marca;

    public Auto() {
    }

    public Auto(Integer id, String color, Double precio, String marca) {
        this.id = id;
        this.color = color;
        this.precio = precio;
        this.marca = marca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }


    public Boolean compare(Auto a, String field, Integer type) {
    switch (type) {
        case 0:
            switch (field.toLowerCase()) {
                case "id":
                    return this.id < a.getId();
                case "color":
                    return this.color.compareToIgnoreCase(a.getColor()) < 0;
                case "precio":
                    return this.precio < a.getPrecio();
                case "marca":
                    return this.marca.compareToIgnoreCase(a.getMarca()) < 0;
                default:
                    return false;
            }

        case 1:
            switch (field.toLowerCase()) {
                case "id":
                    return this.id > a.getId();
                case "color":
                    return this.color.compareToIgnoreCase(a.getColor()) > 0;
                case "precio":
                    return this.precio > a.getPrecio();
                case "marca":
                    return this.marca.compareToIgnoreCase(a.getMarca()) > 0;
                default:
                    return false;
            }

        default:
            return false;
    }
}


   
    
}
