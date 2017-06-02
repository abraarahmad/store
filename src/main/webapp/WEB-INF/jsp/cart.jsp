<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="/WEB-INF/jsp/common/htmlheader.jsp" />
</head>
<body>
	<c:import url="/WEB-INF/jsp/common/header.jsp" />

	<div class="container">
	<c:forEach var="message" items="${it.messages}" varStatus="status">
		<div  class="alert alert-block">
		<c:out value="${message}"/>
		</div>
	</c:forEach>

	<c:if test="${fn:length(it.cart.cartItems) > 0}">

		<c:forEach var="cartItem" items="${it.cart.cartItems}" varStatus="status">
		<div class="row">
			<form id="form-<c:out value="${cartItem.item.id}"/>" action="<c:url value="/bookstore/removeFromCart" />" method="POST">
				<div class="span2">
					<img src="<c:url value="${cartItem.item.picture}" />" id="form-<c:out value="${cartItem.item.id}"/>-item-image"/>
				</div>
				<div class="span8">
					<div>
						<h2 id="form-<c:out value="${cartItem.item.id}"/>-item-name"><c:out value="${cartItem.item.name }"/></h2>
					</div>
					<div id="form-<c:out value="${cartItem.item.id}"/>-item-price">
						価格 : <c:out value="${cartItem.item.price }"/> 円
					</div>
					<div id="form-<c:out value="${cartItem.item.id}"/>-item-amount">
						数量 : <c:out value="${cartItem.relation.amount }"/> 個
					</div>
					<input type="hidden" name="item-id" value="<c:out value="${cartItem.item.id}"/>" id="itemId-<c:out value="${cartItem.item.id}"/>">
					<input class="btn" type="submit" value="取消" id="form-<c:out value="${cartItem.item.id}"/>-item-cancel">
				</div>
			</form>
		</div>
		</c:forEach>

		<div class="row">
			<h2>合計</h2>

			<div id="total-price">
				<c:out value="${it.cart.total}"/> 円
			</div>
		</div>

		<div class="row">
			<div class="span2">
				<form id="form-clear-cart" action="<c:url value="/bookstore/clearCart" />" method="POST">
					<input class="btn" type="submit" value="カートをクリア" id="celar-cart">
				</form>
			</div>

			<c:if test="${it.cart.valid}">
				<div class="span2">
					<a class="btn" href="<c:url value="/bookstore/prepareOrder"/>" id="purchase-order">購入画面へ進む</a>
				</div>
			</c:if>
		</div>

	</c:if>
	<c:if test="${fn:length(it.cart.cartItems) <= 0}">

	<h2 id="empty-cart-title">カートは空です</h2>

	</c:if>
		<div class="row">
			<div class="span2">
				<a class="btn" href="<c:url value="/bookstore/"/>" id="show-item-list">買い物を続ける</a>
			</div>
		</div>
	</div>
</body>
</html>