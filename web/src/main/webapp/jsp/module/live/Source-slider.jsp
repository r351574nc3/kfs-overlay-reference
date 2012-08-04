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

        <div id="slider">
        <ul class="breadcrumb">
            <c:forTokens items="${param.path}" delims="/" varStatus="status" var="path">
            <c:choose>
            <c:when test="${not status.last}">
            <li><a href="${fn:substringBefore(param.path, path)}${path}">${path}</a> <span class="divider">/</span></li>
            </c:when>
            <c:otherwise>
            <li class="active">${path}</li>
            </c:otherwise>
            </c:choose>
            </c:forTokens>
        </ul>

        <div class="accordion" id="accordion2">
            <c:forEach items="${KualiForm.sources}" var="sourceItem">
            <div class="accordion-group">
              <div class="accordion-heading">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#${sourceItem.id}">${sourceItem.path}</a>
              </div>
              <div id="${sourceItem.id}" class="accordion-body collapse in">
                <div class="accordion-inner">
                  Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
                </div>
              </div>
            </div>
            </c:forEach>
            <div class="accordion-group">
              <div class="accordion-heading">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">
                  Collapsible Group Item #3
                </a>
              </div>
              <div id="collapseThree" class="accordion-body collapse">
                <div class="accordion-inner">            
                    <div id="outer-editor" class="table-bordered bubble" style="height: 480px; margin: 10 10 10 10">
                        <div id="editor">some text</div>
                        <script src="scripts/ace/ace.js" type="text/javascript" charset="utf-8"></script>
                        <script src="scripts/ace/theme-twilight.js" type="text/javascript" charset="utf-8"></script>
                    
                        <script>
                            var editor = ace.edit("editor");
                            editor.setTheme("ace/theme/twilight");
                        </script>
                    </div>
                </div>
              </div>
            </div>
          </div>
        </div>
