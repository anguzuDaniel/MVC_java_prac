package app.controller;

import app.model.Message;

import java.util.*;
import java.util.function.Consumer;

/**
 * This is a simulated message server
 */
public class MessageServer implements Iterable<Message> {
    private Map<Integer, List<Message>> messages;
    private List<Message> selected;

    public MessageServer() {
        selected = new ArrayList<>();
        messages = new TreeMap<>();

        List<Message> list = new ArrayList<>();
        list.add(new Message("Test Message", "This is a test message"));
        list.add(new Message("Test Message 2", "This is a test message 2"));
        list.add(new Message("Test Message 3", "This is a test message 3"));
        messages.put(0, list);

        list = new ArrayList<>();
        list.add(new Message("Test Message 4", "This is a test message 5"));
        list.add(new Message("Test Message 5", "This is a test message 6"));
        list.add(new Message("Test Message 6", "This is a test message 7"));
        messages.put(1, list);
    }

    public void setSelectedServers(Set<Integer> servers) {
        for (Integer id : servers) {
            if (messages.containsKey(id)) {
                selected.addAll(messages.get(id));
            }

        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Message> iterator() {
        return new MessageIterator(selected);
    }
}

class MessageIterator implements Iterator {

    public Iterator<Message> iterator;

    public MessageIterator(List<Message> messages) {
        iterator = messages.listIterator();
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Object next() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return iterator.next();
    }

    /**
     * Removes from the underlying collection the last element returned
     * by this iterator (optional operation).  This method can be called
     * only once per call to {@link #next}.
     * <p>
     * The behavior of an iterator is unspecified if the underlying collection
     * is modified while the iteration is in progress in any way other than by
     * calling this method, unless an overriding class has specified a
     * concurrent modification policy.
     * <p>
     * The behavior of an iterator is unspecified if this method is called
     * after a call to the {@link #forEachRemaining forEachRemaining} method.
     *
     * @throws UnsupportedOperationException if the {@code remove}
     *                                       operation is not supported by this iterator
     * @throws IllegalStateException         if the {@code next} method has not
     *                                       yet been called, or the {@code remove} method has already
     *                                       been called after the last call to the {@code next}
     *                                       method
     * @implSpec The default implementation throws an instance of
     * {@link UnsupportedOperationException} and performs no other action.
     */
    @Override
    public void remove() {
        Iterator.super.remove();
    }

    /**
     * Performs the given action for each remaining element until all elements
     * have been processed or the action throws an exception.  Actions are
     * performed in the order of iteration, if that order is specified.
     * Exceptions thrown by the action are relayed to the caller.
     * <p>
     * The behavior of an iterator is unspecified if the action modifies the
     * collection in any way (even by calling the {@link #remove remove} method
     * or other mutator methods of {@code Iterator} subtypes),
     * unless an overriding class has specified a concurrent modification policy.
     * <p>
     * Subsequent behavior of an iterator is unspecified if the action throws an
     * exception.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     while (hasNext())
     *         action.accept(next());
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEachRemaining(Consumer action) {
        Iterator.super.forEachRemaining(action);
    }
}