package library.management.test;

import library.management.DAO.LanguageDAO;
import library.management.entity.Language;

import java.util.List;

public class TestLanguage {

    public static void addLanguage() {
        String lgName = "thien"; // Thêm ngôn ngữ

        // Tạo đối tượng Language với các tham số
        Language language = new Language();
        language.setLgName(lgName);

        // Gọi phương thức thêm ngôn ngữ từ LanguageDAO
        if (LanguageDAO.getInstance().them(language) > 0) {
            System.out.println("Thêm ngôn ngữ thành công!");
        } else {
            System.out.println("Thêm ngôn ngữ thất bại!");
        }
    }

    public static void deleteLanguage() {
        String lgID = "LANG11"; // ID của ngôn ngữ cần xóa

        // Tạo đối tượng Language với lgID cần xóa
        Language language = new Language(lgID);

        // Gọi phương thức xóa ngôn ngữ từ LanguageDAO
        if (LanguageDAO.getInstance().xoa(language) > 0) {
            System.out.println("Xóa ngôn ngữ thành công!");
        } else {
            System.out.println("Xóa ngôn ngữ thất bại!");
        }
    }

    public static void updateLanguage() {
        String lgID = "LANG11"; // ID của ngôn ngữ cần cập nhật
        String lgName = "thien2"; // Tên ngôn ngữ mới

        // Tạo đối tượng Language với thông tin cập nhật
        Language language = new Language(lgID, lgName);

        // Gọi phương thức cập nhật ngôn ngữ từ LanguageDAO
        if (LanguageDAO.getInstance().capNhat(language) > 0) {
            System.out.println("Cập nhật ngôn ngữ thành công!");
        } else {
            System.out.println("Cập nhật ngôn ngữ thất bại!");
        }
    }

    public static void getAllLanguages() {
        // Lấy danh sách tất cả ngôn ngữ từ LanguageDAO
        List<Language> languages = LanguageDAO.getInstance().layTatCa();

        if (languages != null && !languages.isEmpty()) {
            System.out.println("Danh sách ngôn ngữ:");
            for (Language language : languages) {
                System.out.println("STT: " + language.getSTT() +
                        ", lgName: " + language.getLgName() +
                        ", lgID: " + language.getLgID());
            }
        } else {
            System.out.println("Không có ngôn ngữ nào trong cơ sở dữ liệu.");
        }
    }

    public static void main(String[] args) {
        addLanguage();
        updateLanguage();        // Kiểm tra thêm ngôn ngữ
    }
}
