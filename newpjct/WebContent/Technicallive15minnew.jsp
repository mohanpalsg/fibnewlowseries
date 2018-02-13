<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import = "java.io.*,java.util.*" %>

<script type="text/javascript">



        function OthertechFormChange() {
          	
        	
        	
        	
        	$.post(
                    "MynewTech15min", 
                    {
                     
                     chartinterval : $( "#PeriodSelect option:selected" ).val(),
                     pricedifflow : $("#lowvalue").val(),
					 pricediffhigh : $("#highvalue").val(),
                     loadsamedata : "true",
                     techfilter : $( "#Conditionselect option:selected" ).val()
                     
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	  
        } 
        
        
function OthertechtimeFormChange() {
          	
	
	 
        	
        	$.post(
                    "MynewTech15min", 
                    {
                     chartinterval : $( "#PeriodSelect option:selected" ).val(),
                     pricedifflow : $("#lowvalue").val(),
					 pricediffhigh : $("#highvalue").val(),
					 techfilter : $( "#Conditionselect option:selected" ).val()
                     
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	 $('#content').html("<div><br><br><br><br><p>Loding Data</p></div>");
        } 
        
        
        
        $('#dataTables-techlive15').DataTable({
            responsive: true
    });
        

        
     	 
</script>




<br>
<br>
<br>
<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Technical Indicators Live </p>
                            </div>
                             <div class="panel-body">
							 
							 
							 
							  <div class="dataTable_wrapper col-lg-10">
<table class="table table-striped table-bordered table-hover "  id="dataTables-techlive15">
       
<thead>

<tr>
       <td>Symbol</td>
       <td>LTP</td>
       <td>REF-PRICE</td>
       <td>WillR</td>
       <td>rsi</td>
       <td>SK</td>
       <td>SD</td>
       <td>LTP_DIFF</td>
       <td>HIGH_DIFF</td>
       <td>LOW_DIFF</td>
       <td>Trend</td>
     
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getLasttradedprice()}</td>
<td>${entry.value.getLevelvalue()}</td>
<td>${entry.value.getWilliamsr()}</td>
<td>${entry.value.getRsi()}</td>
<td>${entry.value.getStochk()}</td>
<td>${entry.value.getStochd()}</td>
<td>${entry.value.getLTPdiff()}</td>
<td>${entry.value.getHighdiff()}</td>
<td>${entry.value.getLowdiff()}</td>
<td>${entry.value.getTrend()}</td>


</tr>
    
</c:forEach>
</tbody>   

</table>
   </div>
   
    <div class="dataTable_wrapper col-lg-2">
	  <div class="panel panel-primary">
	  
	   <div class="panel-heading">
                                <p>Applied Filters</p>
                            </div>
							 <div class="panel-body">
							 
							 <div class="panel panel-info">
							  <div class="panel-heading">
                                <p>Candle Interval</p>
                            </div>
							
							<div class="panel-body">
							
							<select id="PeriodSelect" onchange="OthertechtimeFormChange()" >
 
 <option value="5"  <c:if test="${Minselect eq '5'}">
selected
</c:if>>5Min</option>
 

 <option value="15"  <c:if test="${Minselect eq '15'}">
selected
</c:if>>15Min</option>

 <option value="30"  <c:if test="${Minselect eq '30'}">
selected
</c:if>>30Min</option>

<option value="60"  <c:if test="${Minselect eq '60'}">
selected
</c:if>>1Hour</option>

<option value="120"  <c:if test="${Minselect eq '120'}">
selected
</c:if>>2Hour</option>

<option value="300"  <c:if test="${Minselect eq '300'}">
selected
</c:if>>Day</option>

<option value="600"  <c:if test="${Minselect eq '600'}">
selected
</c:if>>Week</option>



</select>
							
							<div>
						 </div>
						 </div>
						 <div class="panel panel-info">
						  <div class="panel-heading">
                                <p>Base Level</p>
                            </div>
							  <div class="panel-body">
							  <select id="Conditionselect" onchange="OthertechtimeFormChange()" >
 
 <option value="STDOWN"  <c:if test="${condselect eq 'STDOWN'}">
selected
</c:if>>TrendDn</option>
 

 <option value="STUP"  <c:if test="${condselect eq 'STUP'}">
selected
</c:if>>TrendUp</option>

  <option value="CMS1"  <c:if test="${condselect eq 'CMS1'}">
selected
</c:if>>Pivot S1</option>


  <option value="CMS2"  <c:if test="${condselect eq 'CMS2'}">
selected
</c:if>>Pivot S2</option>

  <option value="CMS3"  <c:if test="${condselect eq 'CMS3'}">
selected
</c:if>>Pivot S3</option>

 <option value="CMS4"  <c:if test="${condselect eq 'CMS4'}">
selected
</c:if>>Pivot S4</option>

  <option value="CMR1"  <c:if test="${condselect eq 'CMR1'}">
selected
</c:if>>Pivot R1</option>


  <option value="CMR2"  <c:if test="${condselect eq 'CMR2'}">
selected
</c:if>>Pivot R2</option>

  <option value="CMR3"  <c:if test="${condselect eq 'CMR3'}">
selected
</c:if>>Pivot R3</option>

 <option value="CMR4"  <c:if test="${condselect eq 'CMR4'}">
selected
</c:if>>Pivot R4</option>



</select>
							  
							  </div>
							
						 
						 
						 </div>
						 
							 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Range Filters</p>
                            </div>
                             <div class="panel-body">          
         

Price-highdiff<input id="highdiffslider" type="range" value="<c:out value="${highdiff}"/>" min="0" max="10" step="0.5"  onchange="OthertechFormChange()" oninput="highvalue.value =highdiffslider.value">
<output name="highdiffoname" id="highvalue"><c:out value="${highdiff}"/></output> 
<br>

Price-lowdiff<input id="lowdiffslider" type="range" value="<c:out value="${lowdiff}"/>" min="-10" max="0" step="0.5"  onchange="OthertechFormChange()" oninput="lowvalue.value=lowdiffslider.value">
<output name="lowdiffoname" id="lowvalue"><c:out value="${lowdiff}"/></output> 






</div>
</div>
</div>
</div>
</div>
</div>
</div>