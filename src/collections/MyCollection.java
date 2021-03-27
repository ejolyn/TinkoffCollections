package collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class MyCollection<E> implements Collection<E> {

    private int size = 0;

    private Object[] elementData = new Object[1];

    @Override
    public boolean add(E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) ((size + 1) * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(final Object o) {
        MyIterator iterator = new MyIterator();
        while (iterator.hasNext()) {
            Object elem = iterator.next();
            if ((elem == null && o == null) || (elem != null && elem.equals(o))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length <= size) {
            a = (T[]) Arrays.copyOf(elementData, size, a.getClass());
            return a;
        }
        int i = 0;
        while (i < size) {
            a[i] = (T) elementData[i];
            i++;
        }
        return a;
    }

    @Override
    public boolean remove(final Object o) {
        MyIterator iterator = new MyIterator();
        while (iterator.hasNext()) {
            Object elem = iterator.next();
            if ((o == null && elem == null)
                    || (o != null && elem != null && o.equals(elem))) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        Iterator iteratorC = c.iterator();
        boolean result = true;
        while (iteratorC.hasNext()) {
            Object elem = iteratorC.next();
            MyIterator iterator = new MyIterator();
            boolean resultIndividual = false;
            while (iterator.hasNext()) {
                Object myElem = iterator.next();
                if ((myElem == null && elem == null)
                        || (myElem != null && elem != null && myElem.equals(elem))) {
                    resultIndividual = true;
                }
            }
            result = result && resultIndividual;
        }
        return result;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        int length = size;
        if (size + c.size() >= elementData.length) {
            elementData = Arrays.copyOf(elementData, (size + c.size()));
            size = size + c.size();
        }
        int i = length;
        for (E e : c) {
            elementData[i++] = e;
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        Iterator iteratorC = c.iterator();
        boolean result = false;
        while (iteratorC.hasNext()) {
            Object elem = iteratorC.next();
            MyIterator iterator = new MyIterator();
            while (iterator.hasNext()) {
                Object myElem = iterator.next();
                if ((myElem == null && elem == null)
                        || (myElem != null && elem != null && myElem.equals(elem))) {
                    iterator.remove();
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        MyIterator iterator = new MyIterator();
        boolean result = false;
        while (iterator.hasNext()) {
            Iterator iteratorC = c.iterator();
            Object elem = iterator.next();
            boolean resultIndividual = false;
            while (iteratorC.hasNext()) {
                Object myElem = iteratorC.next();
                if ((myElem == null && elem == null)
                        || (myElem != null && elem != null && myElem.equals(elem))) {
                    resultIndividual = true;
                    break;
                }
            }
            if (!resultIndividual) {
                iterator.remove();
                result = true;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    private class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;
        private boolean flagRemove = true;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            flagRemove = false;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (cursor == 0 || flagRemove) {
                throw new IllegalStateException();
            } else {
                Object[] newElementData = new Object[size + 1];
                for (int i = 0; i < elementData.length - 1; i++) {
                    if (i < cursor - 1) {
                        newElementData[i] = elementData[i];
                    } else if (i >= cursor - 1) {
                        newElementData[i] = elementData[i + 1];
                    }
                }
                size--;
                cursor--;
                elementData = newElementData;
                flagRemove = true;
            }
        }
    }
}
