To Run the code on a different computer quickly:

Click the green Code button near the top right.

Click Download ZIP.

Extract the ZIP file on your computer, open your terminal/command prompt inside that extracted folder.

1. Clone the Repository

Open your command prompt or terminal where you want to save the project and run:
git clone https://github.com/satwikalajangi/Student-Management-System.git
This downloads the entire folder structure
Navigate into the Project Folder //cd Student-Management-System

Since project uses an external database driver (sqlite-jdbc-3.53.1.0.jar), need to tell Java where to find that JAR file when compiling and running.
Compile all the Java files while linking the SQLite library:
javac -cp ".;sqlite-jdbc-3.53.1.0.jar" *.java
Run the main application:
java -cp ".;sqlite-jdbc-3.53.1.0.jar" StudentManagementApp
