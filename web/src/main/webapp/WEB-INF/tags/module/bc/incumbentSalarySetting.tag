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

<%@ attribute name="readOnly" required="false" description="determine whether the contents can be read only or not"%>

<c:set var="tableWidth" value="100%"/>
<c:set var="isKeyFieldsLocked" value="${KualiForm.singleAccountMode}"/>

<html:hidden property="budgetByAccountMode" />

<kul:tabTop tabTitle="Incumbent" defaultOpen="true">
	<div class="tab-container" align=center>
		<bc:incumbentInfo/>
	</div>
</kul:tabTop>

<kul:tab tabTitle="Incumbent Funding" defaultOpen="true" tabErrorKey="${BCConstants.ErrorKey.DETAIL_SALARY_SETTING_TAB_ERRORS}">
<div class="tab-container" align=center>
	<c:if test="${not readOnly}">
		<kul:subtab lookedUpCollectionName="fundingLine" width="${tableWidth}" subTabTitle="Add Funding">      
			<bc:appointmentFundingLineForIncumbent fundingLine="${KualiForm.newBCAFLine}" fundingLineName="newBCAFLine" countOfMajorColumns="11" isKeyFieldsLocked="${isKeyFieldsLocked}" hasBeenAdded="false">
				<html:image property="methodToCall.addAppointmentFundingLine.anchorsalarynewLineLineAnchor" 
			       	src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" 
			       	title="Add a Salary Setting Line" alt="Add a Salary Setting Line" styleClass="tinybutton"/>
			</bc:appointmentFundingLineForIncumbent>
		</kul:subtab>
	</c:if>
        		
    <c:forEach items="${KualiForm.budgetConstructionIntendedIncumbent.pendingBudgetConstructionAppointmentFunding}" var="fundingLine" varStatus="status">
	<c:if test="${!fundingLine.purged}">	
		<c:set var="fundingLineName" value="budgetConstructionIntendedIncumbent.pendingBudgetConstructionAppointmentFunding[${status.index}]"/>
    	<c:set var="editable" value="${!readOnly && !fundingLine.displayOnlyMode}"/>
		<c:set var="isVacant" value="${fundingLine.emplid eq BCConstants.VACANT_EMPLID}" />
		<c:set var="isNewLine" value="${fundingLine.newLineIndicator}" />
		<c:set var="hasBeenDeleted" value="${fundingLine.appointmentFundingDeleteIndicator}" />
		<c:set var="markedAsDelete" value="${!fundingLine.persistedDeleteIndicator && fundingLine.appointmentFundingDeleteIndicator}" />
		<c:set var="hidePercentAdjustment" value="${fundingLine.appointmentFundingDeleteIndicator || KualiForm.hideAdjustmentMeasurement || readOnly || empty fundingLine.bcnCalculatedSalaryFoundationTracker}" />
		
		<c:set var="canPurge" value="${editable && !fundingLine.purged && empty fundingLine.bcnCalculatedSalaryFoundationTracker}" />
		<c:set var="canUnpurge" value="${editable && fundingLine.purged}" />
		
		<c:set var="canDelete" value="${editable && !hasBeenDeleted && not isNewLine}" />
		<c:set var="canUndelete" value="${editable && hasBeenDeleted}" /> 
		
		<c:set var="canVacate" value="${false}"/>
		<c:set var="canRevert" value="${editable && markedAsDelete && not isVacant && not isNewLine && not fundingLine.vacatable}" />
	          	
	    <kul:subtab lookedUpCollectionName="fundingLine" width="${tableWidth}" subTabTitle="${fundingLine.appointmentFundingString}" >
	    	<bc:appointmentFundingLineForIncumbent fundingLine="${fundingLine}" fundingLineName="${fundingLineName}" countOfMajorColumns="11" 
	    		lineIndex="${status.index}" hasBeenAdded="true" readOnly="${readOnly}">    		
				
	    		<c:if test="${canVacate}">
					<html:image property="methodToCall.vacateSalarySettingLine.line${status.index}.anchorsalaryexistingLineLineAnchor${status.index}" 
						src="${ConfigProperties.externalizable.images.url}tinybutton-vacate.gif" 
						title="Vacate Salary Setting Line ${status.index}"
						alt="Vacate Salary Setting Line ${status.index}" styleClass="tinybutton" />
				</c:if>
				<c:if test="${canRevert}">	
					<html:image property="methodToCall.revertSalarySettingLine.line${status.index}.anchorsalaryexistingLineLineAnchor${status.index}" 
						src="${ConfigProperties.externalizable.images.url}tinybutton-revert1.gif" 
						title="revert Salary Setting Line ${status.index}"
						alt="revert Salary Setting Line ${status.index}" styleClass="tinybutton" />
				</c:if>				
				
				<c:if test="${canPurge}">	
					<html:image property="methodToCall.purgeSalarySettingLine.line${status.index}.anchorsalaryexistingLineLineAnchor${status.index}" 
						src="${ConfigProperties.externalizable.images.url}tinybutton-purge.gif" 
						title="Purge Salary Setting Line ${status.index}"
						alt="Purge Salary Setting Line ${status.index}" styleClass="tinybutton" />
				</c:if>
				
				<c:if test="${canDelete}">	
					<html:image property="methodToCall.deleteSalarySettingLine.line${status.index}.anchorsalaryexistingLineLineAnchor${status.index}" 
						src="${ConfigProperties.externalizable.images.url}tinybutton-delete1.gif" 
						title="Delete Salary Setting Line ${status.index}"
						alt="Delete Salary Setting Line ${status.index}" styleClass="tinybutton" />
				</c:if>
				
				<c:if test="${canUndelete}">	
					<html:image property="methodToCall.undeleteSalarySettingLine.line${status.index}.anchorsalaryexistingLineLineAnchor${status.index}" 
						src="${ConfigProperties.externalizable.images.url}tinybutton-undelete.gif" 
						title="undelete Salary Setting Line ${status.index}"
						alt="undelete Salary Setting Line ${status.index}" styleClass="tinybutton" />
				</c:if>
			</bc:appointmentFundingLineForIncumbent>	
		</kul:subtab>
	</c:if>
	</c:forEach>
        
    <kul:subtab lookedUpCollectionName="fundingLine" width="${tableWidth}" subTabTitle="Totals" > 
    	<table border="0" cellpadding="0" cellspacing="0" style="width: ${tableWidth};" class="datatable">    
			<tr><td class="infoline"><center><br/>
				<bc:appointmentFundingTotal pcafAware="${KualiForm.budgetConstructionIntendedIncumbent}"/>
			<br/></center></td><tr>
		</table>
	</kul:subtab>
