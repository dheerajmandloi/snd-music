
<%-- 
    Document   : show_all_song
    Created on : 26 Apr 2025, 6:51:10 pm
    Author     : Dheeraj Mandloi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            
try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Web","root","sneha");
            PreparedStatement ps=cn.prepareStatement("select * from song");
           
            
            
            ResultSet rs=ps.executeQuery();
          
            %>
            <table border="2">
                <caption><h2>All Songs!</h2></caption>
                <tr><th>ID</th><th>Name</th><th>Movie/Album</th><th>Singer</th><th>Language</th><th>Release Date</th><th>Song</th>
            <%
           while(rs.next())
           {
           String id=rs.getString(1);
           String name=rs.getString(2);
           String movie_album=rs.getString(3);
           String singer=rs.getString(4);
           String language=rs.getString(5);
           String release_date=rs.getString(6);
           String filename=rs.getString(7);
           %>
                        <tr><td><%=id%></td><td><%=name%></td><td><%=movie_album%></td><td><%=singer%></td><td><%=language%></td><td><%=release_date%></td><td>
                    
                                <audio controls>
  
  <source src="UploadedFiles/<%=filename%>" type="audio/mpeg">
  Your browser does not support the audio tag.
</audio>
                                
                                
                    </td></tr>
                <%
            }
            out.println("</table>");
            }
               
        catch(Exception e){
            System.out.println(e);
            }
    
            
            %>
    </body>
</html>
