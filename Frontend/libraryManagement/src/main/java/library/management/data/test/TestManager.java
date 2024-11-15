package library.management.data.test;

import library.management.data.DAO.ManagerDAO;
import library.management.data.entity.Manager;

public class TestManager {
    public static void addManager() {
        Manager manager = new Manager("John Doe", "password123", "0321516546554", "john.doe@example.com");

        if (ManagerDAO.getInstance().add(manager) > 0) {

            System.out.println("Thêm người quản lý thành công!");
        } else {
            System.out.println("Thêm người quản lý thất bại!");
        }
    }

    public static void deleteManager() {
        Manager manager = new Manager("MNG5");

        if (ManagerDAO.getInstance().delete(manager) > 0) {

            System.out.println("Xóa người quản lý thành công!");
        } else {
            System.out.println("Xóa người quản lý thất bại!");
        }
    }

    public static void updateManager() {
        Manager manager = new Manager("John Smith", "newpassword", "1321354321", "john.smith@example.com", "MNG5");

        if (ManagerDAO.getInstance().update(manager) > 0) {

            System.out.println("Cập nhật người quản lý thành công!");
        } else {
            System.out.println("Cập nhật người quản lý thất bại!");
        }
    }

    public static void main(String[] args) {
        addManager();
        updateManager();
        deleteManager();
    }
}
