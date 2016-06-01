<%-- 
    Document   : index
    Author     : Maçãs
--%>

<%@page import="java.util.List"%>
<%@page import="modelo.Evento"%>
<%@page import="persistencia.EventoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema Evento</title>
    </head>
    <body>
        <h1>Eventos</h1>
<table>
  <%
      EventoDAO dao = new EventoDAO();
      List<Evento> eventos = dao.listar();
      
      for (Evento evento : eventos ) {
      %>
    <tr>
        Titulo:
    <th><%=evento.getTitulo()%>:</th> 
        Data Inicio: 
    <td><%=evento.getData_inicial()%></td>
        Categoria:
    <td><%=evento.getCategoria_evento()%></td>
        Descrição:
    <td><%=evento.getDescricao_evento()%></td>
        Area Evento:
    <td><%=evento.getArea_evento()%></td>
        Data inicio das inscrições:
    <td><%=evento.getData_inicial_inscricao()%></td>
   </tr>
<%
}
%>
</table>
    </body>
</html>
