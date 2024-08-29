# Google Classroom Replica

The main goal of this project is to replicate google classroom as much as possible. The frontend will in react and the backend will be in spring boot.

## Activity Overview

- In classroom, users can do tasks based on their role. There will be a functionality for uploading and viewing assignments.

- In order to do that, an individual classroom should be created first. In that, two types of users will remain : student, teacher.

- Initially there will be no user role. Any user can create a classroom. In that case the creator of the classroom will be considered as a teacher. The teacher can add user of both types. 

- When a classroom is created, a random hex code will be generated. Users who join via hex code will be considered as student. In order to join as a teacher, the classroom creator has to add the user as teacher.

- Before enrolling into classroom, the users must register into the system.

- After creating a room, users can give any post along with any files as attachments. In that post, anyone can comment.

- For assignment, a separate assignment post should be created by teachers. It can be seen both in posts section and assignment section. Within that assignment post, students can upload assignment. Additionally, teachers can attach a pdf/doc file into the assignment and put some description.

- Students should upload assignments in pdf/docx format. One student can not see other student's assignment.

- There should be a deadline for assignment. If any assignments are uploaded after the deadline, it should be flagged as late submit. Also teacher can turn off assignment submission after deadline.

## Role Wise Activity

### Teacher

- The creator of the classroom will be considered as teacher.

- The teacher can add users as teacher and students. He can only add those users who are registered in the classroom.

- The teacher can give posts and comment on post.

- Also he can provide assignment. While creating assignment, he can provide description and attach a file. Providing description is mandatory but attachment is optional. Also a deadline should be provided. The day deadline is fixed, the time will be till 11:59pm by default. Teachers can customize the time.

- After evaluating the assignments, teacher can grade the students. He can see all the student's grade in grade section.

- The teacher can delete the classroom.

### Student

- The students can join via a code or can be added in by teacher in classroom

- Students can post on classroom and comment on posts like teachers

- They can not create assignment and cannot see other people's assignment or grade

- If a student uploads after deadline, the assignment will be marked as late submit.

- One student can not see other student's assignment.

- Students can upload their assignment in pdf/docx format.