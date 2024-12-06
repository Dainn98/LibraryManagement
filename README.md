# Library Management System Application

# Overview
The Library Management System (LMS) is a comprehensive application designed to efficiently manage library resources. It provides a user-friendly interface for both library staff and users, facilitating seamless operations such as book management, user management, and transaction handling. This is our own first project using Java and JavaFX at University of Engineering and Technology, Vietnam National University.

# Table of Contents

1. [Overview](#overview)  
2. [Set up](#set-up)  
   - [Prerequisites](#prerequisites)  
   - [Build using Maven](#build-using-maven)  
3. [UML Diagram](#uml)  
4. [Features](#features)  
   - [Registration and Login](#registration-and-login)  
   - [Manager Features](#manager-features)  
     1. [Dashboard](#1-dashboard)  
     2. [Documents Management](#2-documents-management)  
     3. [User Management](#3-user-management)  
     4. [Library Catalog](#4-library-catalog)  
     5. [Pending Approvals](#5-pending-approvals)  
     6. [Pending Loans](#6-pending-loans)  
     7. [All Issued Documents](#7-all-issued-documents)  
     8. [Library FAQs](#8-library-faqs)  
   - [User Features](#user-features)  
     1. [Home](#1-home)  
     2. [Borrowing Books](#2-borrowing-books)  
     3. [Processing](#3-processing)  
     4. [History](#4-history)  
     5. [Library FAQs](#5-library-faqs)  
     6. [Policy](#6-policy)  
   - [General Features](#general-features)  
     1. [Settings](#1-settings)  
     2. [Change Theme](#2-change-theme)  
     3. [Sign Out](#3-sign-out)  
5. [Technologies Used](#technologies-used)  
6. [Usage](#usage)  
7. [Team Members](#team-members)  

# Set up

**Prerequisites**
- Java JDK 23: https://www.oracle.com/java/technologies/downloads/#java23
- Maven 3.9.9: https://maven.apache.org/download.cgi

**Build using Maven**

1.	Download

- Access the project repository at https://github.com/Dainn98/LibraryManagement 

2.	Extract and Open

- Extract the downloaded files and open them in your IDE.

3.	Configure Project Structure

- Navigate to File > Project Structure > Module > resources, right-click, and select Resources > OK.

4.	Rebuild Project

- Use Build > Rebuild Project.

5.	Run Application

- Select Maven > Plugins > javafx > javafx:run and double-click to run.


# UML

![src](https://github.com/user-attachments/assets/f2054a0c-b86e-4a8c-8332-911443cb3660)

# Features

## Registration and Login

- Existing Account: Login with your credentials.
  ![login](https://github.com/user-attachments/assets/0dc40cdf-cbf1-4cde-af7f-0faea188f872)
- New Account: Register a new account.
  ![register](https://github.com/user-attachments/assets/956518e1-c8f5-469d-8a8e-245419d0da1a)
- Rules:

  + Password: Minimum 6 characters, at least one letter, one number, and one special character, no spaces allowed.
  + Phone Number: Must be exactly 10 digits, starting with '0', digits only.
  + Email: Must be in valid format (e.g., example@gmail.com), accepted domains: gmail.com, vnu.edu.vn.
  + Security Code: A 6-digit code sent via email.
________________________________________
## Manager Features

### 1. Dashboard

- Overview: Provides real-time summaries and visualizations of library data.

![dashboard](https://github.com/user-attachments/assets/52c19b99-c2b7-43d1-b04a-16322faa16b9)

- Key Features:
  + Summary: Total documents, remaining documents, issued documents, and users.
  + Charts: Visual display of statuses.
  + Real-Time Updates: Ensures accurate monitoring.

### 2. Documents Management

- Overview: Streamlined handling of library materials.

![documents](https://github.com/user-attachments/assets/2bd28569-557e-4cb9-b41b-3de2e41de3b7)
![image](https://github.com/user-attachments/assets/e601064d-ac94-4c06-a7f3-7b9d453ec41d)
![image](https://github.com/user-attachments/assets/1928e2b0-a5d9-4563-b7c2-ac655bd0a017)
- Key Features:

   +	Search & Filter by title, author, or ISBN.

   +	Manage Documents: Add, edit, or delete entries.

   +	Document Table: Shows ISBN, author, quantity, and availability.

   +	Return & Renew: Handle borrowed documents with late fee options.

### 3. User Management

-	Overview: Manage user information efficiently.

![users](https://github.com/user-attachments/assets/0a170f50-5e9d-4ce7-ac54-1549aeee90ea)

- Key Features:
  +   User Table: Displays user details.
  +  Edit User: Update user data.
  +  Search & Filter: Locate users quickly.
  +  Import Users: Bulk upload user data via file.
  

### 4. Library Catalog

-	Overview: Manage books both locally and online.

![image](https://github.com/user-attachments/assets/62165d21-4973-446e-956c-ebae719af340)

![libCatalog](https://github.com/user-attachments/assets/691220d5-fdad-4805-bb71-d8cf76dc5d8c)
- Key Features:

  +	Tabbed View: Separate tabs for local and online collections.

  +	Search & Add: Add books seamlessly.

### 5. Pending Approvals

-	Overview: Manage user applications awaiting approval.
![Screenshot 2024-11-29 023426](https://github.com/user-attachments/assets/52110e64-c2cf-4863-9904-15b5a20f195c)
- Key Features:

  +	Filter Approvals: Sort requests by criteria.

  +	Approve/Disapprove: Handle requests efficiently.

### 6. Pending Loans

-	Overview: Handle loan requests.
![Screenshot 2024-11-29 023436](https://github.com/user-attachments/assets/6735de7c-4598-4ace-9428-17b185e22958)
- Key Features:

  +	Search & Filter by title, ISBN, or user.

  +	Batch Actions: Approve or delete multiple requests.

### 7. All Issued Documents

-	Overview: Track borrowed documents and overdue fees.
![Screenshot 2024-11-29 023446](https://github.com/user-attachments/assets/aef34b7b-151f-4a88-8e38-79b9873728bd)
- Key Features:

  +	Monitor Status: Borrow dates and fees.

### 8. Library FAQs

-	Overview: Interactive chat for real-time assistance.
![Screenshot 2024-11-29 023752](https://github.com/user-attachments/assets/7281d6fe-8be9-4c67-8af6-f7f287b2bce0)
![Screenshot 2024-11-29 023731](https://github.com/user-attachments/assets/580aeff9-3e22-4a23-8ccc-e410325fd17c)  
- Key Features:

  +	Chat Box: AI-driven interaction.

  +	Speech-to-Text: Convert speech for easier communication.
________________________________________
## User Features

### 1. Home

-	Overview: Browse books by categories and manage loans
  ![Screenshot 2024-11-29 041548](https://github.com/user-attachments/assets/ccf27758-bb03-4042-87b3-c7ac15aa7f52)
- Key Features:
  + Search & Borrow: Access library resources easily.

### 2. Borrowing Books

-	Overview: Track borrowed books.
  ![Screenshot 2024-11-29 041558](https://github.com/user-attachments/assets/50266b30-c474-4f5a-896a-4088c9024f52)
- Key Features:

  +	Return: Return borrowed items.

### 3. Processing

-	Overview: View books under review.
  ![Screenshot 2024-11-29 041607](https://github.com/user-attachments/assets/89f481a0-1102-4480-a479-d0a12c65f287)
- Key Features:

  +	Undo: Cancel requests.

### 4. History

-	Overview: View borrowed and returned book records.
  ![Screenshot 2024-11-29 041617](https://github.com/user-attachments/assets/b8063a67-1193-4533-941c-5cf7f097717c)
- Key Features:

  +	Search: Find history by ID, ISBN, etc.

### 5. Library FAQs

-	Overview: Interactive chat for real-time assistance.
![Screenshot 2024-11-29 041742](https://github.com/user-attachments/assets/d13b5463-cc67-4e4f-a1cd-d66954e91100)
![Screenshot 2024-11-29 041707](https://github.com/user-attachments/assets/eafeff5b-3d27-47ba-88d6-9c9cd6946517)

- Key Features:

  +	Chat Box & Speech-to-Text.

### 6. Policy

-	View library rules by clicking the Policy button.
________________________________________
## General Features

### 1. Settings

-	Overview: Update personal information and view policies.
![profile](https://github.com/user-attachments/assets/3443db84-2a20-4f20-8596-e83a1184e4b8)
![security](https://github.com/user-attachments/assets/a7421db8-5969-4f94-b243-a2271298b88b)
![policy](https://github.com/user-attachments/assets/236ad786-7073-4288-aa6c-bc1cdd806c13)
- Key Features:

  +	Update Info: Requires a security code sent via email for changes.

### 2. Change Theme

-	Toggle between dark and light themes with the Change Theme button.
  ![Screenshot 2024-11-29 041831](https://github.com/user-attachments/assets/e38bced6-aecd-4781-bbf2-fc27d03d9b2b)
### 3. Sign Out

-	Logout and return to the login screen.

# Technologies Used
- **Java**: The core programming language for the application.
- **JavaFX**: Framework for building the user interface.
- **Scene Builder**: A visual layout tool for designing JavaFX application UIs.
- **Maven**: Dependency management and build automation tool.
- **SQLite**: Lightweight database for storing library data (or your preferred database).
- **JUnit**: Framework for unit testing to ensure code quality.

# Usage
- Launch the application and create a user account.
- Navigate through the intuitive interface to manage books and transactions.
- Utilize the reporting features to analyze library performance.

# Team Members
- **Phạm Tuấn Anh** (Team Leader) - Student ID: 23020010
- **Nguyễn Đức Anh** - Student ID: 23020007
- **Đào Nắng Dịu** - Student ID: 23020022
- **Phạm Đức Thiện** - Student ID: 23020160

## Special thanks to all contributors and mentors for their support.
