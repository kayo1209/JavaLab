package edu.tdtu._lab4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



@WebServlet(name = "HomeServlet", value = "/home/")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");

        if (page == null || page.isEmpty()) {
            // If the page parameter is missing, show the default welcome page
            response.getWriter().println("Welcome to our website");
        } else {
            String destination = null;
            switch (page) {
                case "about":
                    destination = "/about.jsp";
                    break;
                case "contact":
                    destination = "/contact.jsp";
                    break;
                case "help":
                    destination = "/help.jsp";
                    break;
                default:
                    response.getWriter().println("Invalid page parameter");
                    return;
            }

            request.getRequestDispatcher(destination).forward(request, response);
        }
    }
}
