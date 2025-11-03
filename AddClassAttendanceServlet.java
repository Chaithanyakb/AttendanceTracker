package com.attendance;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddClassAttendanceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String className = request.getParameter("class_name");
        String date = request.getParameter("date");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "");

            for (int i = 1; i <= 64; i++) {
                String studentId = String.format("4CB23AI%03d", i);
                String status = request.getParameter(studentId);

                if (status != null) {
                    String sql = "INSERT INTO attendance (student_id, class_name, date, status) VALUES (?, ?, ?, ?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, studentId);
                    ps.setString(2, className);
                    ps.setString(3, date);
                    ps.setString(4, status);
                    ps.executeUpdate();
                }
            }

            // Redirect to report
            response.sendRedirect("report.html");

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        } finally {
            try { if (ps != null) ps.close(); if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}
