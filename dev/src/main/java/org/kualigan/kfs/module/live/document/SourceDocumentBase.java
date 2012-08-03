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
package org.kualigan.kfs.module.live.document;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.kuali.rice.kns.document.TransactionalDocumentBase;

/**
 * 
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class SourceDocumentBase extends TransactionalDocumentBase implements SourceDocument {
    @Id
    @GeneratedValue(generator="SOURCE_DOCUMENT_ID_SEQ")
    @SequenceGenerator(name="SOURCE_DOCUMENT_ID_SEQ",sequenceName="SOURCE_DOCUMENT_ID_SEQ", allocationSize=5)
    @Column(name="id",nullable=false)
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(final Long id) {
        this.id = id;
    }
    
    public String getDefaultParentDocType() {
        return "FP";
    }
}