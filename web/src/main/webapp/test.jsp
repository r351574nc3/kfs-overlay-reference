<%--
 Copyright 2005-2008 The Kuali Foundation
 
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
<%@ taglib uri="/kr/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/kr/WEB-INF/tlds/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/kr/WEB-INF/tlds/fn.tld" prefix="fn"%>
<%@ taglib uri="/kr/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/kr/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/kr/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/kr/WEB-INF/tlds/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/kr/WEB-INF/tlds/displaytag.tld" prefix="display"%>
<%@ taglib uri="/kr/WEB-INF/tlds/struts-bean-el.tld" prefix="bean-el"%>
<%@ taglib uri="/kr/WEB-INF/tlds/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/kr/WEB-INF/tlds/struts-logic-el.tld" prefix="logic-el"%>
<%@ taglib uri="/kr/WEB-INF/tlds/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/kr/WEB-INF/tlds/kuali-func.tld" prefix="kfunc"%>
<%@ taglib tagdir="/WEB-INF/tags/kr" prefix="kul"%>
<%@ taglib tagdir="/WEB-INF/tags/kim" prefix="kim"%>
<%@ taglib tagdir="/WEB-INF/tags/kew" prefix="kew"%>
<%@ taglib tagdir="/WEB-INF/tags/kr/dd" prefix="dd"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="./assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
    <link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">Project name</a>
          <div class="btn-group pull-right">
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="icon-user"></i> Username
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="#">Profile</a></li>
              <li class="divider"></li>
              <li><a href="#">Sign Out</a></li>
            </ul>
          </div>
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

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span2">
        </div><!--/span-->
        <div class="span9">
          <div class="hero-unit">
            <h1>Hello, world!</h1>
            <p>This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
            <p><a class="btn btn-primary btn-large">Learn more &raquo;</a></p>
          </div>
          <div class="row-fluid">
          </div><!--/row-->
        </div><!--/span-->
      </div><!--/row-->
        <div class="alert">
          <button class="close" data-dismiss="alert">×</button>
          <strong>Warning!</strong> Best check yo self, you're not looking too good.
        </div>
      <hr>

      <footer>
        <p>&copy; Company 2012</p>
      </footer>

    </div><!--/.fluid-container-->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./assets/js/jquery.js"></script>
    <script src="./assets/js/bootstrap-transition.js"></script>
    <script src="./assets/js/bootstrap-alert.js"></script>
    <script src="./assets/js/bootstrap-modal.js"></script>
    <script src="./assets/js/bootstrap-dropdown.js"></script>
    <script src="./assets/js/bootstrap-scrollspy.js"></script>
    <script src="./assets/js/bootstrap-tab.js"></script>
    <script src="./assets/js/bootstrap-tooltip.js"></script>
    <script src="./assets/js/bootstrap-popover.js"></script>
    <script src="./assets/js/bootstrap-button.js"></script>
    <script src="./assets/js/bootstrap-collapse.js"></script>
    <script src="./assets/js/bootstrap-carousel.js"></script>
    <script src="./assets/js/bootstrap-typeahead.js"></script>

    <script>
function createAutoClosingAlert(selector, delay) {
   var alert = $(selector).alert();
    alert("Got " + alert);
   window.setTimeout(function() { alert.alert('close') }, delay);
}

createAutoClosingAlert(".alert", 2000);
    alert("Got here");
    </script>
  </body>
</html>
