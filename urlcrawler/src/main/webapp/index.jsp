<jsp:include page="top.jsp" />


<p style="font-size: 8px;">HOME</p>

<h1>Welcome to URL Crawler Application</h1>

<h2>USAGE</h2>
<ul>
	<li>Go to "Submit Search" page in order to submit searches.
		Searches might take time to complete according to your search
		parameters. But don't worry they run asynchronously. So you can submit
		multiple searches and they can run simultaneously.</li>
	<li>You can follow the search results (completed/running) from
		"Search Results" page. This page shows the searches you've made in the
		current session.</li>
	<li>You can see your previous searches from "Previous Searches"
		page. This page shows the searches you've made in the current session
		and also in your previous sessions. They are based on cookies.</li>
</ul>
<h2>NOTES</h2>
<ul>
<li>Session timeout is 1 hour. You can see the difference
	between "Search Results" and "Previous Searches". You will notice that
	"Previous Searches"" will be preserved even after session timeout.</li>

</ul>
<jsp:include page="bottom.jsp" />
