# Library Management System Application

## Overview
The Library Management System (LMS) is a comprehensive application designed to efficiently manage library resources. It provides a user-friendly interface for both library staff and users, facilitating seamless operations such as book management, user management, and transaction handling. This is our own first project using Java and JavaFX at University of Engineering and Technology, Vietnam National University.

## Set up

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


## UML


## Features
**Register**
- If you already have an account

  ![login](https://github.com/user-attachments/assets/0dc40cdf-cbf1-4cde-af7f-0faea188f872)

- If you don't have an account

  ![register](https://github.com/user-attachments/assets/956518e1-c8f5-469d-8a8e-245419d0da1a)

- Note:


**Manager**
- Dashboard: 
![dashboard](https://github.com/user-attachments/assets/52c19b99-c2b7-43d1-b04a-16322faa16b9)
- Documents:
![documents](https://github.com/user-attachments/assets/2bd28569-557e-4cb9-b41b-3de2e41de3b7)
- Users:
![users](https://github.com/user-attachments/assets/0a170f50-5e9d-4ce7-ac54-1549aeee90ea)
- Library Catalog:
![libCatalog](https://github.com/user-attachments/assets/691220d5-fdad-4805-bb71-d8cf76dc5d8c)
- Pending Approvals:
![Screenshot 2024-11-29 023426](https://github.com/user-attachments/assets/52110e64-c2cf-4863-9904-15b5a20f195c)
- Pending Loans:
![Screenshot 2024-11-29 023436](https://github.com/user-attachments/assets/6735de7c-4598-4ace-9428-17b185e22958)
- All Issued Document:
![Screenshot 2024-11-29 023446](https://github.com/user-attachments/assets/aef34b7b-151f-4a88-8e38-79b9873728bd)
- Library FAQs:
![Screenshot 2024-11-29 023752](https://github.com/user-attachments/assets/7281d6fe-8be9-4c67-8af6-f7f287b2bce0)
![Screenshot 2024-11-29 023731](https://github.com/user-attachments/assets/580aeff9-3e22-4a23-8ccc-e410325fd17c)
- Sign Out:
  
- Setting:
![profile](https://github.com/user-attachments/assets/3443db84-2a20-4f20-8596-e83a1184e4b8)
![security](https://github.com/user-attachments/assets/a7421db8-5969-4f94-b243-a2271298b88b)
![policy](https://github.com/user-attachments/assets/236ad786-7073-4288-aa6c-bc1cdd806c13)
- Change theme:

**User**
- Home: When the Home button is clicked, display books from the database by categories and allow users to search for books using various filters, such as author name, book title, ISBN, and more.
  ![Screenshot 2024-11-29 041548](https://github.com/user-attachments/assets/ccf27758-bb03-4042-87b3-c7ac15aa7f52)

- Borrowing Book: When the Borrowing Books button is clicked, display the books currently being borrowed.
  ![Screenshot 2024-11-29 041558](https://github.com/user-attachments/assets/50266b30-c474-4f5a-896a-4088c9024f52)

- Processing: When the Processing button is clicked, display the books currently under review.
  ![Screenshot 2024-11-29 041607](https://github.com/user-attachments/assets/89f481a0-1102-4480-a479-d0a12c65f287)

- History: When the History button is clicked, display the list of borrowed and returned books.
  ![Screenshot 2024-11-29 041617](https://github.com/user-attachments/assets/b8063a67-1193-4533-941c-5cf7f097717c)

- Library FAQs: When the Library FAQs button is clicked, display a chatbox for users to communicate with AI, along with a speech-to-text feature.
  ![Screenshot 2024-11-29 041742](https://github.com/user-attachments/assets/d13b5463-cc67-4e4f-a1cd-d66954e91100)
  ![Screenshot 2024-11-29 041707](https://github.com/user-attachments/assets/eafeff5b-3d27-47ba-88d6-9c9cd6946517)

- Policy: When the Policy button is clicked, the library's rules will be displayed.
  ![Screenshot 2024-11-29 041913](https://github.com/user-attachments/assets/0ad073cb-ae19-4790-81a0-d8b2d6c502ee)

- Sign Out:
- Setting: When the Settings button is clicked, a screen will appear where users can update their information.
  ![Screenshot 2024-11-29 041849](https://github.com/user-attachments/assets/1431fdca-d46d-4b77-9b86-e8781b244a35)
  ![Screenshot 2024-11-29 041904](https://github.com/user-attachments/assets/021094d1-08f1-4b22-8cb3-c016db0a5a3d)
  
**Change theme**: When the "changeTheme" button is clicked, it will toggle between two modes: dark and light.

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
