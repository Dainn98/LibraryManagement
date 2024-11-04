package library.management.data.DAO;

public interface DAOInterface<Item> {
    int them(Item item);

    int xoa(Item item);

    int capNhat(Item item);
}
