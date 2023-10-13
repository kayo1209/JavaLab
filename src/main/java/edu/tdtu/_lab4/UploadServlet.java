package edu.tdtu._lab4;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


@WebServlet(name = "UploadServlet", value = "/upload")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("upload.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get file name, file part, and other form parameters
        String fileName = request.getParameter("name");
        Part filePart = request.getPart("file");
        boolean overrideIfExists = "on".equals(request.getParameter("override"));

        // Get the file extension from the uploaded file
        String fileExtension = getFileExtension(filePart);

        // Check if the file extension is supported
        if (!isFileExtensionSupported(fileExtension)) {
            sendHtmlResponse(response, "Unsupported file extension");
            return;
        }

        // Create the uploads directory if it does not exist
        String uploadPath = getServletContext().getRealPath(UPLOAD_DIR);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Check if the file already exists
        File uploadedFile = new File(uploadDir, fileName + "." + fileExtension);
        if (uploadedFile.exists() && !overrideIfExists) {
            sendHtmlResponse(response, "File already exists");
            return;
        }

        // Save the uploaded file
        filePart.write(uploadedFile.getAbsolutePath());

        // Prepare the download link for the uploaded file
        String downloadLink = request.getContextPath() + "/download?file=" + fileName + "." + fileExtension;

        // Send the success response with the download link
        String successMessage = "File uploaded. <a href='" + downloadLink + "'>Click here to visit file</a>";
        sendHtmlResponse(response, successMessage);
    }

    private String getFileExtension(Part part) {
        String submittedFileName = part.getSubmittedFileName();
        return submittedFileName.substring(submittedFileName.lastIndexOf('.') + 1);
    }

    private boolean isFileExtensionSupported(String extension) {
        String supportedExtensions = "txt, doc, docx, img, pdf, rar, zip";
        return supportedExtensions.contains(extension.toLowerCase());
    }

    private void sendHtmlResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("<html><body>" + message + "</body></html>");
    }
}