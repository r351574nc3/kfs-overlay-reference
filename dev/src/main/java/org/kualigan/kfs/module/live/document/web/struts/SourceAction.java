/*
 * Copyright 2005-2006 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kualigan.kfs.module.live.document.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.kuali.kfs.sys.context.SpringContext;

import org.kualigan.kfs.module.live.service.SourceService;

import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

import static org.kualigan.kfs.logging.SimpleLogger.*;


/**
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class SourceAction extends  KualiTransactionalDocumentActionBase {
    
    /**
     * method used for doc handler actions. Typically assumes that this is the entry point for the 
     * document when it is first created. A number of things are done hear assuming the document
     * is created at this point.
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final ActionForward retval = super.docHandler(mapping, form, request, response);
                
        final SourceForm sf = (SourceForm) form;
        
        final String path = request.getAttribute("path") != null ? (String) request.getAttribute("path") : "/";

        sf.setSources(getSourceService().listSources(path));

        
        return retval;
    }
    
    public SourceService getSourceService() {
        return SpringContext.getBean(SourceService.class);
    }
}
