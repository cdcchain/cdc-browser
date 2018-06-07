<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctxPath" value="${pageContext.request.contextPath}" ></c:set>
<c:set var="staticPath" value="${ctxPath}/static" ></c:set>
<c:set var="version" value="20161115" ></c:set>
<script type="text/javascript">
var project = {
		ctxPath:"${ctxPath}",
		staticPath:"${staticPath}",
}
</script> 
<link rel="shortcut icon" href="${staticPath}/img/favicon.ico" />
