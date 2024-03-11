<%@ page import="com.example.aston.enums.Country" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Weather API</title>
    <style><%@include file="css/style.css"%></style>
</head>
<body>
<c:set value="<%= Country.values()%>" var="countries"/>
<div class="container">
    <div ${empty country ? 'hidden' : ''} class="text">
        В городе ${country.name} температура составляет ${temperature} °C
        на ${dateTime}
    </div>
    <form method="get" action="weather">
        <label for="country"></label>
        <div class="controls">
            <select id="country" name="country">
                <c:forEach items="${countries}" var="countryName">
                    <option ${country.name == countryName.name ? 'selected' : ''} value="${countryName.toString()}">
                            ${countryName.name}
                    </option>
                </c:forEach>
            </select>
            <br/>
            <button>Запросить погоду</button>
        </div>
    </form>
</div>
</body>
</html>
