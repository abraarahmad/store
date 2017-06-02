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
		<h1 id="thankyou-title">
			Thank you!
		</h1>

		<c:forEach var="cartItem" items="${it.orderedCart.cartItems}" varStatus="status">

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
				<c:out value="${it.orderedCart.total}"/> 円
			</div>
		</div>

		<div>
			<a class="btn" href="<c:url value="/bookstore/"/>" id="show-item-list">商品リストへ戻る</a>
		</div>
	</div>
</body>
</html>