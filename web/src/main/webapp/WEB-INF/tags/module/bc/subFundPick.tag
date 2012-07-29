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

<table align="center" cellpadding="0" cellspacing="0"
	class="datatable-100">
	<tr>
		<th class="grid" colspan="6" align="left">
			<br>
			${KualiForm.operatingModeTitle}
			<br>
			<br>
		</th>
	</tr>

	<c:if test="${empty KualiForm.subFundPickList}">
		<tr>
			<th class="grid" colspan="6" align="left">
				<bean:message
					key="${BCConstants.Report.SUB_FUND_LIST_EMPTY_MESSAGE_KEY}" />
			</th>
		</tr>
</table>

<div id="globalbuttons" class="globalbuttons">
	<html:image
		src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_close.gif"
		styleClass="globalbuttons" property="methodToCall.returnToCaller"
		title="close" alt="close" />
</div>
</c:if>

<c:if test="${!empty KualiForm.subFundPickList}">
	<c:set var="subFundAttribute"
		value="${DataDictionary.BudgetConstructionSubFundPick.attributes}" />

	<tr>
		<th class="grid">
			Select
		</th>
		<th class="grid">
			<kul:htmlAttributeLabel
				attributeEntry="${subFundAttribute.subFundGroupCode}"
				useShortLabel="false" />
		</th>
		<th class="grid">
			<kul:htmlAttributeLabel
				attributeEntry="${subFundAttribute['subFundGroup.subFundGroupDescription']}"
				useShortLabel="false" />
		</th>
	</tr>

	<logic:iterate name="KualiForm" id="subFundPick"
		property="subFundPickList" indexId="ctr">
		<html-el:hidden name="KualiForm"
			property="subFundPickList[${ctr}].principalId" />
		<html-el:hidden name="KualiForm"
			property="subFundPickList[${ctr}].versionNumber" />

		<tr align="center">
			<td class="grid" valign="center">
				<html:checkbox property="subFundPickList[${ctr}].reportFlag"
					value="1" />
			</td>
			<td class="grid" valign="center">
				<kul:htmlControlAttribute
					property="subFundPickList[${ctr}].subFundGroupCode"
					attributeEntry="${subFundAttribute.subFundGroupCode}"
					readOnly="true" />
			</td>
			<td class="grid" valign="center">
				<kul:htmlControlAttribute
					property="subFundPickList[${ctr}].subFundGroup.subFundGroupDescription"
					attributeEntry="${subFundAttribute['subFundGroup.subFundGroupDescription']}"
					readOnly="true" />
			</td>
		</tr>
	</logic:iterate>

	</table>

	<div id="globalbuttons" class="globalbuttons">
		<html:image
			src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_selectall.gif"
			property="methodToCall.selectAllSubFunds" title="Select"
			alt="Select All Codes" styleClass="smallbutton" />
		<html:image
			src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_unselall.gif"
			property="methodToCall.unselectAllSubFunds" title="Unselect"
			alt="Unselect All Codes" styleClass="smallbutton" />
		<html:image
			src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif"
			styleClass="globalbuttons" property="methodToCall.performReport"
			title="Perform Report" alt="submit"
			onclick="excludeSubmitRestriction=true" />
		<html:image
			src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_close.gif"
			styleClass="globalbuttons" property="methodToCall.returnToCaller"
			title="close" alt="close" />
	</div>

</c:if>


