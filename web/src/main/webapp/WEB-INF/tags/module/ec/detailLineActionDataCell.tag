<%--
 Copyright 2006-2009 The Kuali Foundation
 
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

<%@ attribute name="action" required="true"
    description="the name of action that could be rendered" %>
<%@ attribute name="imageFileName" required="true"
    description="the graphic representation of the given action" %>              
              
<html:image property="methodToCall.${action}" 
	src="${ConfigProperties.externalizable.images.url}${imageFileName}" 
	title="${action}" 
	alt="${action}" 
	styleClass="tinybutton" />
