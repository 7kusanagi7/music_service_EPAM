<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/payment-style.css">
</head>

<div class="modal fade" tabindex="-1" id="payment">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="col-xs-12 col-md-12">
                <!-- CREDIT CARD FORM STARTS HERE -->
                <div class="panel panel-default credit-card-box">
                    <div class="panel-heading display-table" >
                        <div class="row display-tr" >
                            <h3 class="panel-title display-td"><fmt:message key="payment.details"/></h3>
                            <div class="display-td" >
                                <img class="img-responsive pull-right" src="${pageContext.request.contextPath}/static/img/accepted.png">
                            </div>
                        </div>
                    </div>
                    <div class="panel-body">
                        <c:choose>
                        <c:when test="${sessionScope.user.loan >= 0}">
                            <form role="form" id="payment-form" method="POST" action="${pageContext.request.contextPath}/cart/checkout">

                        </c:when>
                        <c:otherwise>
                            <form role="form" id="payment-form" method="POST" action="${pageContext.request.contextPath}/cart/pay-loan">
                        </c:otherwise>
                        </c:choose>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="form-group">
                                        <label for="cardNumber"><fmt:message key="payment.card"/></label>
                                        <div class="input-group">
                                            <input
                                                    type="tel"
                                                    class="form-control"
                                                    name="cardNumber"
                                                    placeholder="<fmt:message key="payment.holder.card"/>"
                                                    autocomplete="cc-number"
                                                    required autofocus
                                                    id="cardNumber"
                                            />
                                            <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-7 col-md-7">
                                    <div class="form-group">
                                        <label for="cardExpiry"><fmt:message key="payment.expiry"/></label>
                                        <input
                                                type="tel"
                                                class="form-control"
                                                name="cardExpiry"
                                                placeholder="<fmt:message key="payment.holder.expiry"/>"
                                                autocomplete="cc-exp"
                                                required
                                                id="cardExpiry"
                                        />
                                    </div>
                                </div>
                                <div class="col-xs-5 col-md-5 pull-right">
                                    <div class="form-group">
                                        <label for="cardCVC"><fmt:message key="payment.cvc"/></label>
                                        <input
                                                type="tel"
                                                class="form-control"
                                                name="cardCVC"
                                                placeholder="<fmt:message key="payment.cvc"/>"
                                                autocomplete="cc-csc"
                                                required
                                                id="cardCVC"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class='col-xs-12'>
                                    <div class='form-control total btn btn-info'>
                                        <fmt:message key="payment.total"/>:
                                        <span class='amount'><c:choose>
                                            <c:when test="${sessionScope.user.loan >= 0}">
                                                ${sessionScope.totalPrice}
                                            </c:when>
                                            <c:otherwise>
                                                ${-sessionScope.user.loan}
                                            </c:otherwise>
                                        </c:choose> <fmt:message key="currency.value"/></span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <button class="subscribe btn btn-success btn-lg btn-block" type="submit"><fmt:message key="payment.submit"/></button>
                                </div>
                            </div>
                        </form>
                            <div class="row" style="display:none;">
                                <div class="col-xs-12">
                                    <p class="payment-errors"></p>
                                </div>
                            </div>
                            <hr>
                        <c:if test="${sessionScope.user.loan >= 0}">
                            <div class="alert alert-warning" role="alert">
                                <i class="fas fa-exclamation-triangle"></i> <fmt:message key="payment.credit.text"/>:
                            </div>
                            <div class="row">
                                <div class='col-xs-12'>
                                    <form method="POST" action="${pageContext.request.contextPath}/cart/checkout">
                                        <input type="hidden" name="credit" value="true">
                                        <button type="submit" class="btn btn-danger btn-block credit"><fmt:message key="payment.credit"/></button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
                <!-- CREDIT CARD FORM ENDS HERE -->
            </div>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.payment/1.2.3/jquery.payment.min.js"></script>
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/payment.js"></script>

