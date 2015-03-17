<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="top.jsp" />


<p style="font-size: 8px;">
	<a href="${pageContext.request.contextPath}">HOME</a>/Statistics
</p>
<h1>Statistics</h1>

<table>
	<tr style="font-weight: bold;">
		<td>Domain</td>
		<td>URLs</td>

	</tr>
	<c:forEach items="${domainmap}" var="domaindata"
		varStatus="domainmapstatus">

		<tr>
			<td><c:out value="${domaindata.key}" /></td>


			<td><select style="width: 300px">
					<c:forEach items="${domaindata.value}" var="domaindatapages"
						varStatus="domaindatapagesstatus">
            Pages: ${domaindatapages}
        	
					<option><c:out
								value="${fn:substring(domaindatapages.url, 0, 50)}" /></option>
					</c:forEach>
			</select></td>

		</tr>
	</c:forEach>
</table>

<a href="javascript:history.back()">Go Back</a>

<h2>Charts</h2>

<img src="${imageurl}"></img>

<p>
<a href="javascript:history.back()">Go Back</a>



<jsp:include page="bottom.jsp" />

