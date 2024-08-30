package repository;

public interface Repository<T> {
    void save(T massage);
    T load();
}
