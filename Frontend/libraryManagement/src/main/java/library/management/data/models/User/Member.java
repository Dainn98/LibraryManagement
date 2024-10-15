package library.management.data.models.User;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a member in the library management system.
 */
public class Member extends User {
    private SimpleStringProperty membershipLevel;

    /**
     * Constructs a new Member with the specified details.
     *
     * @param name the name of the member
     * @param id the ID of the member
     * @param VIP whether the member is a VIP
     * @param email the email of the member
     * @param mobile the mobile number of the member
     * @param address the address of the member
     * @param identityCard the identity card number of the member
     * @param membershipLevel the membership level of the member
     */
    public Member(String name, String id, boolean VIP, String email,
        String mobile, String address, String identityCard, String membershipLevel) {
        super(name, id, VIP, email, mobile, address, identityCard);
        this.membershipLevel = new SimpleStringProperty(membershipLevel);
    }

    /**
     * Gets the membership level of the member.
     *
     * @return the membership level
     */
    public String getMembershipLevel() {
        return membershipLevel.get();
    }

    /**
     * Sets the membership level of the member.
     *
     * @param membershipLevel the new membership level
     */
    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel.set(membershipLevel);
    }
}