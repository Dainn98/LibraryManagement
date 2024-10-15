package library.management.data.models;

import library.management.data.models.Documents.*;
import library.management.data.models.User.*;
import java.util.*;

public class testModel {
    private static final Manager userManager = new Manager();
    private static final DocumentController documentManager = new DocumentController();  // Giả sử có lớp DocumentsManager để quản lý tài liệu
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int n = sc.nextInt();
            sc.nextLine();
            switch (n) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    addDocument();
                    break;
                case 2:
                    removeDocument();
                    break;
                case 3:
                    updateDocument();
                    break;
                case 4:
                    findDocument();
                    break;
                case 5:
                    displayAllDocuments();
                    break;
                case 6:
                    addUser();
                    break;
                case 7:
                    borrowDocument();
                    break;
                case 8:
                    returnDocument();
                    break;
                case 9:
                    displayUserInfo();
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("[0] Exit");
        System.out.println("[1] Add Document");
        System.out.println("[2] Remove Document");
        System.out.println("[3] Update Document");
        System.out.println("[4] Find Document");
        System.out.println("[5] Display Documents");
        System.out.println("[6] Add User");
        System.out.println("[7] Borrow Document");
        System.out.println("[8] Return Document");
        System.out.println("[9] Display User Info");
        System.out.print("Select an option: ");
    }

    private static void addDocument() {
        System.out.print("Enter document ID: ");
        String id = sc.nextLine();
        System.out.print("Enter document title: ");
        String title = sc.nextLine();

        System.out.print("Enter document type: ");
        String[] typeArr = sc.nextLine().split(","); // có thể có nhiều type
        HashSet<String> types = new HashSet<>();
        for (String type : typeArr) {
            types.add(type.trim());
        }

        System.out.print("Enter document author: ");
        String[] authorArr = sc.nextLine().split(","); // đây cũng v
        HashSet<String> authors = new HashSet<>();
        for (String author : authorArr) {
            authors.add(author.trim());
        }

        System.out.print("Enter document tags: ");
        String[] tagArr = sc.nextLine().split(","); // đây cũng v
        HashSet<String> tags = new HashSet<>();
        for (String tag : tagArr) {
            tags.add(tag.trim());
        }

        System.out.print("Enter number of available copies: ");
        int availableCopies = sc.nextInt();

        Document doc = new Document(id, title, types, authors, tags, availableCopies);
        documentManager.addDocument(doc);
    }

    private static void removeDocument() {
        System.out.print("Enter Document Title to remove: ");
        String id = sc.nextLine();
        documentManager.removeDocument(id);
    }


    private static void updateDocument() {
        System.out.print("Enter Document Title to update: ");
        String title = sc.nextLine();
        System.out.print("Enter Document ID to update: ");
        String id = sc.nextLine();
        System.out.print("Enter document type to update: ");
        String[] typeArr = sc.nextLine().split(","); // có thể có nhiều type
        HashSet<String> types = new HashSet<>();
        for (String type : typeArr) {
            types.add(type.trim());
        }

        System.out.print("Enter document author to update: ");
        String[] authorArr = sc.nextLine().split(","); // đây cũng v
        HashSet<String> authors = new HashSet<>();
        for (String author : authorArr) {
            authors.add(author.trim());
        }

        System.out.print("Enter document tags to update: ");
        String[] tagArr = sc.nextLine().split(","); // đây cũng v
        HashSet<String> tags = new HashSet<>();
        for (String tag : tagArr) {
            tags.add(tag.trim());
        }

        System.out.print("Enter number of available copies to update: ");
        int availableCopies = sc.nextInt();
        Document doc = new Document(id, title, types, authors, tags, availableCopies);

        documentManager.updateDocument(doc);
    }

    private static void findDocument() {
        System.out.print("Enter document title to find: ");
        String title = sc.nextLine();
        if(documentManager.findDocumentByTitle(title) != null) documentManager.displayDocument(documentManager.findDocumentByTitle(title));
    }

    private static void displayAllDocuments() {
        documentManager.displayDocument();
    }

    private static void addUser() {
        System.out.print("Enter user Name: ");
        String name = sc.nextLine();
        System.out.print("Enter user ID: ");
        String id = sc.nextLine();
        System.out.println("Enter user VIP (true/false): ");
        boolean isVip = sc.nextBoolean();
        sc.nextLine();
        System.out.println("Enter user email: ");
        String email = sc.nextLine();
        System.out.println("Enter user mobile: ");
        String mobile = sc.nextLine();
        System.out.println("Enter user address: ");
        String address = sc.nextLine();
        System.out.println("Enter user identity card: ");
        String identityCard = sc.nextLine();
        System.out.println("Enter memebershipLevel: ");
        String membershipLevel = sc.nextLine();

        Member member = new Member(name, id, isVip, email, mobile, address, identityCard, membershipLevel);
        userManager.addMember(member);
        System.out.printf("User ID: %s, Name: %s, VIP: %b, Email: %s, Mobile: %s, Address: %s, Identity Card: %s, Membership Level: %s added successfully.%n",
            member.getId(), member.getName(),
            member.getVIP(), member.getEmail(),
            member.getMobile(), member.getAddress(),
            member.getIdentityCard(), member.getMembershipLevel());
    }

    private static void borrowDocument() {
        System.out.print("Enter user Name: ");
        String nameOfUser = sc.nextLine();
        User user = userManager.findUser(nameOfUser);
        if(user == null) {
            System.out.println("User not found");
            return;
        } else{
            System.out.print("Enter document name you want to borrow: ");
            String title = sc.nextLine();
            System.out.print("Enter document copies you want to borrow: ");
            int number = sc.nextInt();
            Document doc = documentManager.findDocumentByTitle(title);
            if(doc == null) System.out.print("Document not found");
            else if(doc.getAvailableCopies() < number){
                System.out.print("Don't have enough");
            }
            else{
                Document borrowedDocument = new Document(doc);
                borrowedDocument.setAvailableCopies(number);
                doc.setAvailableCopies(doc.getAvailableCopies() - number);
                userManager.borrowDocument(borrowedDocument);
                System.out.println("You have successfully borrowed " + number + " copies of " + borrowedDocument.getTitle());
            }
        }
    }

    private static void returnDocument() {
        System.out.print("Enter user Name: ");
        String nameOfUser = sc.nextLine();
        User user = userManager.findUser(nameOfUser);
        if(user == null) {
            System.out.println("User not found");
            return;
        }
        else{
            System.out.print("Enter document name you want to return: ");
            String title = sc.nextLine();
            System.out.print("Enter document copies you want to return: ");
            int number = sc.nextInt();
            Document doc = documentManager.findDocumentByTitle(title);
            if(doc == null) System.out.print("Document not found");
            else {
                Document returnDocument = new Document(doc);
                returnDocument.setAvailableCopies(number);
                doc.setAvailableCopies(doc.getAvailableCopies() + number);
                userManager.borrowDocument(returnDocument);
                System.out.println("You have successfully return " + number + " copies of " + returnDocument.getTitle());
            }
        }
    }

    private static void displayUserInfo() {
        System.out.println("[1] All users");
        System.out.println("[2] Find user by name");
        int k = sc.nextInt();
        sc.nextLine();
        switch (k){
            case 1:
                userManager.displayAllUsers();
                break;
            case 2:
                System.out.println("Enter user name: ");
                String name = sc.nextLine();
                User user =userManager.findUser(name);
                if (user != null) {
                    System.out.println("The information of the user:");
                    userManager.displayUser(user);

                } else {
                    System.out.println("User not found!");
                }
                break;
            default:
                System.out.println("Action is not supported!");
        }
    }
}
