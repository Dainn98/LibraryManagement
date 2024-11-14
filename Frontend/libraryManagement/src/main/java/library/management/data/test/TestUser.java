package library.management.data.test;

import library.management.data.DAO.UserDAO;
import library.management.data.entity.User;

import java.util.List;

public class TestUser {

    public static void addUser() {
        User user = new User("John Doe", "123 Main St", "1234567890", "0987654321", "johndoe@example.com", "USA", "California");

        if (UserDAO.getInstance().add(user) > 0) {
            System.out.println("Thêm người dùng thành công!");
        } else {
            System.out.println("Thêm người dùng thất bại!");
        }
    }

    public static void deleteUser() {
        User user = new User();
        user.setUserId("USER11");

        if (UserDAO.getInstance().delete(user) > 0) {
            System.out.println("Xóa người dùng thành công!");
        } else {
            System.out.println("Xóa người dùng thất bại!");
        }
    }

    public static void updateUser() {
        User user = new User("USER11", "Jane Doe", "456 Elm St", "987654321012", "0123456789", "janedoe@example.com", "USA", "New York");

        if (UserDAO.getInstance().update(user) > 0) {
            System.out.println("Cập nhật người dùng thành công!");
        } else {
            System.out.println("Cập nhật người dùng thất bại!");
        }
    }

    public static void getAllUsers() {
        List<User> users = UserDAO.getInstance().getAllUser();

        if (users != null && !users.isEmpty()) {
            System.out.println("Danh sách người dùng:");
            for (User user : users) {
                System.out.println("userName: " + user.getUserName() +
                        ", address: " + user.getAddress() +
                        ", identityCard: " + user.getIdentityCard() +
                        ", phoneNumber: " + user.getPhoneNumber() +
                        ", email: " + user.getEmail() +
                        ", country: " + user.getCountry() +
                        ", state: " + user.getState() +
                        ", userId: " + user.getUserId());
            }
        } else {
            System.out.println("Không có người dùng nào trong cơ sở dữ liệu.");
        }
    }

    public static void main(String[] args) {
        addUser();
        updateUser();
        deleteUser();
        getAllUsers();
    }
}
