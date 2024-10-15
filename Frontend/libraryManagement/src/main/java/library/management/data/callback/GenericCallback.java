package library.management.data.callback;

/**
 * Interface for a generic callback.
 */
public interface GenericCallback {
    /**
     * Called when a task is completed.
     *
     * @param val the value to be passed to the callback
     * @return the value to be returned by the callback
     */
    Object taskCompleted(Object val);
}
