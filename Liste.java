import java.util.Iterator;
interface Liste <E> extends Iterable<E> {
    int stoerrelse ();
    void leggTil (E x);
    E hent ();
    E fjern ();
    @Override
    Iterator<E> iterator();

}