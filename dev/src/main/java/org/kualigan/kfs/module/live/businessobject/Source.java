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
package org.kualigan.kfs.module.live.businessobject;
 
 
/**
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class Source implements java.io.Serializable {

    private String path;
    private String id;
    private String extension;
    private String type;

     /**
      * Absolute path of the source
      * 
      * @return String
      */
     public String getPath() {
         return this.path;
     }
     
     public void setPath(final String path) {
         this.path = path;
     }
     
     /**
      * VCS Identifier
      * 
      * @return String
      */
     public String getId() {
         return this.id;
     }
     
     public void setId(final String id) {
         this.id = id;
     }
     
     /**
      * The source type
      * 
      * @return String
      */
     public String getType() {
         return this.type;
     }
     
     public void setType(final String type) {
        this.type = type;
     }
     /**
      * File extension of the source
      * 
      * @return String
      */
     public String getExtension() {
        return this.extension;
     }
     
     public void setExtension(final String extension) {
         this.extension = extension;
     }
 }