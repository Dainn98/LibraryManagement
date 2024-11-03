package library.management.DAO;

public interface DAOInterface<Item> {
    int them(Item item);

    int xoa(Item item);

    int capNhat(Item item);
}
