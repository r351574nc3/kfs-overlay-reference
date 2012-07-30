<%--
 Copyright 2006-2008 The Kuali Foundation
 
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

<c:set var="readOnly"
	value="${!KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />

 
<kul:documentPage showDocumentInfo="true"
	documentTypeName="LiabilityDecreaseDocument"
	htmlFormAction="endowLiabilityDecreaseDocument" renderMultipart="true" 
	showTabButtons="true">

    <c:if test="${KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}">
        <c:set var="fullEntryMode" value="true" scope="request" />
    </c:if>

	<endow:endowmentDocumentOverview editingMode="${KualiForm.editingMode}" 
	                                 endowDocAttributes="${DataDictionary.LiabilityDecreaseDocument.attributes}" />
	
	<sys:hiddenDocumentFields isFinancialDocument="false" />
     
    <endow:endowmentTransactionalDocumentDetails
         documentAttributes="${DataDictionary.LiabilityDecreaseDocument.attributes}" 
         readOnly="${readOnly}" 
         subTypeReadOnly="false"
         tabTitle="Liability Decrease Details"
         headingTitle="Liability Decrease Details"
         summaryTitle="Liability Decrease Details"
         />

    <endow:endowmentSecurityDetailsSection showSource="true" showTarget="false" showRegistrationCode="true" openTabByDefault="true" showLabels="false"/>  
                  
	<endow:endowmentTransactionLinesSection hasSource="true" hasTarget="false" hasUnits="true" isTransAmntReadOnly="false"/> 
                   
    <endow:endowmentTaxLotLine 
    	documentAttributes="${DataDictionary.EndowmentTransactionTaxLotLine.attributes}" 
    	isSource="true"
    	isTarget="false"
    	displayHoldingCost="true"
    	displayGainLoss="false"
    	showSourceDeleteButton="false"
    	showTargetDeleteButton="false"
    	readOnly="${readOnly}"/>

 
	<kul:notes /> 

	<kul:routeLog />

	<kul:panelFooter />

	<sys:documentControls transactionalDocument="true" extraButtons="${KualiForm.extraButtons}" />

</kul:documentPage>
