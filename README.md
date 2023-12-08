# Learning Management System

## Description
- This project aims to create a RESTful learning management system with a single-page application (SPA) frontend. It supports various functions such as student self-registration, course creation and management by administrators, and student learning process tracking.

## Function 1: Student Self-Registration
- Students can self-register by providing basic demographic and contact information.
### Business Rules:
- Students must be a minimum of 16 years old.
- Students cannot register more than once with the same email address.
- Mandatory fields include first name, last name, date of birth, address, email, and phone number.

## Function 2: Course Creation and Management
- Administrators can create and set up a set of courses.
### Business Rules:
- Course names must be unique.
- Only administrators have access to course management.
- Courses must be completed within 6 months of the start date.

## Function 3: Student Learning Process
- Students can select and start the learning process.
### Business Rules:
- Students cannot register or select more than three courses at a time.
- Students can log hours for the day by entering date, task category (e.g., researching, practicing, watching videos), task description, and time spent (in 30-minute increments).
- Students can log multiple hours for the same day.
- Students can update and delete a log entry.

