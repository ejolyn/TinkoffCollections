package collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class MyCollection<E> implements Collection<E> {

    private int size = 10;

    private Object[] elementData = new Object[size];

    @Override
    public boolean add(final E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
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
            if (iterator.next().equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return elementData;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < elementData.length) {
            a = (T[]) Arrays.copyOf(elementData, elementData.length);
        } else {
            int i = 0;
            while (i < elementData.length) {
                a[i++] = (T) elementData[i];
            }
            while (i < a.length) {
                a[i++] = null;
            }
        }
        return a;
    }

    @Override
    public boolean remove(final Object o) {
        MyIterator iterator = new MyIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            Object elem = iterator.next();
            MyIterator myIterator = new MyIterator();
            boolean elemContains = false;
            while (myIterator.hasNext()) {
                if (myIterator.next().equals(elem)) {
                    elemContains = true;
                }
            }
            if (!elemContains) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (elementData.length + c.size() >= size) {
            elementData = Arrays.copyOf(elementData, (size + c.size()));
        }
        int i = elementData.length;
        for (E e : c) {
            elementData[i++] = e;
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        MyIterator iterator = new MyIterator();
        boolean result = false;
        while (iterator.hasNext()) {
            Object elem = iterator.next();
            Iterator iteratorC = c.iterator();
            while (iteratorC.hasNext()) {
                if (iteratorC.next().equals(elem)) {
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
        Object[] arr = c.toArray();
        while (iterator.hasNext()) {
            Object elem = iterator.next();
            for (int i = 0; i < c.size(); i++) {
                if (!arr[i].equals(elem)) {
                    iterator.remove();
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public void clear() {
        MyIterator iterator = new MyIterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
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
                Object[] newElementData = new Object[size - 1];
                for (int i = 0; i < elementData.length; i++) {
                    if (i < cursor - 1) {
                        newElementData[i] = elementData[i];
                    } else if (i >= cursor) {
                        newElementData[i - 1] = elementData[i];
                    }
                }
                elementData = newElementData;
                flagRemove = true;
            }
        }
    }
}
