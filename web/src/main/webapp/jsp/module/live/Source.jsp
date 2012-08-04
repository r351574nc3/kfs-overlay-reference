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
<html>
    <head>
        <meta charset="utf-8">
        <title>Source Document</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <style>
            #editor {
                position: absolute;
                width: 95%;
                height: 480px;
            }
            .bubble {
                background: #EEE;
                padding: 3px;
                background: #EEE;
                border-radius: 3px;
            }
        </style>
        <link href="css/bootstrap.css" rel="stylesheet">
        <style type="text/css">
          body {
            padding-top: 60px;
            padding-bottom: 40px;
          }
        </style>
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
        <script src="scripts/module/live/source.js" type="text/javascript" charset="utf-8"></script>
        <script src="scripts/jquery-1.7.2.min.js"></script>
        <script src="assets/js/google-code-prettify/prettify.js"></script>
        <script src="assets/js/bootstrap-transition.js"></script>
        <script src="assets/js/bootstrap-alert.js"></script>
        <script src="assets/js/bootstrap-modal.js"></script>
        <script src="assets/js/bootstrap-dropdown.js"></script>
        <script src="assets/js/bootstrap-scrollspy.js"></script>
        <script src="assets/js/bootstrap-tab.js"></script>
        <script src="assets/js/bootstrap-tooltip.js"></script>
        <script src="assets/js/bootstrap-popover.js"></script>
        <script src="assets/js/bootstrap-button.js"></script>
        <script src="assets/js/bootstrap-collapse.js"></script>
        <script src="assets/js/bootstrap-carousel.js"></script>
        <script src="assets/js/bootstrap-typeahead.js"></script>
        <script src="assets/js/application.js"></script>
        <script language="JavaScript" type="text/javascript" src="/kfs-tem/dwr/engine.js"></script>
        <script language="JavaScript" type="text/javascript" src="dwr/util.js"></script>
        <script src="dwr/interface/SourceService.js"></script>
    </head>
    <body>
    
        <div class="navbar navbar-fixed-top">
          <div class="navbar-inner">
            <div class="container">
              <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </a>
              <a class="brand" href="#">Source Document</a>
              <div class="nav-collapse">
                <ul class="nav">
                  <li class="active"><a href="#">Home</a></li>
                  <li><a href="#about">About</a></li>
                  <li><a href="#contact">Contact</a></li>
                </ul>
              </div><!--/.nav-collapse -->
            </div>
          </div>
        </div>

          <div class="alert alert-block alert-error fade in">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4 class="alert-heading">Oh snap! You got an error!</h4>
            <p>Change this and that and try again.</p>
            <p>
              <a class="btn btn-danger" href="#">Take this action</a> <a class="btn" href="#">Or do this</a>
            </p>
          </div>

        <div id="slider">
        <ul class="breadcrumb">
            <c:forTokens items="${param.path}" delims="/" varStatus="status" var="path">
            <c:choose>
            <c:when test="${not status.last}">
            <li><a href="#">${path}</a> <span class="divider">/</span></li>
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
<script>
            function listSources(path) {
                SourceService.sources('core', {
                        callback:function(data) {
                            alert(data[0].id);
                        },
                        errorHandler:function(errorMessage) { 
                                window.status = errorMessage;
                                alert(errorMessage);
                        }
                });
            }

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

            $('#slider a').click(function() {
                history.pushState({ path: this.path }, '', this.href)
                $.get('liveSource.do?methodToCall=docHandler&command=initiate&docTypeName=SOURCE&path=core/src/', function(data) {
                    slideTo(data)      
                })
              return false  
            })
</script>

    <script src="scripts/modernizr.custom.48556.js" type="text/javascript"></script>
    
    <script src="scripts/github.js" type="text/javascript"></script>
    </body>
</html>