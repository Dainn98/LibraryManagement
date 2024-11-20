//package library.management.data.test;
//
//import library.management.data.DAO.LanguageDAO;
//import library.management.data.entity.Language;
//
//import java.util.List;
//
//public class TestLanguage {
//
//    public static void addLanguage() {
//        String lgName = "thien";
//
//        Language language = new Language();
//        language.setLgName(lgName);
//
//        if (LanguageDAO.getInstance().add(language) > 0) {
//            System.out.println("Thêm ngôn ngữ thành công!");
//        } else {
//            System.out.println("Thêm ngôn ngữ thất bại!");
//        }
//    }
//
//    public static void deleteLanguage() {
//        String lgID = "LANG11";
//
//        Language language = new Language(lgID);
//
//        if (LanguageDAO.getInstance().delete(language) > 0) {
//            System.out.println("Xóa ngôn ngữ thành công!");
//        } else {
//            System.out.println("Xóa ngôn ngữ thất bại!");
//        }
//    }
//
//    public static void updateLanguage() {
//        String lgID = "LANG11";
//        String lgName = "thien2";
//
//        Language language = new Language(lgID, lgName);
//
//        if (LanguageDAO.getInstance().update(language) > 0) {
//            System.out.println("Cập nhật ngôn ngữ thành công!");
//        } else {
//            System.out.println("Cập nhật ngôn ngữ thất bại!");
//        }
//    }
//
//    public static void main(String[] args) {
//        addLanguage();
//        updateLanguage();
//        deleteLanguage();
//
//    }
//}
