<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<html lang="${locale}">
<head>
    <title><fmt:message key="nav.cart"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/cart-style.css">
    <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>
</head>
<body>

<custom:header/>

<div class="container">
    <table id="cart" class="table table-hover table-condensed">
        <thead>
        <tr>
            <th style="width:50%"><fmt:message key="cart.disk"/></th>
            <th style="width:10%"><fmt:message key="cart.price"/></th>
            <th style="width:8%"><fmt:message key="cart.quantity"/></th>
            <th style="width:22%" class="text-center"><fmt:message key="cart.subtotal"/></th>
            <th style="width:10%"></th>
        </tr>
        </thead>
        <c:forEach items="${sessionScope.cartProducts}" var="product">
            <tbody>
            <tr>
                <td data-th="Product">
                    <div class="row">
                        <div class="col-sm-2 hidden-xs"><img src="${product.coverImage}" class="img-responsive"/></div>
                        <div class="col-sm-10">
                            <h4 class="nomargin">${product.title}</h4>
                        </div>
                    </div>
                </td>
                <form action="${pageContext.request.contextPath}/cart/change-price" method="post" class="parent-custom${product.id}">
                    <td data-th="Price" class="price">${product.price} <fmt:message key="currency.value"/></td>
                    <td data-th="Quantity">
                        <input type="number" name="quantity" class="form-control text-center input-custom" min="1" value="${sessionScope.quantities[product.id]}">
                        <input type="hidden" id="command-id" name="diskId" value="${product.id}">
                    </td>
                    <td data-th="Subtotal" id="subtotal${product.id}" class="text-center">${product.price * sessionScope.quantities[product.id]}</td>
                </form>
                <td class="actions" data-th="">
                    <form method="post" action="${pageContext.request.contextPath}/cart/delete">
                        <input type="hidden" name="diskId" value="${product.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm"><i class="fas fa-trash"></i></button>
                    </form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
        <tfoot>
        <tr>
            <td><a href="${pageContext.request.contextPath}/disk" class="btn btn-warning"><i class="fa fa-angle-left"></i> <fmt:message key="cart.continue"/></a></td>
            <td colspan="2" class="hidden-xs"></td>
            <td class="hidden-xs text-center"><strong id="total" class="total">${sessionScope.totalPrice} <fmt:message key="currency.value"/></strong></td>
            <td><a class="btn btn-success btn-block" <c:if test="${not empty sessionScope.cartProducts}">data-toggle="modal" data-keyboard="true" data-target="#payment"</c:if> <c:if test="${empty sessionScope.cartProducts}">disabled</c:if>><fmt:message key="cart.checkout"/> <i class="fa fa-angle-right"></i></a></td>
        </tr>
        </tfoot>
    </table>

    <jsp:include page="payment.jsp"/>

    <custom:footer/>

</div>
<script src="${pageContext.request.contextPath}/static/js/changePrice.js"></script>
</body>
</html>
