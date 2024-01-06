/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.lista.tablas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.table.AbstractTableModel;
import logica.Auto;
import logica.Vendedor;
import logica.Venta;

/**
 *
 * @author jsbal
 */
public class ModeloTablaVenta extends AbstractTableModel {

    private DynamicList<Venta> ventas;

    @Override
    public int getRowCount() {
        return ventas.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Venta venta = ventas.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (venta != null) ? venta.getId() : "";
                case 1:
                    return (venta != null) ? venta.getFechaVenta() : "";
                case 2:
                    Vendedor vendedor = venta.getVendedor();
                    if (vendedor != null) {
                        return vendedor.getNombre() + " " + vendedor.getApellido();
                    } else {
                        return "";
                    }
                case 3:
                    Auto auto = venta.getAuto();
                    if (auto != null) {
                        return auto.getMarca() + " ";
                    } else {
                        return "";
                    }
                case 4:
                    return (venta != null) ? venta.getMonto() : "";
                default:
                    return null;
            }
        } catch (EmptyException ex) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No Definido";
            case 1:
                return "FECHA";
            case 2:
                return "VENDEDOR";
            case 3:
                return "AUTO";
            case 4:
                return "MONTO";
            default:
                return null;
        }
    }

    public DynamicList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(DynamicList<Venta> ventas) {
        this.ventas = ventas;
    }
}
