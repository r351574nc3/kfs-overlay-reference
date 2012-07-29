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
<c:set var="personAttributes" value="${DataDictionary.PersonImpl.attributes}" />
<c:set var="documentAttributes" value="${DataDictionary.TravelEntertainmentDocument.attributes}" />
<c:set var="travelerAttributes" value="${DataDictionary.TravelerDetail.attributes}" />
<c:set var="tabindexOverrideBase" value="8" />

<h3>Requester (Person to be reimbursed)</h3>

<table cellpadding="0" cellspacing="0" class="datatable">
	<tr>
		<th>
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${documentAttributes.eventTitle}" readOnly="true" />
			</div>
		</th>
		<td class="datacell" colspan="3">
			<kul:htmlControlAttribute attributeEntry="${documentAttributes.eventTitle}" property="document.eventTitle" readOnly="${!fullEntryMode}" />
		</td>
	</tr>
	<tr>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${documentAttributes.hostName}" />
			</div>
		</th>

		<td class="datacell" colspan="3">
			<div align="left">
				<kul:htmlControlAttribute attributeEntry="${documentAttributes.hostName}" property="document.hostName" readOnly="true" />
				<c:if test="${fullEntryMode}">
					<!--<kul:lookup boClassName="org.kuali.kfs.module.tem.businessobject.TEMProfile" lookupParameters="document.host.travelerTypeCode:travelerTypeCode"
						fieldConversions="travelerTypeCode:document.host.travelerTypeCode,principalId:document.host.principalId,firstName:document.host.firstName,middleName:document.host.middleName,lastName:document.host.lastName" />
				-->
				<kul:lookup boClassName="org.kuali.kfs.module.tem.businessobject.TEMProfile" lookupParameters="document.host.travelerTypeCode:travelerTypeCode"
						fieldConversions="profileId:document.hostProfileId" />
				
				</c:if>

			</div>
		</td>
	</tr>
	<c:if test="${fullEntryMode}">
		<tr>
			<th class="bord-l-b"><div align="right">Requester Lookup:</div>
			</th>
			<td class="datacell" colspan="3">
				<div align="left">
					&nbsp;
					<kul:lookup boClassName="org.kuali.kfs.module.tem.businessobject.TEMProfile"
						lookupParameters="document.traveler.travelerTypeCode:travelerTypeCode" />
				</div>
			</td>
		</tr>
	</c:if>
	<tr>
		<th>
			<div align="right">Requester Type Code:</div>
		</th>

		<td class="datacell" colspan="3">
			<div align="left">
				<kul:htmlControlAttribute attributeEntry="${travelerAttributes.travelerType.name}" property="document.traveler.travelerType.name" readOnly="true" />
			</div>
		</td>
	</tr>

	<c:if test="${KualiForm.document.traveler.travelerTypeCode == 'EMP'}">
		<tr>
			<th class="bord-l-b">
				<div align="right">
					<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.principalId}" />
				</div>
			</th>
			<td class="datacell">
				<kul:htmlControlAttribute attributeEntry="${travelerAttributes.principalId}" property="document.traveler.principalId" readOnly="true" />
			</td>
			<th>
				<div align="right">
					<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.principalName}" readOnly="true" />
				</div>
			</th>
			<td class="datacell">
				<kul:htmlControlAttribute attributeEntry="${travelerAttributes.principalName}" property="document.traveler.principalName" readOnly="true" />
			</td>
		</tr>
	</c:if>
	<tr>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.firstName}" />
			</div>
		</th>
		<td class="datacell">
			<kul:htmlControlAttribute attributeEntry="${travelerAttributes.firstName}" property="document.traveler.firstName" readOnly="true" />
		</td>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.lastName}" />
			</div>
		</th>
		<td class="datacell">
			<kul:htmlControlAttribute attributeEntry="${travelerAttributes.lastName}" property="document.traveler.lastName" readOnly="true" />
		</td>
	</tr>
	<c:if test="${fullEntryMode}">
		<tr>
			<th class="bord-l-b"><div align="right">Address Lookup:</div>
			</th>
			<td class="datacell" colspan="3">
            	<div align="left">&nbsp;
			    	<kul:lookup boClassName="org.kuali.kfs.module.tem.businessobject.TemProfileAddress" lookupParameters="document.traveler.principalId:principalId,document.traveler.customerNumber:customerNumber"
			    	fieldConversions="streetAddressLine1:document.traveler.streetAddressLine1,streetAddressLine2:document.traveler.streetAddressLine2,zipCode:document.traveler.zipCode,countryCode:document.traveler.countryCode,stateCode:document.traveler.stateCode,cityName:document.traveler.cityName" />
				</div>
			</td>
		</tr>
	</c:if>
	<tr>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.streetAddressLine1}" />
			</div>
		</th>
		<td class="datacell">
			<kul:htmlControlAttribute attributeEntry="${travelerAttributes.streetAddressLine1}" property="document.traveler.streetAddressLine1" readOnly="true" />
		</td>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.streetAddressLine2}" />
			</div>
		</th>
		<td class="datacell">
			<kul:htmlControlAttribute attributeEntry="${travelerAttributes.streetAddressLine2}" property="document.traveler.streetAddressLine2" readOnly="true" />
		</td>
	</tr>
	<tr>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.cityName}" />
			</div>
		</th>
		<td class="datacell">
			<kul:htmlControlAttribute attributeEntry="${travelerAttributes.cityName}" property="document.traveler.cityName" readOnly="true" />
		</td>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.stateCode}" />
			</div>
		</th>
		<td class="datacell">
		<kul:htmlControlAttribute attributeEntry="${travelerAttributes.stateCode}" property="document.traveler.stateCode" readOnly="true"
				tabindexOverride="${tabindexOverrideBase + 5}" />
		</td>
	</tr>
	<tr>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.countryCode}" />
			</div>
		</th>
		<td class="datacell">
		<kul:htmlControlAttribute attributeEntry="${travelerAttributes.countryCode}" property="document.traveler.countryCode" readOnly="true"
				tabindexOverride="${tabindexOverrideBase + 4}" /></td>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.zipCode}" />
			</div></th>
		<td class="datacell"><kul:htmlControlAttribute
				attributeEntry="${travelerAttributes.zipCode}"
				property="document.traveler.zipCode" readOnly="true" /> 
		</td>
	</tr>
	<tr>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.emailAddress}" />
			</div>
		</th>
		<td class="datacell"><kul:htmlControlAttribute attributeEntry="${travelerAttributes.emailAddress}" property="document.traveler.emailAddress" readOnly="true" /></td>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.phoneNumber}" />
			</div></th>
		<td class="datacell">
			<kul:htmlControlAttribute attributeEntry="${travelerAttributes.phoneNumber}" property="document.traveler.phoneNumber" readOnly="true" />
		</td>
	</tr>
	<tr>
		<th class="bord-l-b">
			<div align="right">
				<kul:htmlAttributeLabel attributeEntry="${travelerAttributes.liabilityInsurance}" />
			</div>
		</th>
		<td class="datacell" colspan="3">
			<div align="left">
				<kul:htmlControlAttribute attributeEntry="${travelerAttributes.liabilityInsurance}" property="document.traveler.liabilityInsurance" readOnly="${!fullEntryMode}" />
			</div>
		</td>
	</tr>
</table>
