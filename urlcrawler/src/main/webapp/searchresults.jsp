<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="top.jsp" />


<p style="font-size: 8px;">
	<a href="${pageContext.request.contextPath}">HOME</a>/Search Results
</p>
<h1>Search Results</h1>

<table>
	<tr style="font-weight: bold;">
		<td>Date Submitted</td>
		<td>Job ID</td>
		<td>Search URL</td>
		<td>Link Depth</td>
		<td>Status</td>
	</tr>
	<c:forEach var="job" items="${jobs}">

		<tr>
			<td><c:out value="${job.date}" /></td>
			<td> <a href="statistics?id=<c:out value="${job.id}"/>"><c:out value="${job.id}"/></a> </td>
			<td><c:out value="${job.searchUrl}" /></td>
			<td><c:out value="${job.maxDepth}" /></td>
			<td><c:choose>
					<c:when test="${job.finished}">
						<img src="images/ok.png">
					</c:when>
					<c:otherwise>
						<img src="images/running.gif">
					</c:otherwise>
				</c:choose></td>
		</tr>
	</c:forEach>
</table>



<a href="javascript:history.back()">Go Back</a>



<jsp:include page="bottom.jsp" />

