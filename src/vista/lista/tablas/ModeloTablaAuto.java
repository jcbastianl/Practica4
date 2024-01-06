/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.lista.tablas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.table.AbstractTableModel;
import logica.Auto;

/**
 *
 * @author jsbal
 */
public class ModeloTablaAuto extends AbstractTableModel {
    private DynamicList<Auto> autos;

    @Override
    public int getRowCount() {
        return autos.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
/*   private Integer id_auto;
    private String color;
    private Double precio;
    private String marca;
*/
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Auto p = autos.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (p != null) ? p.getId(): " ";
                case 1:
                    return (p != null) ? p.getColor(): " ";
                case 2:
                    return (p != null) ? p.getPrecio(): " " ;
                case 3:
                    return (p != null) ? p.getMarca(): " " ;
              
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
                return "Id";
            case 1:
                return "Color";
            case 2:
                return "Precio";
            case 3:
                return "Marca";
            default:
                return null;
        }
    }

    public DynamicList<Auto> getAutos() {
        return autos;
    }

    public void setAutos(DynamicList<Auto> autos) {
        this.autos = autos;
    }

   

}
