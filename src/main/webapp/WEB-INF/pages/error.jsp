<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<!DOCTYPE html>
<html lang="${locale}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Error</title>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/error-style.css">
</head>
<body>

<custom:header/>

<div class="container error">
    <div class="jumbotron">
        <div class="text-center"><i class="fa fa-5x fa-frown-o" style="color:#d9534f;"></i></div>
        <h1 class="text-center">${pageContext.errorData.statusCode}<p><small class="text-center"><fmt:message key="error.small"/></small></p></h1>
        <c:choose>
            <c:when test="${pageContext.errorData.statusCode eq 404}">
                <h3 class="text-center"><fmt:message key="error.404.cause"/></h3>
            </c:when>
            <c:when test="${pageContext.errorData.statusCode eq 403}">
                <h3 class="text-center"><fmt:message key="error.403.cause"/></h3>
            </c:when>
            <c:when test="${pageContext.errorData.statusCode eq 500}">
                <h2 class="text-center"><fmt:message key="error.500.cause"/></h2>
            </c:when>
            <c:otherwise>
                <h3 class="text-center">${requestScope['javax.servlet.error.message']}</h3>
            </c:otherwise>
        </c:choose>
        <br>
        <p class="text-center"><fmt:message key="error.text"/></p>
        <p class="text-center"><a class="btn btn-primary" href="${pageContext.request.contextPath}/"><i class="fa fa-home"></i> <fmt:message key="error.button"/></a></p>
    </div>
</div>

<custom:footer/>

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>