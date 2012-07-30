<%--
 Copyright 2007-2008 The Kuali Foundation
 
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

<script language="javascript" src="dwr/interface/TravelDocumentService.js"></script> 
<script language="javascript" src="dwr/interface/TravelReimbursementService.js"></script>
<script language="javascript" src="scripts/module/tem/common.js"></script>

<c:set var="fullEntryMode" value="${KualiForm.editingMode['fullEntry']}" scope="request" />

<kul:documentPage showDocumentInfo="true"
    documentTypeName="TravelReimbursementDocument"
    htmlFormAction="temTravelReimbursement" renderMultipart="true"
    showTabButtons="true">
       
    <sys:documentOverview editingMode="${KualiForm.editingMode}" />

    <c:if test="${showReports}">
        <tem-tr:reports />
    </c:if>
    <tem-tr:tripOverview />
    <tem-tr:travelAdvances />

    <!-- Expenses related -->
    <c:if test="${not empty KualiForm.document.primaryDestinationName}"> 
		<tem:perDiemExpenses />
    </c:if>
    <tem:expenses /> 
    <tem-tr:expenseTotals />

	<tem:specialCircumstances />
	<tem:groupTravel />

    <tem-tr:history />
    
	<tem:contactInfo/>    
	<tem:travelerCertification/>

    <tem:summaryByObjectCode />
    
    <tem:assignAccounts />
    <tem:accountingLines />
    <gl:generalLedgerPendingEntries />
    <tem:relatedDocuments />

	<kul:notes
	notesBo="${KualiForm.document.documentBusinessObject.boNotes}"
	noteType="${Constants.NoteTypeEnum.BUSINESS_OBJECT_NOTE_TYPE}"
	attachmentTypesValuesFinderClass="${DataDictionary.TravelReimbursementDocument.attachmentTypesValuesFinderClass}" />  
    
    <kul:adHocRecipients />

    <kul:routeLog />

    <kul:panelFooter />
    
    <sys:documentControls
    transactionalDocument="${documentEntry.transactionalDocument}"
    extraButtons="${KualiForm.extraButtons}" />

      
</kul:documentPage>
