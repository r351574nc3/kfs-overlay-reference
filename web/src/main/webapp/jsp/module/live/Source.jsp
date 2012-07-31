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

        <ul class="breadcrumb">
          <li>
            <a href="#">Home</a> <span class="divider">/</span>
          </li>
          <li>
            <a href="#">Library</a> <span class="divider">/</span>
          </li>
          <li class="active">Data</li>
        </ul>

        <div id="outer-editor" class="table-bordered bubble" style="height: 480px; margin: 10 10 10 10">
            <div id="editor">some text</div>
            <script src="scripts/ace/ace.js" type="text/javascript" charset="utf-8"></script>
            <script src="scripts/ace/theme-twilight.js" type="text/javascript" charset="utf-8"></script>
        
            <script>
                var editor = ace.edit("editor");
                editor.setTheme("ace/theme/twilight");
            </script>
        </div>
    </body>
</html>