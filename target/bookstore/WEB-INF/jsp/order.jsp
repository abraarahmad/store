<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="/WEB-INF/jsp/common/htmlheader.jsp" />
</head>
<body>
	<c:import url="/WEB-INF/jsp/common/header.jsp" />

	<div class="container">

		<c:forEach var="cartItem" items="${it.cart.cartItems}" varStatus="status">


		<div class="row">
			<div class="span2">
				<img src="<c:url value="${cartItem.item.picture}" />"  id="row-<c:out value="${cartItem.item.id}"/>-item-image"/>
			</div>

			<div class="span8">
				<div>
					<h2 id="row-<c:out value="${cartItem.item.id}"/>-item-name"><c:out value="${cartItem.item.name }"/></h2>
				</div>
				<div id="row-<c:out value="${cartItem.item.id}"/>-item-price">
					価格 : <c:out value="${cartItem.item.price }"/> 円
				</div>
				<div id="row-<c:out value="${cartItem.item.id}"/>-item-amount">
					数量 : <c:out value="${cartItem.relation.amount }"/> 個
				</div>
			</div>
		</div>

		</c:forEach>

		<div class="row">
			<h2>合計</h2>

			<div id="total-price">
				<c:out value="${it.cart.total}"/> 円
			</div>
		</div>

		<div class="row">
			<a class="btn" href="<c:url value="/bookstore/showCart"/>" id="show-cart">戻る</a>
		</div>

		<div class="row">
			<form id="form-order" class="form-horizontal" action="<c:url value="/bookstore/order" />" method="POST">
				<fieldset>
				<div class="control-group">
					<label class="control-label">氏名 : </label>
					<input name="name" value="<c:out value="${it.customer.name}"/>" id="customer-name"/>
					<c:if test="${it.messages['name'] != null}">
					<div id="customer-name-error"><c:out value="${it.messages['name']}"/></div>
					</c:if>
				</div>
				<div class="control-group">
					<label class="control-label">住所 : </label>
					<input name="address" value="<c:out value="${it.customer.address}"/>" id="customer-address"/>
					<c:if test="${it.messages['address'] != null}">
					<div id="customer-address-error"><c:out value="${it.messages['address']}"/></div>
					</c:if>
				</div>
				<div class="control-group">
					<input class="btn btn-primary" type="submit" value="送信" id="send-order"/>
				</div>
				</fieldset>
			</form>
		</div>

	</div>

</body>
</html>