package library.management.data.models.User;

import java.util.ArrayList;
import library.management.data.models.Documents.Document;
import library.management.data.transaction.Transaction;

/**
 * Represents a manager in the library management system.
 */
public class Manager extends User {

  private ArrayList<Member> MemberList = new ArrayList<>();
  private ArrayList<Document> borrowList = new ArrayList<>();
  private ArrayList<Transaction> transactionList = new ArrayList<>();

  /**
   * Default constructor initializing the manager with default values.
   */
  public Manager() {
    super("manager", "88888888", true, "admin888@gmail.com",
        "8888888888", "HaTinh", "888888888888");
    MemberList = new ArrayList<>();
    borrowList = new ArrayList<>();
    transactionList = new ArrayList<>();
  }

  /**
   * Constructor initializing the manager with specified values.
   *
   * @param name         the name of the manager
   * @param id           the ID of the manager
   * @param VIP          whether the manager is a VIP
   * @param email        the email of the manager
   * @param mobile       the mobile number of the manager
   * @param address      the address of the manager
   * @param identityCard the identity card number of the manager
   */
  public Manager(String name, String id, boolean VIP,
      String email, String mobile, String address, String identityCard) {
    super(name, id, VIP, email, mobile, address, identityCard);
    MemberList = new ArrayList<>();
    borrowList = new ArrayList<>();
    transactionList = new ArrayList<>();
  }

  /**
   * Adds a member to the member list.
   *
   * @param member the member to be added
   */
  public void addMember(Member member) {
    boolean exists = MemberList.stream().anyMatch(m -> m.getId().equals(member.getId()));
    if (exists) {
      System.out.println("User with ID " + member.getId() + " already exists.");
    } else {
      MemberList.add(member);
      System.out.printf(
          "User ID: %s, Name: %s, VIP: %b, Email: %s, Mobile: %s, Address: %s, Identity Card: %s, Membership Level: %s added successfully.%n",
          member.getId(), member.getName(), member.getVIP(), member.getEmail(), member.getMobile(),
          member.getAddress(), member.getIdentityCard(), member.getMembershipLevel());
    }
  }

  /**
   * Removes a user from the member list by their ID.
   *
   * @param id the ID of the user to be removed
   */
  public void removeUser(String id) {
    boolean check = false;
    int numberOfMembers = MemberList.size();
    for (int i = 0; i < numberOfMembers; i++) {
      if (MemberList.get(i).getId().equals(id)) {
        Member removedMember = MemberList.remove(i);
        System.out.printf("User ID: %s, Name: %s removed successfully.%n", removedMember.getId(),
            removedMember.getName());
        check = true;
        break;
      }
    }
    if (!check) {
      System.out.println("User not found!");
    }
  }

  /**
   * Adds a document to the borrow list.
   *
   * @param doc the document to be borrowed
   */
  public void borrowDocument(Document doc) {
    borrowList.add(doc);
  }

  /**
   * Removes a document from the borrow list.
   *
   * @param doc the document to be returned
   */
  public void returnDocument(Document doc) {
    borrowList.remove(doc);
  }

  /**
   * Finds a borrowed document by its title.
   *
   * @param title the title of the document
   * @return the found document or null if not found
   */
  public Document findUserBorrowDoc(String title) {
    return borrowList.stream().filter(doc -> doc.getTitle().equals(title)).findFirst().orElse(null);
  }

  /**
   * Displays the details of a user.
   *
   * @param user the user to be displayed
   */
  public void displayUser(User user) {
    System.out.printf("ID: %s, Name: %s, VIP: %b, Email: %s, "
            + "Mobile: %s, Address: %s, Identity Card: %s, Membership Level: %s%n",
        user.getId(), user.getName(), user.getVIP(), user.getEmail(), user.getMobile(),
        user.getAddress(), user.getIdentityCard(), ((Member) user).getMembershipLevel());
  }

  /**
   * Displays the details of all users in the member list.
   */
  public void displayAllUsers() {
    for (User user : MemberList) {
      displayUser(user);
    }
  }

  /**
   * Finds a user by their name.
   *
   * @param name the name of the user
   * @return the found user or null if not found
   */
  public User findUser(String name) {
    return MemberList.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
  }
}