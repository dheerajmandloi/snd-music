import jakarta.servlet.RequestDispatcher;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/Upload")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50   // 50MB
)
public class Upload extends HttpServlet {
    private static final String UPLOAD_DIR = "UploadedFiles";
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/music_db";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String filePath="";
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
      
        System.out.println("value="+uploadPath);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

       
        String fileName = null;
        System.out.println("partsss==="+request.getParts());
        for (Part part : request.getParts()) {
            System.out.println("Value="+extractFileName(part));
        fileName = extractFileName(part);
         filePath = uploadPath + File.separator + fileName;
        part.write(filePath); // Save file
    
}
        
        System.out.println("FileName : "+fileName);
        
        try{
            //String filePathName=uploadPath + File.separator + fileName;
        String songName = request.getParameter("name");
        String singerName = request.getParameter("singer");
        String moviealbum = request.getParameter("movie");
        String releaseYear = request.getParameter("rd"); 
        String language = request.getParameter("lang");

        // Store data in database
       
        Class.forName("com.mysql.cj.jdbc.Driver");
        //make connection
        Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/web","root","sneha");      

        PreparedStatement stmt=cn.prepareStatement("insert into song(name,singer,movie_album,release_date,filename,language) values(?,?,?,?,?,?)");
       
       
            stmt.setString(1, songName);
            stmt.setString(2, singerName);
            stmt.setString(3, moviealbum);
            stmt.setString(4, releaseYear);
            stmt.setString(5, fileName);
            
            stmt.setString(6, language);
           
             int executeupdate = stmt.executeUpdate();
            cn.close();

            if (executeupdate > 0) {  
                request.setAttribute("message", "Upload Successful!");
           
         RequestDispatcher rd = request.getRequestDispatcher("adminhome.html");
         rd.forward(request, response);
            } else {
                request.setAttribute("message", "Upload Failed!");
                RequestDispatcher rd = request.getRequestDispatcher("upload.html");
            rd.forward(request, response);
            }

           
       cn.close();
       
        }
        catch (Exception e) {
        out.println("<h3>Error: " + e + "</h3>");
}
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
}