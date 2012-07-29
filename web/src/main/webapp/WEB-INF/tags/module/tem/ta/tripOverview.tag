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

<c:set var="documentAttributes" value="${DataDictionary.TravelAuthorizationDocument.attributes}" />
<kul:tab tabTitle="Trip Overview" defaultOpen="true" tabErrorKey="${TemKeyConstants.TRVL_AUTH_TRIP_OVERVIEW_ERRORS}">
    <div class="tab-container" align="center">

    <tem:traveler/>
    <tem:tripInformation />
   <jsp:doBody />
    </div>
</kul:tab>
