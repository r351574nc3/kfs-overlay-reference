<%--
 Copyright 2005-2006 The Kuali Foundation
 
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
<style>
    #editor {
        position: absolute;
        width: 90%;
        height: 480px;
    }
</style>

<kul:documentPage showDocumentInfo="true"
	documentTypeName="org.kualigan.kfs.module.live.document.SourceDocumentBase"
	htmlFormAction="liveSource" renderMultipart="true"
	showTabButtons="true">

<sys:documentOverview editingMode="${KualiForm.editingMode}" />
<kul:tab tabTitle="Files" defaultOpen="true" tabErrorKey="${Constants.DOCUMENT_NOTES_ERRORS},attachmentFile"
                 transparentBackground="${transparentBackground}" >
  <div id="tab-Files-container" class="tab-container">
  </div>
</kul:tab>

<kul:tab tabTitle="Source" defaultOpen="true" tabErrorKey="${Constants.DOCUMENT_NOTES_ERRORS},attachmentFile"
                 transparentBackground="${transparentBackground}" >
<div id="outer-editor" style="height: 480px">
    <div id="editor">some text</div>
    <script src="scripts/ace/ace.js" type="text/javascript" charset="utf-8"></script>
    <script src="scripts/ace/theme-twilight.js" type="text/javascript" charset="utf-8"></script>

    <script>
        var editor = ace.edit("editor");
        editor.setTheme("ace/theme/twilight");
    </script>
</div>
</kul:tab>
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<sys:documentControls transactionalDocument="true" extraButtons="${KualiForm.extraButtons}" />

</kul:documentPage>
