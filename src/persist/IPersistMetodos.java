package persist;

import java.util.List;

public interface IPersistMetodos<T,ID> {
    public < T > void save(T entity);
    public < T > void update(T entity);
    public < T > void delete(T entity);
    public < T > T findById(ID id);
    public < T > List<T> listAllEntity(Class<T> entity);
    public < T > List<T> listAllEntityOrderBy(Class<T> entity, String fieldName);
}
