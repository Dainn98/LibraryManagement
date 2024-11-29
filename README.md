# Library Management System Application

# Overview
The Library Management System (LMS) is a comprehensive application designed to efficiently manage library resources. It provides a user-friendly interface for both library staff and users, facilitating seamless operations such as book management, user management, and transaction handling. This is our own first project using Java and JavaFX at University of Engineering and Technology, Vietnam National University.

# Set up

**Prerequisites**
- Java JDK 23: https://www.oracle.com/java/technologies/downloads/#java23
- Maven 3.9.9: https://maven.apache.org/download.cgi

**Build using Maven**

1.Download from https://github.com/Dainn98/LibraryManagement
![image](https://github.com/user-attachments/assets/b0b73497-4716-4188-8c64-d73244646156)

2.Extract this and open in your IDE

3.Click File > Project Structure > Module > resources then right click and choose Resources -> OK

4.Rebuild

Build > Rebuild Project

5.Run

Choose Maven > Plugins > javafx > javafx:run and double click.


# UML


# Features
## Register
- If you already have an account

  ![login](https://github.com/user-attachments/assets/0dc40cdf-cbf1-4cde-af7f-0faea188f872)

- If you don't have an account

  ![register](https://github.com/user-attachments/assets/956518e1-c8f5-469d-8a8e-245419d0da1a)

- Note:


## Manager
### Dashboard

**Introduction**

The dashboard of the Library Management System provides an overview and visual representation of documents and users in the library, helping management efficiently monitor and organize information.

**Key Features**

*Summary Overview:*
- All Documents: Displays the total number of documents in the system.
- Remaining Documents: Shows the number of documents available (not issued or in use).
- Issued Documents: Displays the number of documents that have been issued.
- All Users: Shows the total number of users in the system.
- Users Holding Documents: Displays the number of users currently holding documents.

*Documents Statistics:*
- A bar chart showcasing:
- Total documents.
- Remaining documents.
- Issued documents.
- User Statistics:

*A bar chart displaying:*
- Total users.
- Approved users.
- Users currently holding documents.

*Chart Legends:*
Below the charts, legends differentiate the data types:
- Document Information: Related to document data.
- User Information: Related to user data.
- Main Functions of the Dashboard:
- Provide an overview of the system's data, including documents and users.
- Visualize data with charts for easy tracking and analysis.
- Real-time updates for management to quickly understand the situation.
![dashboard](https://github.com/user-attachments/assets/52c19b99-c2b7-43d1-b04a-16322faa16b9)
### Documents
**Header**
- All Documents: Displays the total number of documents in the system.
- Remaining Documents: Shows the available documents currently in stock.
- Search Bar: Allows users to search for documents by title, author, or ISBN.
- Advanced Search: An option for detailed filtering of data.
- Manage Document (Dropdown): Contains options for managing documents, such as adding, editing, or importing data.
**Documents Table**
*Main Columns*
- Document ID: A unique identifier for each document.
- Document ISBN: The document's ISBN code.
- Document Title: The title of the document.
- Document Author: The author of the document.
- Document Publisher: The publisher of the document.
- Document Category: The document's category.
- Quantity: The total number of documents available.
- Remaining Documents: The number of documents currently in stock.
- Availability: The availability status of the document.
- Table Content: Currently, the table is empty as no data has been added to the system.

**Footer**
- Check All: Select all items in the table.
- Delete: Delete selected documents.

**Main Functions**
- Document Management: Tools for adding, editing, or deleting documents.
- Search: Allows quick and flexible document searches.
- Display Document Information: A data table listing detailed information about the library's documents.

**Return Document**

*Input Section*
- Enter Issue ID:
A text field to input the unique ID of the issued document.
- Issue Type (Dropdown):
Allows selection of the issue type (e.g., returning, renewal, or other issues).

*Main Content*
- List View: Displays the information of documents to be returned or renewed after entering the Issue ID.

*Footer*
- Late Fee: A field to display or input late return fees (if any).
- Navigation Buttons:
+ Back: Navigate to the previous page.
+ Submit: Confirm the document return.
+ Renew: Extend the borrowing period of the document.

*Main Functions*
- Return Documents: Users input the Issue ID to identify the document to be returned, then click Submit.
- Renew Documents: Click Renew to extend the borrowing period.
- Manage Late Fees: Calculate late fees and input them into the Late Fee field.
If you need further assistance or modifications for this interface, please let me know!
**Issue Document**
*Input Section*
- Enter Usernamme: A text field to input the unique username of the user.
- Enter ISBN: A text field to input the unique ISBN of the document.
- Enter quantity: A text field to input quantity of books.

*Main Fuctions*
- Allow users to borrow books.
![documents](https://github.com/user-attachments/assets/2bd28569-557e-4cb9-b41b-3de2e41de3b7)
![image](https://github.com/user-attachments/assets/e601064d-ac94-4c06-a7f3-7b9d453ec41d)
![image](https://github.com/user-attachments/assets/1928e2b0-a5d9-4563-b7c2-ac655bd0a017)

### Users
**Introduction**

This section of the Library Management System displays and manages user information.

**Key features**

*User Table*
Displays user details:
- User Name: Username or email.
- Phone Number: Contact number.
- Email Address: Registered email.
- Check box
*Search and Filter*
- Search Bar: Find users by entering their name, email, or other attributes.
- Filter Dropdown: Narrow down results based on specific categories (e.g., email or phone).
*User Information Panel*
Displays details of the selected user:
- Username
- Email
- Phone number
Allows editing these details.
*Action*
- Edit User: Update information directly via the form.
- Save/Cancel: Confirm or discard changes.

**Instructions**

*Search for Users*
- Enter a keyword in the search bar.
- Apply filters if needed.
*Edit User*
- Select a user from the table.
- Update the details in the "User Information" panel.
- Click "Save" to confirm or "Cancel" to revert.
*Import Users*
- Click the "Import Data" button.
- Upload the file containing user information.

![users](https://github.com/user-attachments/assets/0a170f50-5e9d-4ce7-ac54-1549aeee90ea)
### Library Catalog

**Introduction**
The Library Catalog provides an interactive interface for managing books in the library system.
**Key features**

- TabPlane: display documents to two types: in database and online.
- Search Bar: Allows manager to search by document title, author, or ISBN.
- When you click on a book, a new window will appear displaying all the book's details and allowing you to add the book to the library.

![image](https://github.com/user-attachments/assets/62165d21-4973-446e-956c-ebae719af340)

![libCatalog](https://github.com/user-attachments/assets/691220d5-fdad-4805-bb71-d8cf76dc5d8c)
### Pending Approvals

**Introduction**

This application screen is used for managing pending user requests to access the library. Admins can review, approve, or disapprove user applications based on their details.
**Key features**

*Filter Approvals*

Users can filter pending approvals using fields like:
- Username
- Country
- State
- Year

*User Details Table*

Displays a list of users awaiting approval with the following details:
- Username: The name of the user.
- Phone Number: Contact number of the user.
- Email: User's email address.
- Country: User's country.
- State: User's state or city.
- Year: The year associated with the application.

*Approval/Disapproval Options*

Each row has buttons to:
- Approve (Green): Approve the user's access request.
- Disapprove (Red): Deny the user's access request.

*Batch Actions*

- Use the "Check All" option to select all users for a bulk operation.
- Use the "Delete" button to remove selected entries.

*Action Buttons*

- Approve Button (Yellow): Batch approve selected requests.
**Instructions**
*Review User Information*

- Check the user's details in the table.
- Filter Users:Use the filter options at the top to find specific requests.
- Approve/Disapprove Requests:Click the Approve or Disapprove button for individual users.
- Batch Processing: Check multiple requests using the "Check All" option and perform batch approval or deletion.
- Final Actions: Click the yellow Approve button to confirm approvals for selected users.
![Screenshot 2024-11-29 023426](https://github.com/user-attachments/assets/52110e64-c2cf-4863-9904-15b5a20f195c)
### Pending Loans:
**Introduction**
The "Loans Requests" interface is used to manage the loan applications of documents in the library.
**Key features**
*Search Function*

A search bar is available to search for documents by:
- Title
- Author
- ISBN

*Document Loan Details Table*

This table shows the following details for each loan request:
- Issued ID: Unique identifier for the issued loan.
- Document ISBN: ISBN number of the borrowed document.
- User Name: Name of the user requesting the loan.
- Date Borrowed: The date when the document was borrowed.
- Date Returned: The date when the document was returned (if applicable).
- Required Date: The date by which the document should be returned.
- Quantity: Number of copies requested.
- Status: The current status of the loan request (e.g., pending, approved, etc.).
- Late Fee: If applicable, any late fee due on the loan.
- Approval: The current approval status for the loan request.

*Approval/Disapproval Options*

Each row includes buttons for:
- Approve (Yellow): Approve the loan request.
- Disapprove (Yellow): Disapprove the loan request.

*Issue Type Filter*

A dropdown menu allows filtering loan requests based on the issue type.

*Batch Actions*

- Check All: Select all entries for batch processing.
- Delete: Delete selected loan requests.

*Action Buttons*

- Approve Button (Yellow): Apply the approval decision for the selected requests.

**Instructions**

*Search for Documents*

Use the search bar to locate documents by their title, author, or ISBN.

*Review Loan Requests*

Check the details for each loan request in the table, including the user's name, borrow dates, and document status.

*Approve/Disapprove Request*:

Click on the Approve or Disapprove button for each request to make a decision.

*Filter by Issue Type*

Use the dropdown menu to filter loan requests based on the issue type.

*Batch Processing*

Use the Check All option to select multiple requests, then either approve or disapprove them in bulk.

*Final Actions*

Click the yellow Approve button to confirm the approval for selected requests.
![Screenshot 2024-11-29 023436](https://github.com/user-attachments/assets/6735de7c-4598-4ace-9428-17b185e22958)
### All Issued Document

**Introduction**

The "Issued Documents" interface is used for tracking documents currently issued to users. This section allows library manager to monitor the status of issued documents, check return details, and calculate late fees if applicable.

**Key features**

*Search Functionality*

A search bar is provided to look up issued documents by:
- Title
- Author
- ISBN

*Filter by Issue Type*

- A dropdown menu allows filtering issued documents by different types of IDs.

*Issued Documents Table*

Displays details for each issued document, including:
- Issued ID: Unique identifier for the issued document.
- Document ISBN: ISBN number of the issued document.
- Document Title: Title of the issued document.
- User Name: Name of the user who borrowed the document.
- Date Borrowed: The date the document was borrowed.
- Date Returned: The date the document was returned (if applicable).
- Number of Days: The number of days the document has been borrowed.
- Late Fee (MWK): Any applicable late fee, calculated in MWK.
- Status: The current status of the document (e.g., issued, returned, overdue).

**Instruction**

*Search for Documents*

Use the search bar to locate issued documents by title, author, or ISBN.

*View Document Details*

Check the details of issued documents in the table to track borrowed materials.

*Filter by Issue Type*

Use the dropdown menu to focus on specific categories or types of issued documents.

*Monitor Status*

Keep track of the borrowing duration and calculate late fees for overdue documents.
![Screenshot 2024-11-29 023446](https://github.com/user-attachments/assets/aef34b7b-151f-4a88-8e38-79b9873728bd)

### Library FAQs

**Introduction**

The Chat Box feature provides a user-friendly interface for real-time communication between users and AI.

**Key features**

*Chat box*

This is the places that users and AI communicate with others.

*Voice*

Allow speech to text.

**Instruction**

*Send Messages*

Type your message in the text input box and click Send.

*Using voice*

Click to micro icon and speak, then click to micro againto save text.

*New chat*

Click to "New chat" to reset the chatbox.
![Screenshot 2024-11-29 023752](https://github.com/user-attachments/assets/7281d6fe-8be9-4c67-8af6-f7f287b2bce0)
![Screenshot 2024-11-29 023731](https://github.com/user-attachments/assets/580aeff9-3e22-4a23-8ccc-e410325fd17c)  
- Setting:
  When the Settings button is clicked, a screen will appear where users can update their information and view policy.
![profile](https://github.com/user-attachments/assets/3443db84-2a20-4f20-8596-e83a1184e4b8)
![security](https://github.com/user-attachments/assets/a7421db8-5969-4f94-b243-a2271298b88b)
![policy](https://github.com/user-attachments/assets/236ad786-7073-4288-aa6c-bc1cdd806c13)

## User
### Home
When the Home button is clicked, display books from the database by categories and allow users to search for books using various filters, such as author name, book title, ISBN, and more.
  ![Screenshot 2024-11-29 041548](https://github.com/user-attachments/assets/ccf27758-bb03-4042-87b3-c7ac15aa7f52)

### Borrowing Book
When the Borrowing Books button is clicked, display the books currently being borrowed.
  ![Screenshot 2024-11-29 041558](https://github.com/user-attachments/assets/50266b30-c474-4f5a-896a-4088c9024f52)

### Processing
When the Processing button is clicked, display the books currently under review.
  ![Screenshot 2024-11-29 041607](https://github.com/user-attachments/assets/89f481a0-1102-4480-a479-d0a12c65f287)

### History
When the History button is clicked, display the list of borrowed and returned books.
  ![Screenshot 2024-11-29 041617](https://github.com/user-attachments/assets/b8063a67-1193-4533-941c-5cf7f097717c)

### Library FAQs
When the Library FAQs button is clicked, display a chatbox for users to communicate with AI, along with a speech-to-text feature.
  ![Screenshot 2024-11-29 041742](https://github.com/user-attachments/assets/d13b5463-cc67-4e4f-a1cd-d66954e91100)
  ![Screenshot 2024-11-29 041707](https://github.com/user-attachments/assets/eafeff5b-3d27-47ba-88d6-9c9cd6946517)

### Policy
When the Policy button is clicked, the library's rules will be displayed.
  ![Screenshot 2024-11-29 041913](https://github.com/user-attachments/assets/0ad073cb-ae19-4790-81a0-d8b2d6c502ee)

### Setting
When the Settings button is clicked, a screen will appear where users can update their information.
  ![Screenshot 2024-11-29 041849](https://github.com/user-attachments/assets/1431fdca-d46d-4b77-9b86-e8781b244a35)
  ![Screenshot 2024-11-29 041904](https://github.com/user-attachments/assets/021094d1-08f1-4b22-8cb3-c016db0a5a3d)

## Others:
**Change theme**: When the "changeTheme" button is clicked, it will toggle between two modes: dark and light.
**Sign out**: When click "Sign Out" button, and click to OK, you win return login scene.

  ![Screenshot 2024-11-29 041831](https://github.com/user-attachments/assets/e38bced6-aecd-4781-bbf2-fc27d03d9b2b)


## Technologies Used
- **Java**: The core programming language for the application.
- **JavaFX**: Framework for building the user interface.
- **Scene Builder**: A visual layout tool for designing JavaFX application UIs.
- **Maven**: Dependency management and build automation tool.
- **SQLite**: Lightweight database for storing library data (or your preferred database).
- **JUnit**: Framework for unit testing to ensure code quality.

## Usage
- Launch the application and create a user account.
- Navigate through the intuitive interface to manage books and transactions.
- Utilize the reporting features to analyze library performance.

## Team Members
- **Phạm Tuấn Anh** (Team Leader) - Student ID: 23020010
- **Nguyễn Đức Anh** - Student ID: 23020007
- **Đào Nắng Dịu** - Student ID: 23020022
- **Phạm Đức Thiện** - Student ID: 23020160

## Special thanks to all contributors and mentors for their support.
