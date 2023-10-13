package edu.tdtu._lab4;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


@WebServlet(name = "ProductServlet", value = "/product")
public class ProductServlet extends HttpServlet {
    private List<Product> productsList;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        gson = new Gson();

        // Init the product list
        productsList = new ArrayList<>();
        productsList.add(new Product(1, "iphone XS", 623));
        productsList.add(new Product(2, "nokia lumia", 112));
        productsList.add(new Product(3, "galaxy note 10", 549));
        productsList.add(new Product(4, "mi 11 ", 369));
        productsList.add(new Product(5, "reno 7", 408));
        productsList.add(new Product(6, "bphone", 999));
    }

    private Product getProductByID (int id) {
        for (Product product : productsList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.equals("")) {
            ResponseApi res;
            if (productsList.isEmpty()) {
                res = new ResponseApi(1, "Không tìm thấy sản phẩm", null);
            } else {
                res = new ResponseApi(0, "Đọc sản phẩm thành công", productsList);
            }

            response.setContentType("application/json");
            response.getWriter().println(gson.toJson(res));

        } else {
            try {
                Product product = getProductByID(Integer.parseInt(id));

                if (product != null) {
                    ResponseApi res = new ResponseApi(0, "Đọc sản phẩm thành công", product);
                    response.setContentType("application/json");
                    response.getWriter().println(gson.toJson(res));
                } else {
                    ResponseApi res = new ResponseApi(2, "Không tìm thấy sản phẩm với ID:" + id, null);
                    response.setContentType("application/json");
                    response.getWriter().println(gson.toJson(res));
                }
            } catch (NumberFormatException e) {
                ResponseApi res = new ResponseApi(3, "Mã sản phẩm ko phù hợp", null);
                response.setContentType("application/json");
                response.getWriter().println(gson.toJson(res));
            }
        }
    }


    // POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idValue = request.getParameter("id");
        String name = request.getParameter("name");
        String priceValue = request.getParameter("price");

        if (idValue == null || name == null || priceValue == null) {
            ResponseApi res = new ResponseApi(5, "Thiếu thông tin sản phẩm", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(res));
            return;
        }

        int id;
        double price;
        try {
            id = Integer.parseInt(idValue);
            price = Double.parseDouble(priceValue);

        } catch (NumberFormatException e) {
            ResponseApi res = new ResponseApi(6, "ID hoặc giá không hợp lệ", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(res));
            return;
        }

        // Check if the product ID already exists
        if (getProductByID(id) != null) {
            ResponseApi res = new ResponseApi(7, "Đã tồn tại sản phẩm với ID này", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(res));
            return;
        }

        // Create a new product and add it to the list
        Product newProduct = new Product(id, name, price);
        productsList.add(newProduct);

        ResponseApi res = new ResponseApi(0, "Thêm sản phẩm thành công", newProduct);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write(gson.toJson(res));
    }


    // PUT
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idValue = request.getParameter("id");
        String name = request.getParameter("name");
        String priceValue = request.getParameter("price");

        if (idValue == null || name == null || priceValue == null) {
            ResponseApi res = new ResponseApi(8, "Thiếu thông tin sản phẩm", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(res));
            return;
        }

        int id;
        double price;

        try {
            id = Integer.parseInt(idValue);
            price = Double.parseDouble(priceValue);

        } catch (NumberFormatException e) {
            ResponseApi res = new ResponseApi(9, "ID hoặc giá không hợp lệ", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(res));
            return;
        }


        Product existingProduct = getProductByID(id);
        if (existingProduct == null) {
            ResponseApi res = new ResponseApi(10, "Sản phẩm với ID này không tồn tại", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(gson.toJson(res));
            return;
        }


        existingProduct.setName(name);
        existingProduct.setPrice(price);

        ResponseApi res = new ResponseApi(0, "Product updated successfully", existingProduct);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(res));
    }



    // DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idValue = request.getParameter("id");

        if (idValue == null || idValue.isEmpty()) {
            ResponseApi res = new ResponseApi(11, "Missing product ID for deletion", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(res));
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idValue);
        } catch (NumberFormatException e) {
            ResponseApi res = new ResponseApi(12, "Invalid product ID format for deletion", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(res));
            return;
        }

        Product existingProduct = getProductByID(id);
        if (existingProduct == null) {
            ResponseApi res = new ResponseApi(13, "Product with the given ID does not exist", null);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(gson.toJson(res));
            return;
        }

        // Delete the product
        productsList.remove(existingProduct);

        ResponseApi res = new ResponseApi(0, "Product deleted successfully", existingProduct);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(res));
    }
}