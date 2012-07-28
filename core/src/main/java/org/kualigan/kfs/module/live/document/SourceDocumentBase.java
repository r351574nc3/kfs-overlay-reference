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
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
 
public class SourceDocumentBase extends TransactionalDocumentBase implements SourceDocument {
    @Id
    @GeneratedValue(generator="TEM_PER_DIEM_ID_SEQ")
    @SequenceGenerator(name="TEM_PER_DIEM_ID_SEQ",sequenceName="TEM_PER_DIEM_ID_SEQ", allocationSize=5)
    @Column(name="id",nullable=false)
    private Long id;   
}