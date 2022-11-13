<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Public Holiday ${location.country_name}</h1>
    <table border="2" width="70%" cellpadding="2">
        <tr>
            <th>Date</th>
            <th>Description</th>
        </tr>
        <c:forEach var="holiday" items="${holidays}">
            <tr>
                <td>${holiday.date}</td>
                <td>${holiday.localName} ( ${holiday.name} )</td>
            </tr>
        </c:forEach>
    </table>
<br/>
<a href="/country">Others Country</a>