package persist;

import java.util.Optional;

public interface IDataMapper<E> {
    public Optional<E> find(int id);
    public int insert(E entity);
    public void update(E entity);
    public void remove(E entity);
    public int getID(E entity);
}
