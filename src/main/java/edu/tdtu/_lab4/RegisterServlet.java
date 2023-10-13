package edu.tdtu._lab4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String birthtime = request.getParameter("birthtime");
        String gender = request.getParameter("gender");
        String country = request.getParameter("country");
        String[] favoriteIDEs = request.getParameterValues("favorite_ide[]");
        String toeic = request.getParameter("toeic");
        String message = request.getParameter("message");

        // Perform validation if needed
        if (name == null || name.isEmpty() ||
                email == null || email.isEmpty() ||
                birthday == null || birthday.isEmpty() ||
                birthtime == null || birthtime.isEmpty() ||
                gender == null || gender.isEmpty() ||
                country == null || country.isEmpty() ||
                favoriteIDEs == null ||
                toeic == null || toeic.isEmpty() ||
                message == null || message.isEmpty()) {

            response.getWriter().println("Missing information. Please fill in all fields.");
            return;
        }

        // Display the user information
        response.setContentType("text/html");
        response.getWriter().println("<h1>User Information:</h1>");
        response.getWriter().println("<p>Name: " + name + "</p>");
        response.getWriter().println("<p>Email: " + email + "</p>");
        response.getWriter().println("<p>Birthday: " + birthday + "</p>");
        response.getWriter().println("<p>Birthtime: " + birthtime + "</p>");
        response.getWriter().println("<p>Gender: " + gender + "</p>");
        response.getWriter().println("<p>Country: " + country + "</p>");
        response.getWriter().println("<p>Favorite IDEs: " + String.join(", ", favoriteIDEs) + "</p>");
        response.getWriter().println("<p>TOEIC Score: " + toeic + "</p>");
        response.getWriter().println("<p>Message: " + message + "</p>");
    }
}