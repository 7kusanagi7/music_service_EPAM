<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<head>
    <link href="${pageContext.request.contextPath}/static/css/footer-style.css" rel="stylesheet">
</head>

<footer class="footer">
    <div class="container">
        <hr>
        <p class="text-muted">&copy; 2021 &mdash; <a href="mailto:maxim.kiselyov07@gmail.com">maxim.kiselyov07@gmail.com</a>, <fmt:message key="footer.text"/></p>
    </div>
</footer>