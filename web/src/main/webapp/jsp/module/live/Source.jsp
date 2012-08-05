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
            .editor {
                position: absolute;
                width: 90%;
                height: 480px;
            }
            .bubble {
                position: relative;
                float: left;
                background: #EEE;
                padding: 3px;
                margin-left: -60px;
                #background: #EEE;
                border-radius: 3px;
            }
        .accordion-group {
            background: white;
        }

        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }

        .tree-browser-wrapper div.th   {
            background-color: #FBFBFB;
            background-image: -moz-linear-gradient(center top , #FFFFFF, #F5F5F5);
            background-repeat: repeat-x;
            border: 1px solid #DDDDDD;
            border-radius: 3px 3px 3px 3px;
            box-shadow: 0 1px 0 #FFFFFF inset;
            list-style: none outside none;
            margin: 0 0 0px;
            padding: 7px 14px;        
        }
        .span1.icon {
            padding : 10 0 0 10;
        }
    
        li.globalbuttons {
            padding-top : 10px;
            padding-bottom : 10px;
        }

        </style>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
        <script src="scripts/module/live/source.js" type="text/javascript" charset="utf-8"></script>
        <script src="scripts/jquery-1.7.2.min.js"></script>
        <script src="assets/js/google-code-prettify/prettify.js"></script>
        <script src="assets/js/bootstrap-transition.js"></script>
        <script src="assets/js/bootstrap-alert.js"></script>
        <script src="assets/js/bootstrap-modal.js"></script>
        <script src="assets/js/bootstrap-dropdown.js"></script>
        <script src="assets/js/bootstrap-tab.js"></script>
        <script src="assets/js/bootstrap-tooltip.js"></script>
        <script src="assets/js/bootstrap-popover.js"></script>
        <script src="assets/js/bootstrap-button.js"></script>
        <script src="assets/js/bootstrap-collapse.js"></script>
        <script src="scripts/ace/ace.js" type="text/javascript" charset="utf-8"></script>
        <script src="scripts/ace/theme-twilight.js" type="text/javascript" charset="utf-8"></script>
        <script language="JavaScript" type="text/javascript" src="/kfs-tem/dwr/engine.js"></script>
        <script language="JavaScript" type="text/javascript" src="dwr/util.js"></script>
        <script src="dwr/interface/SourceService.js"></script>
    </head>
    <body>
    
        <div class="navbar navbar-fixed-top">
          <div class="navbar-inner">
            <div class="container">
              <a class="btn btn-navbar" data-toggle="collapse" data-target="#nav-collapse">
                <span class="icon-bar">test</span>
                <span class="icon-bar">test1</span>
                <span class="icon-bar">test2</span>
              </a>
              <span class="brand" href="#">Source Document</span>
              <div class="nav-collapse" id="nav-collapse">
                <ul class="nav">
                  <li class="globalbuttons"><html:submit styleClass="globalbuttons" 
                                property="methodToCall.submit" 
                                title="submit" alt="submit" 
                                onclick="resetScrollPosition();" 
                                tabindex="${tabindex}" /></li>
                  <li class="globalbuttons"><html:submit styleClass="globalbuttons" 
                                property="methodToCall.save" 
                                title="save" alt="save" 
                                onclick="resetScrollPosition();" 
                                tabindex="${tabindex}" value="Save" /></li>
                  <li class="globalbuttons"><html:submit styleClass="globalbuttons" 
                                property="methodToCall.cancel" 
                                title="cancel" alt="cancel" 
                                onclick="resetScrollPosition();" 
                                tabindex="${tabindex}" value="Cancel" /></li>
                  <li><a href="#about">About</a></li>
                  <li><a href="#contact">Contact</a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>

<%--
          <div class="alert alert-block alert-error fade in">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4 class="alert-heading">Oh snap! You got an error!</h4>
            <p>Change this and that and try again.</p>
            <p>
              <a class="btn btn-danger" href="#">Take this action</a> <a class="btn" href="#">Or do this</a>
            </p>
          </div>
--%>

    <c:import url="Source-slider.jsp" />

    </body>
</html>