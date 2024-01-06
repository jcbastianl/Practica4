/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Venta;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.utiles.Utiles;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import logica.Auto;

import logica.Vendedor;
import logica.Venta;

/**
 *
 * @author jsbal
 */
public class VentaControl extends DaoImplement<Venta> {

    private DynamicList<Venta> ventas;
    private Venta venta;

    public VentaControl() {
        super(Venta.class);
    }

    public DynamicList<Venta> getVentas() {
        ventas = all();
        return ventas;
    }

    public void setVentas(DynamicList<Venta> ventas) {
        this.ventas = ventas;
    }

    public Venta getVenta() {
        if (venta == null) {
            venta = new Venta();
        }
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Boolean persist() {
        return persist(venta);
    }

    public DynamicList<Venta> shellsort(DynamicList<Venta> lista, Integer tipo, String field) throws EmptyException, Exception {
        if (tipo == 0) {
            tipo = 1;
        } else {
            tipo = 0;
        }

        int longitudLista = lista.getLenght();
        Venta[] arrVentas = lista.toArray();

        int tamanoPedazo = longitudLista / 2;

        while (tamanoPedazo > 0) {
            for (int i = tamanoPedazo; i < longitudLista; i++) {
                Venta temp = arrVentas[i];
                int j = i;

                while (j >= tamanoPedazo && arrVentas[j - tamanoPedazo].compare(temp, field, tipo)) {
                    arrVentas[j] = arrVentas[j - tamanoPedazo];
                    j -= tamanoPedazo;
                }

                arrVentas[j] = temp;
            }

            tamanoPedazo = tamanoPedazo / 2;
        }

        return lista.toList(arrVentas);
    }

    public DynamicList<Venta> busquedaLineal(String texto, DynamicList<Venta> ventas, String criterio) {
        DynamicList<Venta> lista = new DynamicList<>();
        try {
            Venta[] aux = shellsort(ventas, 0, criterio).toArray();
            lista.removerAll();

            for (Venta v : aux) {
                if (criterio.equals("nombre") || criterio.equals("apellido")) {
                    Vendedor vendedor = v.getVendedor();
                    if (vendedor != null) {
                        if ((criterio.equals("nombre") && vendedor.getNombre().toLowerCase().contains(texto.toLowerCase()))
                                || (criterio.equals("apellido") && vendedor.getApellido().toLowerCase().contains(texto.toLowerCase()))) {
                            lista.add(v);
                        }
                    }
                } else if (criterio.equals("marca")) {
                    Auto auto = v.getAuto();
                    if (auto != null && auto.getMarca().toLowerCase().contains(texto.toLowerCase())) {
                        lista.add(v);
                    }
                } else {
                    Field nombreAtributo = Utiles.getField(Venta.class, criterio);

                    if (nombreAtributo != null) {
                        nombreAtributo.setAccessible(true);
                        Object getter = nombreAtributo.get(v);

                        if (getter != null && getter.toString().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(v);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

     

  public DynamicList<Venta> busquedaBinaria(String texto, DynamicList<Venta> ventas, String criterio) {
    DynamicList<Venta> lista = new DynamicList<>();

    try {
        Venta[] aux = shellsort(ventas, 0, criterio).toArray();

        int izquierda = 0;
        int derecha = aux.length - 1;

        while (izquierda <= derecha) {
            int puntoMedio = izquierda + (derecha - izquierda) / 2;

            if (criterio.equals("nombre") || criterio.equals("apellido")) {
                // Lógica de búsqueda para nombre y apellido
            } else if (criterio.equals("marca")) {
                // Lógica de búsqueda para marca
            } else if (criterio.equals("color")) {
                Auto auto = aux[puntoMedio].getAuto();
                if (auto != null && auto.getColor().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(aux[puntoMedio]);
                    break;
                } else {
                    int comparacion = texto.toLowerCase().compareTo(auto.getColor().toLowerCase());
                    if (comparacion < 0) {
                        derecha = puntoMedio - 1;
                    } else {
                        izquierda = puntoMedio + 1;
                    }
                }
            } else {
                Field nombreAtributo = Utiles.getField(Venta.class, criterio);
                if (nombreAtributo != null) {
                    nombreAtributo.setAccessible(true);
                    Object valorMedio = nombreAtributo.get(aux[puntoMedio]);

                    // Resto de comparación igual que en el código original
                }
            }
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return lista;
}
}
