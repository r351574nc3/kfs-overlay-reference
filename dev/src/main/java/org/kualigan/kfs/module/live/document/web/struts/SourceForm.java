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

import java.util.List;
import java.util.Set;

import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

import org.kualigan.kfs.module.live.businessobject.Source;

import org.kualigan.kfs.module.live.document.JavaDocument;
import org.kualigan.kfs.module.live.document.SourceDocument;

import org.kuali.rice.kns.service.DocumentService;

/**
 *
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class SourceForm extends KualiTransactionalDocumentFormBase {
    private static final long serialVersionUID = 1L;
    private Set<Source> sources;
    private String path;

    /**
     * Constructs a TransferOfFundsForm instance and sets up the appropriately casted document.
     */
    public SourceForm() {
        super();
    }
    
    public void setSources(final Set<Source> sources) {
        this.sources = sources;
    }
    
    public Set<Source> getSources() {
        return this.sources;
    }
    
    public void setPath(final String path) {
        this.path = path;
    }
    
    public String getPath() {
        return this.path;
    }

    @Override
    protected String getDefaultDocumentTypeName() {
        return "org.kualigan.kfs.module.live.document.SourceDocumentBase";
    }
    
    protected void instantiateDocument() {
        try {
            final SourceDocument document = (SourceDocument) getDocumentService().getNewDocument(org.kualigan.kfs.module.live.document.SourceDocumentBase.class);
            setDocument((org.kuali.rice.kns.document.Document) document);
        }
        catch (Exception e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
            setDocument(new org.kualigan.kfs.module.live.document.SourceDocumentBase());
        }
    }
    
    /**
     * @return Returns the serviceBillingDocument.
     */
    public SourceDocument getSourceDocument() {
        return (SourceDocument) getDocument();
    }
    
    public DocumentService getDocumentService() {
        return org.kuali.kfs.sys.context.SpringContext.getBean(DocumentService.class);
    }
}
