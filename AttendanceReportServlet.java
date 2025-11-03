package com.attendance;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AttendanceReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String className = request.getParameter("class_name");
        String type = request.getParameter("type");

        out.println("<html><head><title>Attendance Report</title>");
        out.println("<style>");
        out.println("body {font-family: Arial; background-color:#f7f9fc; text-align:center;}");
        out.println("table {margin: 30px auto; border-collapse: collapse; width: 80%;}");
        out.println("th, td {padding: 12px; border: 1px solid #ccc;}");
        out.println("th {background-color:#0056b3; color:white;}");
        out.println("tr:nth-child(even){background-color:#f2f2f2;}");
        out.println("</style></head><body>");

        out.println("<h2>Attendance Report for " + className + "</h2>");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "");

            // ✅ Get date range based on type
            String sql = "";
            if ("weekly".equals(type)) {
                sql = "SELECT student_id, COUNT(*) AS total_classes, " +
                      "SUM(CASE WHEN status='Present' THEN 1 ELSE 0 END) AS present_count " +
                      "FROM attendance WHERE class_name=? AND date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                      "GROUP BY student_id";
                out.println("<h3>Last 7 Days (Weekly)</h3>");
            } else if ("monthly".equals(type)) {
                sql = "SELECT student_id, COUNT(*) AS total_classes, " +
                      "SUM(CASE WHEN status='Present' THEN 1 ELSE 0 END) AS present_count " +
                      "FROM attendance WHERE class_name=? AND MONTH(date)=MONTH(CURDATE()) " +
                      "AND YEAR(date)=YEAR(CURDATE()) GROUP BY student_id";
                out.println("<h3>This Month</h3>");
            }

            ps = con.prepareStatement(sql);
            ps.setString(1, className);
            rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr><th>Student ID</th><th>Total Classes</th><th>Present</th><th>Percentage</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                int total = rs.getInt("total_classes");
                int present = rs.getInt("present_count");
                double percentage = (total == 0) ? 0 : (present * 100.0 / total);

                out.println("<tr>");
                out.println("<td>" + rs.getString("student_id") + "</td>");
                out.println("<td>" + total + "</td>");
                out.println("<td>" + present + "</td>");
                out.println("<td>" + String.format("%.2f", percentage) + "%</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            if (!found) {
                out.println("<p style='color:red;'>No attendance records found for this period.</p>");
            }

            out.println("<a href='addattendance.html'><button style='padding:10px 20px; background:#0056b3; color:white; border:none; border-radius:5px;'>Back</button></a>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (con != null) con.close(); } catch(Exception ex){}
        }
    }
}
