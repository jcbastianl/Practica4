/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.logica;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.utiles.Utiles;
import java.lang.reflect.Field;

import logica.Vendedor;

/**
 *
 * @author jsbal
 */
public class VendedorControl extends DaoImplement<Vendedor> {

    private DynamicList<Vendedor> listaVendedores;
    private Vendedor vendedor;

    public VendedorControl() {
        super(Vendedor.class);
    }

    public DynamicList<Vendedor> getListaVendedores() {
        listaVendedores = all();
        return listaVendedores;
    }

    public void setListaVendedores(DynamicList<Vendedor> listaVendedores) {
        this.listaVendedores = listaVendedores;
    }

    public Vendedor getVendedor() {
        if (vendedor == null) {
            vendedor = new Vendedor();
        }
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Boolean persist() {
        vendedor.setId(all().getLenght() + 1);
        return persist(vendedor);
    }

    public DynamicList<Vendedor> shellsort(DynamicList<Vendedor> lista, Integer tipo, String field) throws EmptyException, Exception {

        if (tipo == 0) {
            tipo = 1;
        } else {
            tipo = 0;
        }

        int longitudLista = lista.getLenght();
        Vendedor[] arrCensadores = lista.toArray();

        int tamanoPedazo = longitudLista / 2;

        while (tamanoPedazo > 0) {
            for (int i = tamanoPedazo; i < longitudLista; i++) {
                Vendedor temp = arrCensadores[i];
                int j = i;

                while (j >= tamanoPedazo && arrCensadores[j - tamanoPedazo].compare(temp, field, tipo)) {
                    arrCensadores[j] = arrCensadores[j - tamanoPedazo];
                    j -= tamanoPedazo;
                }

                arrCensadores[j] = temp;
            }

            tamanoPedazo = tamanoPedazo / 2;
        }
        return lista.toList(arrCensadores);
    }

     public DynamicList<Vendedor> busquedaLineal(String texto, DynamicList<Vendedor> vendedores, String criterio) {
        //System.out.println("Estas usando busqueda lineal");
        DynamicList<Vendedor> lista = new DynamicList<>();
        try {
            Vendedor[] aux = shellsort(vendedores, 0, criterio).toArray();
            lista.removerAll();

            for (Vendedor p : aux) {
                Field nombreAtributo = Utiles.getField(Vendedor.class, criterio);

                if (nombreAtributo != null) {
                    nombreAtributo.setAccessible(true);
                    Object getter = nombreAtributo.get(p);

                    if (getter.toString().toLowerCase().contains(texto.toLowerCase())) {
                        lista.add(p);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public DynamicList<Vendedor> busquedaBinaria(String texto, DynamicList<Vendedor> vendedores, String criterio) {

    DynamicList<Vendedor> lista = new DynamicList<>();

    try {
        Vendedor[] aux = shellsort(vendedores, 0, criterio).toArray();
        
        int izquierda = 0;
        int derecha = aux.length - 1;

        while(izquierda <= derecha) {
            int puntoMedio = (izquierda + derecha) / 2;

            Field nombreAtributo = Utiles.getField(Vendedor.class, criterio);
            
            if(nombreAtributo != null) {
                nombreAtributo.setAccessible(true);
                Object valorMedio = nombreAtributo.get(aux[puntoMedio]);

                if(valorMedio.toString().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(aux[puntoMedio]);
                    break;
                }
                else if(valorMedio.toString().toLowerCase().compareTo(texto.toLowerCase()) < 0) {
                    izquierda = puntoMedio + 1; 
                }
                else {
                    derecha = puntoMedio - 1;
                }
           }
       }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    return lista;
}
}
