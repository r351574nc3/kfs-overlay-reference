<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/jsp/sys/kfsTldHeader.jsp"%>

<%@ attribute name="accountingLine" required="false"
              description="The name in the form of the accounting line
              being edited or displayed by the row containing the cell containing this detail." %>
<%@ attribute name="detailField" required="false"
              description="The name of the accounting line field containing the detailed description
              of the value of this cell.  This attribute requires the accountingLine attribute.
              If this attribute is empty, this tag generates a blank line in the same CSS class,
              for consistent spacing." %>
<%@ attribute name="detailFields" required="false"
              description="The name of the accounting line fields containing detailed descriptions
              of the value of each cell. This attribute requires the accountingLine attribute.
              If this attribute is empty, this tag generates a blank line in the same CSS class,
              for consistent spacing. Any supplied field that starts with a semicolon will be treated as a text
              field, rather than a database field. The semicolon will be ignored in the output." %>
<%@ attribute name="dataFieldCssClass" required="false"
              description="The name of the CSS class for this data field." %>
<%@ attribute name="formattedNumberValue" required="false"
              description="number to format instead of property" %>              
              
<c:set var="class" value="${empty dataFieldCssClass ? 'fineprint' : dataFieldCssClass}"/> 
             
<c:if test="${!KualiForm.hideDetails}">
<%--    <br/> --%>
    <div id="${accountingLine}.${detailField}.div" class="${class}">
    <c:if test="${!empty detailFields}">
	    <c:forTokens var="key" items="${detailFields}" delims=",">
	        <c:set var="field" value="${key}"/>
			<c:choose>
			    <c:when test="${fn:startsWith(field,';')}">
	    			<c:out value="${fn:substringAfter(field,';')}" />
			    </c:when>
				<c:otherwise>		
					<bean:write name="KualiForm" property="${accountingLine}.${field}"/>
			    </c:otherwise>
    		</c:choose>    
	    </c:forTokens>    
    </c:if>
    
    <c:if test="${!empty detailField && empty detailFields && empty formattedNumberValue}">
        <bean:write name="KualiForm" property="${accountingLine}.${detailField}"/>
    </c:if>
    
    <c:if test="${!empty detailField && empty detailFields && not empty formattedNumberValue}">
        ${formattedNumberValue}
    </c:if>
  </div>
</c:if>
<c:if test="${!empty detailField}">
  <c:catch var="e">
 	<html:hidden property="${accountingLine}.${detailField}"/>
  </c:catch>
  <c:if test="${e!=null}">
    <input type="hidden" name="${accountingLine}.${detailField}" value="test"/>
  </c:if>
</c:if>

