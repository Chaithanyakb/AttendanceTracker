package com.attendance;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class LoadStudentsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String className = request.getParameter("class_name");
        String year = request.getParameter("year");
        String semester = request.getParameter("semester");
        String subject = request.getParameter("subject_name");
        String date = request.getParameter("date");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Mark Attendance</title>");
        out.println("<style>");
        out.println("body {font-family: Arial, sans-serif; background-color: #f9fbfd; text-align: center;}");
        out.println("table {margin: 30px auto; border-collapse: collapse; width: 70%;}");
        out.println("th, td {border: 1px solid #ddd; padding: 10px; text-align: center;}");
        out.println("th {background: #0056b3; color: white;}");
        out.println(".btn {padding: 10px 20px; background: #0056b3; color: white; border: none; border-radius: 5px; margin-top: 20px; cursor: pointer;}");
        out.println(".btn:hover {background: #003f7f;}");
        out.println("</style></head><body>");

        out.println("<h2>Attendance for " + className + " | Year: " + year + " | Semester: " + semester + "</h2>");
        out.println("<h3>Subject: " + subject + " | Date: " + date + "</h3>");

        out.println("<form action='AddClassAttendanceServlet' method='POST'>");
        out.println("<input type='hidden' name='class_name' value='" + className + "'>");
        out.println("<input type='hidden' name='year' value='" + year + "'>");
        out.println("<input type='hidden' name='semester' value='" + semester + "'>");
        out.println("<input type='hidden' name='subject_name' value='" + subject + "'>");
        out.println("<input type='hidden' name='date' value='" + date + "'>");

        // Example static student names
        List<String> studentNames = Arrays.asList(
                "Aarav", "Aditya", "Akshay", "Akhil", "Amruta", "Ananya", "Arjun", "Bhuvana",
                "Chaitra", "Darshan", "Divya", "Dinesh", "Gaurav", "Harsha", "Ishika", "Jayanth",
                "Karthik", "Kavya", "Lohith", "Manasa", "Manoj", "Nithin", "Pooja", "Pranav",
                "Priya", "Rakesh", "Raksha", "Rohit", "Sanjana", "Sathwik", "Shreya", "Sneha",
                "Suhas", "Tanvi", "Varun", "Vijay", "Vishal", "Yash", "Yogesh"
        );

        out.println("<table>");
        out.println("<tr><th>Student ID</th><th>Name</th><th>Status</th></tr>");

        for (int i = 1; i <= 64; i++) {
            String id = String.format("4CB23AI%03d", i);
            String name = studentNames.get((i - 1) % studentNames.size());
            out.println("<tr>");
            out.println("<td>" + id + "</td>");
            out.println("<td>" + name + "</td>");
            out.println("<td>");
            out.println("<select name='" + id + "'>");
            out.println("<option value='Present'>Present</option>");
            out.println("<option value='Absent'>Absent</option>");
            out.println("</select>");
            out.println("</td>");
            out.println("</tr>");
        }

        // ✅ These lines definitely print correctly
        out.println("</table>");
        out.println("<button type='submit' class='btn'>Submit Attendance</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
