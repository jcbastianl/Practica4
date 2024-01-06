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
import logica.Auto;

/**
 *
 * @author jsbal
 */
public class AutoControl extends DaoImplement<Auto> {

    private DynamicList<Auto> listaAutos;
    private Auto auto;

    public AutoControl() {
        super(Auto.class);
    }

    public DynamicList<Auto> getListaAutos() {
        listaAutos = all();
        return listaAutos;
    }

    public void setListaAutos(DynamicList<Auto> listaAutos) {
        this.listaAutos = listaAutos;
    }

    public Auto getAuto() {
        if (auto == null) {
            auto = new Auto();
        }
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Boolean persist() {
        auto.setId(all().getLenght() + 1);
        return persist(auto);
    }

    public DynamicList<Auto> shellsort(DynamicList<Auto> lista, Integer tipo, String field) throws EmptyException, Exception {

        if (tipo == 0) {
            tipo = 1;
        } else {
            tipo = 0;
        }

        int longitudLista = lista.getLenght();
        Auto[] arrCensadores = lista.toArray();

        int tamanoPedazo = longitudLista / 2;

        while (tamanoPedazo > 0) {
            for (int i = tamanoPedazo; i < longitudLista; i++) {
                Auto temp = arrCensadores[i];
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

    // Ejemplo de método para búsqueda lineal en una lista de autos
    public DynamicList<Auto> busquedaLineal(String texto, DynamicList<Auto> autos, String criterio) {
        //System.out.println("Estas usando busqueda lineal");
        DynamicList<Auto> lista = new DynamicList<>();
        try {
            Auto[] aux = shellsort(autos, 0, criterio).toArray();
            lista.removerAll();

            for (Auto p : aux) {
                Field nombreAtributo = Utiles.getField(Auto.class, criterio);

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

    public DynamicList<Auto> busquedaBinaria(String texto, DynamicList<Auto> autos, String criterio) {

        DynamicList<Auto> lista = new DynamicList<>();

        try {
            Auto[] aux = shellsort(autos, 0, criterio).toArray();

            int izquierda = 0;
            int derecha = aux.length - 1;

            while (izquierda <= derecha) {
                int puntoMedio = (izquierda + derecha) / 2;

                Field nombreAtributo = Utiles.getField(Auto.class, criterio);

                if (nombreAtributo != null) {
                    nombreAtributo.setAccessible(true);
                    Object valorMedio = nombreAtributo.get(aux[puntoMedio]);

                    if (valorMedio.toString().toLowerCase().contains(texto.toLowerCase())) {
                        lista.add(aux[puntoMedio]);
                        break;
                    } else if (valorMedio.toString().toLowerCase().compareTo(texto.toLowerCase()) < 0) {
                        izquierda = puntoMedio + 1;
                    } else {
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
