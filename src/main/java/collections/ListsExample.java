package collections;

import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ListsExample {

    static Logger log = Logger.getLogger(ListsExample.class);

    public static void main(String [ ] args) {

        List<Long> lista = new ArrayList<>();
        List<Long> lista2 = new LinkedList<>();
        ArrayList<Long> lista3 = new ArrayList<>();
        ArrayList<Long> lista4 = new ArrayList<Long>();

        // wybierz by zobaczyć listę możliwych metod
        lista.size();

        // Zobacz że mamy dodatkowe metody do wykorzystania
        LinkedList<Long> listaLinkowana = new LinkedList();

        listaLinkowana.addFirst(1L);

        // Stos jest jak lista ale ma inne metody
        Stack<Long> stos1 = new Stack<>();

        stos1.push(1L);
    }
}