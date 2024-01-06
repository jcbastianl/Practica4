/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Venta;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import logica.Vendedor;
import logica.Venta;


/**
 *
 * @author jsbal
 */
public class VentaControl extends DaoImplement<Venta> {
    private DynamicList<Venta> ventas;

    public VentaControl(Class<Venta> clazz) {
        super(clazz);
    }
    
}
