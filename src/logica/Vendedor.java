/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import controlador.TDA.listas.DynamicList;
import java.util.List;

/**
 *
 * @author jsbal
 */
public class Vendedor {
    private Integer id;
    private String nombre;
    private String apellido;
    private String ruc;
    private String direccion;
    private String telefono;
    private String correo;
    private DynamicList<Venta> ventas;

    public Vendedor() {
    }

    public Vendedor(Integer id, String nombre, String apellido, String ruc, String direccion, String telefono, String correo, DynamicList<Venta> ventas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.ventas = ventas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public DynamicList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(DynamicList<Venta> ventas) {
        this.ventas = ventas;
    }

   public Boolean compare(Vendedor v, String field, Integer type) {
        switch (type) {
            case 0:
                switch (field.toLowerCase()) {
                    case "id":
                        return this.id < v.getId();
                    case "nombre":
                        return this.nombre.compareToIgnoreCase(v.getNombre()) < 0;
                    case "apellido":
                        return this.apellido.compareToIgnoreCase(v.getApellido()) < 0;
                    case "ruc":
                        return this.ruc.compareToIgnoreCase(v.getRuc()) < 0;
                    case "direccion":
                        return this.direccion.compareToIgnoreCase(v.getDireccion()) < 0;
                    case "telefono":
                        return this.telefono.compareToIgnoreCase(v.getTelefono()) < 0;
                    case "correo":
                        return this.correo.compareToIgnoreCase(v.getCorreo()) < 0;
                    default:
                        return false;
                }
            case 1:
                switch (field.toLowerCase()) {
                    case "id":
                        return this.id > v.getId();
                    case "nombre":
                        return this.nombre.compareToIgnoreCase(v.getNombre()) > 0;
                    case "apellido":
                        return this.apellido.compareToIgnoreCase(v.getApellido()) > 0;
                    case "ruc":
                        return this.ruc.compareToIgnoreCase(v.getRuc()) > 0;
                    case "direccion":
                        return this.direccion.compareToIgnoreCase(v.getDireccion()) > 0;
                    case "telefono":
                        return this.telefono.compareToIgnoreCase(v.getTelefono()) > 0;
                    case "correo":
                        return this.correo.compareToIgnoreCase(v.getCorreo()) > 0;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    
}
