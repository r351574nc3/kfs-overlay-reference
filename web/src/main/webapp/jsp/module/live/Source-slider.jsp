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
    <div id="slider row">
        <div class="span10 offset1">
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
        </div>

  <div class="frames">
    <div class="frame frame-center" data-path="/" data-permalink-url="/ajaxorg/ace/tree/2956f1b61f7e9801ac604fb20242111260bf261c" data-title="ajaxorg/ace · GitHub" data-type="tree" data-cached-commit-url="/ajaxorg/ace/cache/commits/2956f1b61f7e9801ac604fb20242111260bf261c?commit_sha=2956f1b61f7e9801ac604fb20242111260bf261c&amp;path=">
      <div class="bubble tree-browser-wrapper span10 offset1">
        <div class="accordion" id="accordion">
        <div class="th row">
          <div class="span2 offset1" >name</div>
          <div class="span2">age</div>
          <div class="span3"><!--
            <div class="history">
              <a href="/ajaxorg/ace/commits/master/" rel="nofollow">history</a>
            </div>-->
            message
          </div>
        </div>
        <c:forEach items="${KualiForm.sources}" var="sourceItem">
        <div class="accordion-group">
          <div class="row">
                <div class="span1 icon"><i class="icon-folder-close"></i></div>
                <div class="span2 content accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" 
                                                data-parent="#accordion" 
                                                href="${sourceItem.path}">${sourceItem.path}</a></div>
                <div class="span2 age">  </div>
                <div class="span3 message">  </div>
          </div>
          <div class="row">
            <div id="${sourceItem.id}" class="span10 accordion-body collapse in">
                <div class="accordion-inner" style="height: 480px">
                    <div class="span10 table-bordered" style="height: 100%" id="editor">some text</div>
                        <script>
                            var editor = ace.edit("editor");
                            editor.setTheme("ace/theme/twilight");
                        </script>
                    </div>
                </div>
            </div>
          </div>
        </div>
        </c:forEach>

        </div>
      </div>
    </div>
  </div>
<script>

function slideTo(data) {
    var width = parseInt($('#slider').css('width'));
    var transfer = $('<div class="transfer"></div>').css({ 'width': (2 * width) + 'px' });
    var current = $('<div class="current"></div>').css({ 'width': width + 'px', 'left': '0', 'float': 'left' }).html($('#slider').html());
    var next = $('<div class="next"></div>').css({ 'width': width + 'px', 'left': width + 'px', 'float': 'left' }).html(data);
    transfer.append(current).append(next);
    $('#slider').html('').append(transfer);
    transfer.animate({ 'margin-left': '-' + width + 'px' }, 300, function () {
        $('#slider').html(data);
    });
}

$('#slider a.js-slide-to').click(function() {
    var current = window.location + "";
    var newurl =  current + $(this).attr("href");

    if ($(this).attr("href").indexOf('/') != $(this).attr("href").length - 1) {
        newurl = current.substr(0, current.indexOf($(this).attr("href")) + $(this).attr("href").length + 1);
    }

    history.pushState({ path:  this.path }, '', newurl)
    var sliderurl = newurl;

    if (sliderurl.indexOf('slider') < 0) {
        sliderurl = newurl.split('&path')[0] + '&slider=' + '&path' + newurl.split('&path')[1];
    }

    $.get(sliderurl, function(data) {
        slideTo(data)      
    })
  return false  
})
</script>
