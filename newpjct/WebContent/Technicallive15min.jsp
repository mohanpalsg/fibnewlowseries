<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function OthertechFormChange() {
          	
        	
         
        	
        	$.post(
                    "Technicals15min", 
                    {
                     
                     commontechskrangeattr : $("#COMMONTECHSKRANGE").val(),
                     commontechsdrangeattr : $("#COMMONTECHSDRANGE").val(),
                     chartinterval : $( "#PeriodSelect option:selected" ).val(),
                     commontechdiffattr : $("#COMMONTECHDIFF").val()
                     
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	  
        } 
        
        
function OthertechtimeFormChange() {
          	
        	
	 
        	
        	$.post(
                    "Technicals15min", 
                    {
                    	 commontechskrangeattr : $("#COMMONTECHSKRANGE").val(),
                         commontechsdrangeattr : $("#COMMONTECHSDRANGE").val(),
                         chartinterval : $( "#PeriodSelect option:selected" ).val(),
                         commontechdiffattr : $("#COMMONTECHDIFF").val()
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	$('#content').html("Loading Data.....");
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

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-techlive15">
       
<thead>

<tr>
       <td>Symbol</td>
       <td>O-Price</td>
       <td>C-Price</td>
       <td>SK</td>
       <td>SD</td>
       <td>Down_LTP</td>
       <td>Down_LOW</td>
       <td>Up_LTP</td>
       <td>Up_HIGH</td>
       <td>Trend</td>
     
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getOpenprice()}</td>
<td>${entry.value.getCloseprice()}</td>
<td>${entry.value.getStochk()}</td>
<td>${entry.value.getStochd()}</td>
<td>${entry.value.getDown7diff()}</td>
<td>${entry.value.getDowndiff()}</td>
<td>${entry.value.getUp7diff()}</td>
<td>${entry.value.getUpdiff()}</td>
<td>${entry.value.getTrend()}</td>


</tr>
    
</c:forEach>
</tbody>   

</table>
   </div>
                            
                                <div class="dataTable_wrapper col-lg-3">
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
 
 <option value="5"  <c:if test="${Minselect eq 5}">
selected
</c:if>>5Min</option>
 

 <option value="15"  <c:if test="${Minselect eq 15}">
selected
</c:if>>15Min</option>

 <option value="30"  <c:if test="${Minselect eq 30}">
selected
</c:if>>30Min</option>

<option value="60"  <c:if test="${Minselect eq 60}">
selected
</c:if>>1Hour</option>

<option value="120"  <c:if test="${Minselect eq 120}">
selected
</c:if>>2Hour</option>

<option value="300"  <c:if test="${Minselect eq 300}">
selected
</c:if>>Day</option>




</select>

</div>
</div>
       
                 
 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Range Filters</p>
                            </div>
                             <div class="panel-body">          

          

Stock-k<input id="COMMONTECHSKRANGEslider" type="range" value="<c:out value="${Commontechskrange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHSKRANGE.value =COMMONTECHSKRANGEslider.value">
<output name="COMMONTECHSKRANGEoname" id="COMMONTECHSKRANGE"><c:out value="${Commontechskrange}"/></output> 
<br>
Stock-D<input id="COMMONTECHSDRANGEslider" type="range" value="<c:out value="${Commontechsdrange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHSDRANGE.value =COMMONTECHSDRANGEslider.value">
<output name="COMMONTECHSDRANGEoname" id="COMMONTECHSDRANGE"><c:out value="${Commontechsdrange}"/></output> 
<br>

Price-diff<input id="COMMONTECHDIFFslider" type="range" value="<c:out value="${Commontechdiff}"/>" min="-1" max="10" step="0.5"  onchange="OthertechFormChange()" oninput="COMMONTECHDIFF.value =COMMONTECHDIFFslider.value">
<output name="COMMONTECHDIFFoname" id="COMMONTECHDIFF"><c:out value="${Commontechdiff}"/></output> 
<br>




</div>
</div>
</div>
</div>
</div>

        </div>
</div>
</div>