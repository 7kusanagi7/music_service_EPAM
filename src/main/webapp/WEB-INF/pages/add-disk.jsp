<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<html lang="${locale}">
<head>
    <title><fmt:message key="disk.addDisk"/></title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>

<custom:header/>

<div class="container">
    <div class="row">
        <div class="col-md-12 ">
            <form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/admin/disk">
                <fieldset>
                    <legend><fmt:message key="disk.addDisk"/></legend>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="title"><fmt:message key="disk.title"/></label>
                        <div class="col-md-4">
                            <input id="title" required pattern="^[А-ЯЁA-Z][A-Za-z\u0400-\u04ff\s]{2,255}$" name="title" type="text" placeholder="<fmt:message key="disk.title"/>" class="form-control">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-md-4 control-label" for="coverImage"><fmt:message key="disk.image"/></label>
                        <div class="col-md-4">
                            <input id="coverImage" required name="coverImage" class="form-control" type="url" placeholder="<fmt:message key="disk.image"/> URL"
                                   pattern="^(https|http).+(jpg|svg|gif|png)$">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="price"><fmt:message key="disk.price"/></label>
                        <div class="col-md-4">
                            <input id="price" required pattern="^\d+(([.,])\d{1,2})?$" name="price" type="number" step="0.01" placeholder="<fmt:message key="disk.price"/>" class="form-control input-md" min="0" value="${disk.price}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label col-xs-12" for="genres"><fmt:message key="disk.genres"/></label>
                        <div class="col-md-4">
                            <select id="genres" required name="genres" data-style="btn-info" class="selectpicker form-control input-md" multiple data-max-options="5" data-live-search="true" title="<fmt:message key="disk.genres"/>" data-size="5">
                                <c:forEach items="${genres}" var="genre">
                                    <option>${genre.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" id="authors">
                        <label class="col-md-4 control-label col-xs-12" for="author"><fmt:message key="disk.author"/></label>
                        <div class="col-md-4">
                            <select id="author" name="author" data-style="btn-danger" data-actions-box="true" class="selectpicker form-control input-md show-tick" title="<fmt:message key="disk.author"/>">
                                <c:forEach items="${authors}" var="author">
                                    <option value="${author.id}">${author.fullName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="Description"><fmt:message key="disk.description"/></label>
                        <div class="col-md-4">
                            <textarea id="description" required name="description" placeholder="<fmt:message key="disk.description"/>" class="form-control input-md" rows="5"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" ></label>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-thumbs-up"></span> <fmt:message key="disk.add"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<custom:footer/>

<!-- Latest compiled and minified JavaScript -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/hideInput.js"></script>
</body>
</html>
