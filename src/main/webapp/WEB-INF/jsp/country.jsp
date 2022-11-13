<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Choose Country for Public Holiday</h1>
<form action="country" method="post" modelAttribute="selectCountry">
    <table>
        <tr>
            <td>Country</td>
            <td>
                <select name="idCountry">
                    <c:forEach var="country" items="${countryList}">
                        <option value="${country.getCountryCode()}">${country.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Year</td>
            <td><input type="text" name="year"></td>
        </tr>
        <tr>
            <td>Month</td>
            <td>
                <select name="month">
                  <c:forEach items="${months}" var="monthName" varStatus="loop">
                      <option value="${loop.index}">${monthName}</option>
                  </c:forEach>
                </select>
            </td>
        </tr>
        
        <tr>
            <td><input type="submit" value="Submit"></td>
        </tr>
    </table>
</form>