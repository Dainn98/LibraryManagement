package library.management.ui.DAO;

public interface DAOInterface<Item> {
    int add(Item item);

    int delete(Item item);

    int update(Item item);
}
