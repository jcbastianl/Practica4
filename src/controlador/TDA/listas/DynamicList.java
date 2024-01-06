/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.listas;

import controlador.TDA.listas.Exception.EmptyException;

public class DynamicList<E> {

    private Node<E> header;
    private Node<E> last;
    private Integer lenght;

    public DynamicList() {
        header = null;
        last = null;
        lenght = 0;
    }

    public Node<E> getHeader() {
        return header;
    }

    public void setHeader(Node<E> header) {
        this.header = header;
    }

    public Node<E> getLast() {
        return last;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    public Boolean isEmpty() {
        return header == null || getLenght() == 0;
    }

    private void addFirst(E info) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(info);
            header = help;
            last = help;

        } else {
            Node<E> headHelp = header;
            help = new Node<>(info, headHelp);
            header = help;

        }
        lenght++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addFirst(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            lenght++;
        }
    }

    public void add(E info) {
        addLast(info);
    }

    public void add(E info, Integer index) throws EmptyException, IndexOutOfBoundsException {
        if (index.intValue() == 0) {
            addFirst(info);
        } else if (index.intValue() == lenght.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            setLenght((Integer) (getLenght() + 1));
        }
    }

    public void remove(Integer index) throws IndexOutOfBoundsException, EmptyException {
        if (index.intValue() < 0 || index.intValue() >= lenght) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        if (index.intValue() == 0) {
            removeFirst();
        } else {

            Node<E> nodoAnterior = getNode(index - 1);

            Node<E> nodoAEliminar = nodoAnterior.getNext();

            nodoAnterior.setNext(nodoAEliminar.getNext());

            if (index.intValue() == (lenght - 1)) {
                last = nodoAnterior;
            }

            nodoAEliminar.setNext(null);

            lenght--;
        }
    }

    public void removerAll() {
        try {
            for (int i = 0; i < lenght; i++) {
                remove(i);
            }
        } catch (Exception e) {
            System.out.println("Algo fallo al vaciar la lista");
        }

    }

    private void removeFirst() {
        if (!isEmpty()) {
            header = header.getNext();
            lenght--;
        }
    }

    private E getFirst() throws EmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        }
        return header.getInfo();
    }

    public E getInfo(Integer index) throws EmptyException {
        return getNode(index).getInfo();
    }

    public Node<E> getNode(Integer index) throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        } else if (index < 0 || index >= lenght) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango");
        } else if (index == 0) {
            return header;
        } else if (index == (lenght - 1)) {
            return last;
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < index) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E merge(E data, Integer pos) throws EmptyException {
        Node<E> provisional = getNode(pos);
        E help = provisional.getInfo();
        provisional.setInfo(data);
        return help;

        /*Node<E> original = getNode(pos);
        Node<E> help = new Node<>(data, original.getNext());
        getNode(pos-1).setNext(help);
         */
    }

    public E extractFirs() throws EmptyException {
        //extrae de la lista y no devuelve
        if (isEmpty()) {
            throw new EmptyException("Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> help = header.getNext();
            header = null;
            header = help;

            if (lenght == 1) {
                last = null;
            }

            lenght--;
            return element;
        }
    }

    public E extractLast() throws EmptyException {
        //extrae de la lista y no devuelve
        if (isEmpty()) {
            throw new EmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> help = getNode(lenght - 2);
            if (help == null) {
                last = null;
                if (lenght == 2) {
                    last = header;

                } else {
                    header = null;

                }
            } else {
                last = null;
                last = help;
                last.setNext(null);
            }
            lenght--;
            return element;
        }
    }

    public E extract(Integer index) throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        } else if (index < 0 || index >= lenght) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango");
        } else if (index == 0) {
            return extractFirs();
        } else if (index == (lenght - 1)) {
            return extractLast();
        } else {
            Node<E> node_preview = getNode(index - 1);
            Node<E> node_actual = getNode(index);
            E info = node_actual.getInfo();
            Node<E> help_next = node_actual.getNext();
            node_actual = null;
            node_preview.setNext(help_next);
            lenght--;
            return info;
        }

    }

     public void clear() {
        header = null;
        last = null;
        lenght = 0;
    }
     
    public int size() {
        return lenght;
    }

    public E get(int index) throws EmptyException, IndexOutOfBoundsException {
        if (index < 0 || index >= lenght) {
            throw new IndexOutOfBoundsException("Error. Índice fuera de rango");
        }
        return getNode(index).getInfo();
    }

    public E[] toArray() {
        Class clazz = null;
        E[] matriz = null;
        if (lenght > 0) {
            clazz = header.getInfo().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, lenght);
            Node<E> aux = header;
            for (int i = 0; i < lenght; i++) {
                matriz[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matriz;
    }

    public DynamicList<E> toList(E[] m) {
        reset();
        for (int i = 0; i < m.length; i++) {
            this.add(m[i]);
        }
        return this;
    }

    public void reset() {
        header = null;
        last = null;
        lenght = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Lista Data\n");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append("\n");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

}
