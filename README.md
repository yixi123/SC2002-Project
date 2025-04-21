## SC2002 - BTO Project Management System
### NTU 2025 Semester 2 OODP Group Project
</br>

# BTO Project Management System

## Overview
The **BTO Project Management System** is a Java-based application designed to manage Build-To-Order (BTO) housing projects. It provides functionalities for different user roles, including **Applicants**, **HDB Officers**, and **HDB Managers**, to interact with the system. The system supports project applications, officer management, enquiry handling, and report generation.

---

## Features
### **Applicant**
- View available BTO projects.
- Apply for a project.
- View application status.
- Withdraw applications.
- Submit and manage enquiries.

### **HDB Officer**
- Manage assigned projects.
- View successful applicants.
- Book flats for applicants.
- Generate booking receipts.
- Handle public enquiries.

### **HDB Manager**
- Create, edit, and delete BTO projects.
- Manage officer applications.
- Approve or reject project applications.
- Generate reports for project applications.
- View and respond to project enquiries.

---

## Project Structure
The project is organized into the following directories:

```
code/
├── MainApp.java                # Entry point of the application
├── archive/                    # Deprecated or unused classes
├── database/                   # Database classes for managing data
│   ├── csv/                    # CSV files for data storage
│   └── dataclass/              # Classes for managing database operations
├── exception/                  # Custom exception classes
├── models/                     # Core models for users, projects, and enums
│   ├── enums/                  # Enums for statuses and types
│   ├── projects/               # Models related to BTO projects
│   └── users/                  # Models for user roles
├── services/                   # Service classes for business logic
│   ├── controller/             # Controllers for user interactions
│   ├── interfaces/             # Interfaces for services
│   └── subservices/            # Subservices for specific functionalities
├── utils/                      # Utility classes for file handling and filtering
└── view/                       # View classes for user interfaces
```

---

## Key Classes and Their Responsibilities

### **MainApp**
- Entry point of the application.
- Handles user authentication and navigation between menus.

### **Models**
- **`BTOProject`**: Represents a BTO project with details like units, prices, and officers.
- **`Applicant`**: Represents an applicant with their applications.
- **`HDBOfficer`**: Represents an officer with their project application and/or officer registration.
- **`HDBManager`**: Represents a manager with the project they manage.
- **`OfficerApplication`**: Represents an officer's application to manage a project.
- **`ProjectApplication`**: Represents an applicant's application for a project.

### **Controllers**
- **`AuthController`**: Manages the flow of authentication and account management processes.
- **`ApplicantController`**: Handles the flow of applicant-specific actions like applying for projects and managing enquiries.
- **`OfficerController`**: Manages the flow of officer-specific actions like booking flats and generating receipts.
- **`ManagerController`**: Handles the flow of manager-specific actions like creating projects and generating reports.

### **Services**
- **`ProjectManagementService`**: Handles project creation, editing, and deletion.
- **`EnquiryService`**: Create, responds, and edit project enquiries
- **`OfficerApplicationService`**: Handles the processes behind officer application, i.e. creation, approval, and refusal
- **`ProjectApplicationService`**: Handles the processes behind project application, i.e. creation, approval, withdrawal, refusal, and booking 
- **`ReportPrintService`**: Generates reports based on project applications.
- **`ReceiptPrintService`**: Generates booking receipts for applicants.

### **Database**
- **`ProjectDB`**: Manages project-related data.
- **`ApplicantDB`**: Manages applicant-related data.
- **`OfficerAppDB`**: Manages officer application data.

### **Utilities**
- **`FileLoader`**: Loads data from CSV files.
- **`FileSaver`**: Saves data to CSV files.
- **`FilterUtil`**: Provides filtering functionalities for projects and applications.

---

## How to Run the Project

### Prerequisites
- **Java Development Kit (JDK)**: Version 17 or higher.
- **IDE**: IntelliJ IDEA, Eclipse, or Visual Studio Code (recommended).

### Steps
1. Clone the repository or download the project files.
2. Open the project in your preferred IDE.
3. Ensure the csv folder contains the required CSV files:
   - `ApplicantList.csv`
   - `OfficerList.csv`
   - `ManagerList.csv`
   - `ProjectList.csv`
   - `EnquiresList.csv`
   - `ProjectApplicationList.csv`
   - `OfficerApplicationList.csv`
4. Run the MainApp.java file to start the application.

---

## Usage

### **Login**
1. Choose your role: Applicant, Officer, or Manager.
2. Enter your credentials to log in.

### **Applicant**
- Navigate through the applicant portal to apply for projects, manage applications, and submit enquiries.

### **Officer**
- Access the officer portal to manage assigned projects, handle enquiries, and generate receipts.

### **Manager**
- Use the manager portal to create and manage projects, approve/reject applications, and generate reports.

---

## Data Storage
- All data is stored in CSV files located in the csv directory.
- The application reads and writes data to these files using the `FileLoader` and `FileSaver` utility classes.

---

## Future Improvements
- Implement a graphical user interface (GUI) for better user experience.
- Add more robust exception handling and error validation.
- Integrate with a database management system (e.g., MySQL) for better scalability.
- Implementation of more time-efficient algorithm, such as Binary Search or Jump Search to retrieve information from database

---

## Contributor
1. Eben Lim   
2. Goh Yi Xiang  
3. Mun Jeong Won  
4. Nicholas Hendra Hartimin  
5. Mannan Gupta  

</br>

Professor: 
Prof. Shen Zi Qi & Dr. Li Fang

---



