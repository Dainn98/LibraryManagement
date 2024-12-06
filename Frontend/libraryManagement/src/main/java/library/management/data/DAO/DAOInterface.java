package library.management.data.DAO;

public interface DAOInterface<Item> {

  int add(Item item);

  int delete(Item item);

  int update(Item item);
}