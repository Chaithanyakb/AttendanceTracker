<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Attendance</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <div class="container">
        <h2>Check Attendance Report</h2>
        
        <form action="ViewAttendanceServlet" method="GET">
            <div class="form-group">
                <label for="student_id">Enter Student ID</label>
                <input type="text" id="student_id" name="student_id" required>
            </div>
            <button type="submit" class="btn">Check Percentage</button>
        </form>
        
        
        <div class="report-section">
            <%
                // Get the attribute set by ViewAttendanceServlet
                Object percentageObj = request.getAttribute("percentage");
            
                // Check if it's not null
                if (percentageObj != null) {
                    double percentage = (Double) percentageObj;
            %>
                        <h3>Attendance Report:</h3>
                        <p>The student's attendance is:</p>
                        <strong><%= String.format("%.2f", percentage) %>%</strong>
            <%
                }
            %>
            
            <%
                // Check for any error messages
                Object errorMsg = request.getAttribute("error");
                if (errorMsg != null) {
            %>
                        <p style="color: red;"><%= errorMsg %></p>
            <%
                }
            %>
        </div>
        
    </div>

</body>
</html>