# Recruit.me
Our project is built to serve the purpose of simplifying and enchancing recruitment process in industries. The companies just need to float a form. The form asks information from student about user details of competitive programming sites (like
[codechef](https://www.codechef.com), [hackerrank](https://www.hackerrank.com/)) or development platform (like [github](https://github.com/)). After successful submission of the form, the user's performance and contribution on those sites will be extracted. This information will be
represented in a csv file. The web App is  designed for students and Interviewers.

## Key Features
- Auto scraping of data from sites like codechef, hackerrank, github
- Send scraped info of applicants back to them as csv file via email
- Side by side comparision of data of all applicants.
- Invite selected applicants for further recruitment process via email


## Tech Stack
- Servlet
- Java JDK 9 for server-side management
- Apache Tomcat 8.0
- Postgres 9.4 as database
- HTML, CSS, Bootstrap for frontend UI
- JavaScript for client side scripting

## Dependencies
- [Java Mail](https://www.oracle.com/technetwork/java/index-138643.html "Java Mail")
- [JAF](https://www.oracle.com/technetwork/java/jaf11-139815.html "JAF")
- [Jaunt](http://jaunt-api.com/download.htm "Jaunt")
- [PostgresSQL JDBC driver](https://jdbc.postgresql.org/download.html "PostgresSQL JDBC driver")

All dependencies are available in folder called **AllExternalJARS** 

## How to run
1.  Open folder **WP** in folder called **IntegretedProject** in Eclipse.
2. Import dependencies from folder called **AllExternalJARS**. <br>
**Note:-** Jaunt expires every month and needs to be updated. Download latest jar from  [Here](http://jaunt-api.com/download.htm "here").
3. Execute the SQL script in **testDB.txt** in postgres
4. Run the project as server in eclipse
5. Access the website on localhost

## DEMO
To watch complete video on youtube click [Here](https://www.youtube.com/watch?v=8xoCqm1-tL4&t=112s "Here")
