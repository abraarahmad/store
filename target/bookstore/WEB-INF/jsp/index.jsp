<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="/WEB-INF/jsp/common/htmlheader.jsp" />
</head>

<body>
	<c:import url="/WEB-INF/jsp/common/header.jsp" />

	<div class="container">
		<c:forEach var="itemStock" items="${it.items}" varStatus="status">
			<form id="form-<c:out value="${itemStock.item.id}"/>"
				class="form-inline" action="<c:url value="/bookstore/addCart" />" method="POST">
				<div class="row">
					<div class="span2">
						<img src="<c:url value="${itemStock.item.picture}" />"
							id="form-<c:out value="${itemStock.item.id}"/>-item-image" />
					</div>
					<div class="span10">
						<div>
							<h2 id="form-<c:out value="${itemStock.item.id}"/>-item-name">
								<c:out value="${itemStock.item.name}" />
							</h2>
						</div>
						<div id="form-<c:out value="${itemStock.item.id}"/>-item-price">
							価格 :
							<c:out value="${itemStock.item.price}" />
							円
						</div>
						<div>
							<c:if test="${itemStock.stock.stock > 0}">
								<input type="hidden" name="item-id"
									value="<c:out value="${itemStock.item.id}"/>" />
								<select name="amount" class="span1"
									id="form-<c:out value="${itemStock.item.id}"/>-item-amount">
									<c:forEach begin="1" end="9" step="1" varStatus="status">
										<c:if test="${status.index <= itemStock.stock.stock }">
											<option value="${status.index}">
												<c:out value="${status.index}" />
											</option>
										</c:if>
									</c:forEach>
								</select>
								<input class="btn" type="submit" value="カートに追加"
									id="form-<c:out value="${itemStock.item.id}"/>-add-cart" />
							</c:if>
						</div>
					</div>
				</div>
			</form>
		</c:forEach>

		<div class="row">
			<a class="btn" href="<c:url value="/bookstore/showCart"/>"
				id="show-cart">カートを確認</a>
		</div>
	</div>
</body>
</html>