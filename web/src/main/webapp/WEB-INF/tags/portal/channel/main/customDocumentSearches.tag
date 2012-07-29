<%--
 Copyright 2007 The Kuali Foundation
 
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

<channel:portalChannelTop channelTitle="Custom Document Searches" />
<div class="body">
	<portal:portalLink displayTitle="true" title="Financial Transactions" url="${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=KFST" /><br /><br />
	<c:if test="${ConfigProperties.module.accounts.receivable.enabled == 'true'}">
	 	<strong>Accounts Receivable</strong><br/>
	    <ul class="chan">
	        <li><portal:portalLink displayTitle="true" title='Customer Invoices' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=INV'/></li>
	        <li><portal:portalLink displayTitle="true" title='Customer Credit Memos' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CRM'/></li>
	        <li><portal:portalLink displayTitle="true" title='Customer Invoice Writeoffs' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=INVW'/></li>
	        <li><portal:portalLink displayTitle="true" title='Cash Controls' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CTRL'/></li>
	        <li><portal:portalLink displayTitle="true" title='Payment Applications' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=APP'/></li>
	    </ul>
	</c:if>
	<c:if test="${ConfigProperties.module.capital.asset.enabled == 'true'}">
	    <strong>Capital Asset Management</strong><br/>
	    <ul class="chan">
			<li><portal:portalLink displayTitle="true" title='Asset Maintenance' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CAMM'/></li>
		</ul>
	</c:if>
	<c:if test="${ConfigProperties['module.contracts.and.grants.enabled'] == 'true'} && 
		${ConfigProperties['module.external.kuali.coeus.enabled'] == 'false'}">
		<strong>Contracts & Grants</strong><br/>
	   	 <ul class="chan">
			<li><portal:portalLink displayTitle="true" title='Proposals' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=PRPL'/></li>
		</ul>
	</c:if>
	<c:if test="${ConfigProperties.module.labor.distribution.enabled == 'true'}">
	 	<strong>Effort Certification</strong><br/>
	    <ul class="chan">
	        <li><portal:portalLink displayTitle="true" title='Effort Certification' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=ECD'/></li>
	    </ul>
	</c:if>    
	<strong>Financial Processing</strong><br/>
    <ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Disbursement Vouchers" url="${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=DV" /></li>
	</ul>
	<c:if test="${ConfigProperties.module.purchasing.enabled == 'true'}">
		<strong>Purchasing/Accounts Payable</strong><br/>
	    <ul class="chan">
	        <li><portal:portalLink displayTitle="true" title='Electronic Invoice Rejects' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=EIRT'/></li>
	        <li><portal:portalLink displayTitle="true" title='Payment Requests' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=PREQ'/></li>
	        <li><portal:portalLink displayTitle="true" title='Purchase Orders' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=PO'/></li>
	        <li><portal:portalLink displayTitle="true" title='Receiving' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=RCV'/></li>
	        <li><portal:portalLink displayTitle="true" title='Requisitions' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=REQS'/></li>
	        <li><portal:portalLink displayTitle="true" title='Vendor Credit Memos' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CM'/></li>
	     </ul>
	</c:if>
    <c:if test="${ConfigProperties.module.travel.enabled == 'true'}">
	    <strong>Travel/Entertainment</strong><br/>
        <ul class="chan">
            <li><portal:portalLink displayTitle="true" title='Entertainment Reimbursement' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=ENT&methodToCall=search'/></li>
            <li><portal:portalLink displayTitle="true" title='Moving and Relocation Reimbursement' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=RELO&methodToCall=search'/></li>            
            <li><portal:portalLink displayTitle="true" title='Travel Authorization' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=TA&statusCodeForMultiboxSearching=OPEN&methodToCall=search'/></li>
            <li><portal:portalLink displayTitle="true" title='Travel Reimbursement' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=TR&methodToCall=search'/></li>
        </ul>
    </c:if>
    </div>
<channel:portalChannelBottom />
