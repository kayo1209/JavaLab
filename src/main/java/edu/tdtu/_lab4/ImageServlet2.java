package edu.tdtu._lab4;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ImageServlet2", value = "/image2")
public class ImageServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("image2.jpg");
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"image2.jpeg\"");

        byte[] buffer = new byte[1024];
        int count;
        while ((count = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, count);
        }

        inputStream.close();
        outputStream.close();
    }
}