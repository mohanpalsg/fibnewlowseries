<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import = "java.io.*,java.util.*" %>

<script type="text/javascript">



        function OthertechFormChange() {
          	
        	
        	
        	
        	$.post(
                    "Fibonaccilevel", 
                    {
                     
                     
                     pricedifflow : $("#lowvalue").val(),
                     chartinterval : $( "#PeriodSelect option:selected" ).val(),
					 pricediffhigh : $("#highvalue").val()
                  
                   
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	  
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
	   <td>REF-LEVEL</td>
       <td>REF-PRICE</td>
       <td>WillR</td>
       <td>rsi</td>
       <td>SK</td>
       <td>SD</td>
       <td>LTP_DIFF</td>
       <td>HIGH_DIFF</td>
       <td>LOW_DIFF</td>
       
     
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getLasttradedprice()}</td>
<td>${entry.value.getLevel()}</td>
<td>${entry.value.getLevelvalue()}</td>
<td>${entry.value.getWilliamsr()}</td>
<td>${entry.value.getRsi()}</td>
<td>${entry.value.getStochk()}</td>
<td>${entry.value.getStochd()}</td>
<td>${entry.value.getLTPdiff()}</td>
<td>${entry.value.getHighdiff()}</td>
<td>${entry.value.getLowdiff()}</td>



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
                             
 <select id="PeriodSelect" onchange="OthertechFormChange()" >
 

 <option value="900"  <c:if test="${Minselect eq 900}">
selected
</c:if>>Monthly</option>

 <option value="600"  <c:if test="${Minselect eq 600}">
selected
</c:if>>Weekly</option>

<option value="60"  <c:if test="${Minselect eq 300}">
selected
</c:if>>Daily</option>

</select>

</div>
</div>
       
						
						 
							 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Range Filters</p>
                            </div>
                             <div class="panel-body">          
         

Price-highdiff<input id="highdiffslider" type="range" value="<c:out value="${highdiff}"/>" min="0" max="50" step="0.5"  onchange="OthertechFormChange()" oninput="highvalue.value =highdiffslider.value">
<output name="highdiffoname" id="highvalue"><c:out value="${highdiff}"/></output> 
<br>

Price-lowdiff<input id="lowdiffslider" type="range" value="<c:out value="${lowdiff}"/>" min="-50" max="0" step="0.5"  onchange="OthertechFormChange()" oninput="lowvalue.value=lowdiffslider.value">
<output name="lowdiffoname" id="lowvalue"><c:out value="${lowdiff}"/></output> 






</div>

</div>
</div>
</div>
</div>
</div>
</div>