</div>
</kul:tab>

<c:if test="${!readOnly}" >
	<kul:tab tabTitle="Purged Appointment Funding" defaultOpen="false">
	<div class="tab-container" align="center">        		
	    <c:forEach items="${KualiForm.budgetConstructionIntendedIncumbent.pendingBudgetConstructionAppointmentFunding}" var="fundingLine" varStatus="status">
			<c:if test="${fundingLine.purged}">	
				<c:set var="fundingLineName" value="budgetConstructionIntendedIncumbent.pendingBudgetConstructionAppointmentFunding[${status.index}]"/> 
				         	
			    <kul:subtab lookedUpCollectionName="fundingLine" width="${tableWidth}" subTabTitle="${fundingLine.appointmentFundingString}">
			    	<bc:appointmentFundingLineForIncumbent fundingLine="${fundingLine}" fundingLineName="${fundingLineName}"	countOfMajorColumns="9" lineIndex="${status.index}" readOnly="true" hasBeenAdded = "true">    		
						<html:image property="methodToCall.restorePurgedSalarySettingLine.line${status.index}.anchorsalaryexistingLineLineAnchor${status.index}" 
							src="${ConfigProperties.externalizable.images.url}tinybutton-restore.gif" 
							title="Restore the purged Salary Setting Line ${status.index}"
							alt="Restore the purged Salary Setting Line ${status.index}" styleClass="tinybutton" />
					</bc:appointmentFundingLineForIncumbent>	
				</kul:subtab>
			</c:if>
		</c:forEach>
	</div>
	</kul:tab>
</c:if>
