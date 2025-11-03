package com.attendance;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ViewStudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Manage Students</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f8f9fa;text-align:center;}");
        out.println("table{margin:30px auto;border-collapse:collapse;width:80%;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background:#0056b3;color:white;}");
        out.println(".btn{padding:6px 15px;border:none;border-radius:5px;color:white;cursor:pointer;}");
        out.println(".edit{background:#28a745;}");
        out.println(".delete{background:#dc3545;}");
        out.println(".add{background:#007bff;margin:20px auto;display:block;}");
        out.println("a{text-decoration:none;color:white;}");
        out.println("</style></head><body>");

        out.println("<h2>Student List</h2>");
        out.println("<button class='btn add'><a href='addstudent.html'>+ Add New Student</a></button>");
        out.println("<table>");
        out.println("<tr><th>Student ID</th><th>Name</th><th>Year</th><th>Semester</th><th>Class</th><th>Subject</th><th>Actions</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                String id = rs.getString("student_id");
                String name = rs.getString("name");
                int year = rs.getInt("year");
                int sem = rs.getInt("semester");
                String cls = rs.getString("class_name");
                String sub = rs.getString("subject_name");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + year + "</td>");
                out.println("<td>" + sem + "</td>");
                out.println("<td>" + cls + "</td>");
                out.println("<td>" + sub + "</td>");
                out.println("<td>");
                out.println("<a href='EditStudentServlet?id=" + id + "'><button class='btn edit'>Edit</button></a> ");
                out.println("<a href='DeleteStudentServlet?id=" + id + "'><button class='btn delete'>Delete</button></a>");
                out.println("</td>");
                out.println("</tr>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='7' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
        }

        out.println("</table></body></html>");
    }
}
