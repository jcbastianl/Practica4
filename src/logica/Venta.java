/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.Date;

/**
 *
 * @author jsbal
 */
public class Venta {
    private int id;
    private Auto auto;
    private Vendedor vendedor;
    
    private Date fechaVenta;
    private double Monto;

    public Venta() {
    }

    public Venta(int id, Auto auto, Vendedor vendedor, Date fechaVenta, double Monto) {
        this.id = id;
        this.auto = auto;
        this.vendedor = vendedor;
        this.fechaVenta = fechaVenta;
        this.Monto = Monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double Monto) {
        this.Monto = Monto;
    }

    public boolean compare(Venta temp, String field, Integer type) {
        switch (type) {
            case 0:
                switch (field.toLowerCase()) {
                    case "id":
                        return this.id < temp.getId();
                    case "auto":
                        return this.auto.compare(temp.getAuto(), field, type);
                    case "vendedor":
                        return this.vendedor.compare(temp.getVendedor(), field, type);
                    case "fechaventa":
                        return this.fechaVenta.compareTo(temp.getFechaVenta()) < 0;
                    case "monto":
                        return this.Monto < temp.getMonto();
                    default:
                        return false;
                }
            case 1:
                switch (field.toLowerCase()) {
                    case "id":
                        return this.id > temp.getId();
                    case "auto":
                        return this.auto.compare(temp.getAuto(), field, type);
                    case "vendedor":
                        return this.vendedor.compare(temp.getVendedor(), field, type);
                    case "fechaventa":
                        return this.fechaVenta.compareTo(temp.getFechaVenta()) > 0;
                    case "monto":
                        return this.Monto > temp.getMonto();
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    

    
    
    
    

    
   

   
    
}
