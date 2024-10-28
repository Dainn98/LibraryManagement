package library.management.data.models.User;

import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;
import java.util.List;
import library.management.data.models.Documents.Document;

/**
 * Represents a user in the library management system.
 */
public class User {
    private SimpleStringProperty name;
    private SimpleStringProperty id;
    private boolean VIP;
    private SimpleStringProperty email;
    private SimpleStringProperty mobile;
    private SimpleStringProperty address;
    private SimpleStringProperty identityCard;
    //Member
    private ArrayList<Document> borrowDocuments;

    /**
     * Default constructor initializing the user with default values.
     */
    public User() {
        this("user", "00000000", false, "user000@gmail.com", "0000000000", "Hanoi", "000000000000");
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param name the name of the user
     * @param id the ID of the user
     * @param VIP whether the user is a VIP
     * @param email the email of the user
     * @param mobile the mobile number of the user
     * @param address the address of the user
     * @param identityCard the identity card number of the user
     */
    public User(String name, String id, boolean VIP, String email, String mobile, String address, String identityCard) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.VIP = VIP;
        this.email = new SimpleStringProperty(email);
        this.mobile = new SimpleStringProperty(mobile);
        this.address = new SimpleStringProperty(address);
        this.identityCard = new SimpleStringProperty(identityCard);
        this.borrowDocuments = new ArrayList<>();
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets the ID of the user.
     *
     * @return the ID of the user
     */
    public String getId() {
        return id.get();
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the new ID of the user
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Gets whether the user is a VIP.
     *
     * @return true if the user is a VIP, false otherwise
     */
    public boolean getVIP() {
        return VIP;
    }

    /**
     * Sets whether the user is a VIP.
     *
     * @param VIP the new VIP status of the user
     */
    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * Sets the email of the user.
     *
     * @param email the new email of the user
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Gets the mobile number of the user.
     *
     * @return the mobile number of the user
     */
    public String getMobile() {
        return mobile.get();
    }

    /**
     * Sets the mobile number of the user.
     *
     * @param mobile the new mobile number of the user
     */
    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    /**
     * Gets the address of the user.
     *
     * @return the address of the user
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Sets the address of the user.
     *
     * @param address the new address of the user
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Gets the identity card number of the user.
     *
     * @return the identity card number of the user
     */
    public String getIdentityCard() {
        return identityCard.get();
    }

    /**
     * Sets the identity card number of the user.
     *
     * @param identityCard the new identity card number of the user
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard.set(identityCard);
    }

    /**
     * Gets the list of documents borrowed by the user.
     *
     * @return the list of borrowed documents
     */
    public List<Document> getBorrowDocuments() {
        return borrowDocuments;
    }

    /**
     * Sets the list of documents borrowed by the user.
     *
     * @param borrowDocuments the new list of borrowed documents
     */
    public void setBorrowDocuments(ArrayList<Document> borrowDocuments) {
        this.borrowDocuments = borrowDocuments;
    }
}