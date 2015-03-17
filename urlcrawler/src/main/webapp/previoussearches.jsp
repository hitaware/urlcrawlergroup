<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="top.jsp" />


<p style="font-size: 8px;">
	<a href="${pageContext.request.contextPath}">HOME</a>/Previous Searches
</p>

<h1>Previous Searches</h1>

<table>
	<tr style="font-weight: bold;">		
		<td>Search URL</td>
		<td>Link Depth</td>
	</tr>
	<c:forEach var="job" items="${jobs}">

		<tr>
			<td><c:out value="${job.searchUrl}" /></td>
			<td><c:out value="${job.maxDepth}" /></td>
		</tr>
	</c:forEach>
</table>

<a href="javascript:history.back()">Go Back</a>
<jsp:include page="bottom.jsp" />

