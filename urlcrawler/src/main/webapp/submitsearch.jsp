<jsp:include page="top.jsp" />



<p style="font-size: 8px;">
<a href="${pageContext.request.contextPath}">HOME</a>/Submit Search
</p>
<h1>Submit Search</h1>

<form action="submitsearch_" id="searchForm">
<input type="text" name="searchUrl" style="width: 300px;height: 50px" placeholder="http://searchurl">
<select name="maxDepth"  style="height: 50px">
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="10">10</option>
<option value="20">20</option>
<option value="40">40</option>
<option value="80">80</option>
</select>
<input type="submit" value="Submit URL" style="width: 100px;height: 50px">

</form>
<!-- the result of the search will be rendered inside this div -->
<div id="result"></div>
<script>
// Attach a submit handler to the form
$( "#searchForm" ).submit(function( event ) {
 
  // Stop form from submitting normally
  event.preventDefault();
 
  // Get some values from elements on the page:
  var $form = $( this );
  var  searchUrl = $form.find( "input[name='searchUrl']" ).val();
  var  maxDepth = $form.find( "input[name='maxDepth']" ).val();
  var  url = $form.attr( "action" );
 
  var postData = $(this).serializeArray();
  
  // Send the data using post
  var posting = $.post( url,postData );
 
  // Put the results in a div
  posting.done(function( data ) {
   // var content = $( data ).find( "#content" );
   // alert(data);
    $( "#result" ).empty().append(data );
  });
});
</script>

<h2>NOTES</h2>
<ul>
<li>Please enter a URL and link depth and submit the search. The search will run in a seperate thread
of a fixed sized thread pool.</li>

</ul>

<a href="javascript:history.back()">Go Back</a>

<jsp:include page="bottom.jsp" />

