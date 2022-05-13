package pack.util;

public class CachedObject<T> {

    private final CachedObjectProvider<T> provider;
    private boolean isValid;
    private T cachedObject;

    public CachedObject(CachedObjectProvider<T> provider) {
        this.provider = provider;
        this.isValid = false;
    }

    public T getObject() {
        if(!isValid) {
            cachedObject = provider.buildObject();
            isValid = true;
        }

        return cachedObject;
    }

    public void invalidate() {
        isValid = false;
    }
}
