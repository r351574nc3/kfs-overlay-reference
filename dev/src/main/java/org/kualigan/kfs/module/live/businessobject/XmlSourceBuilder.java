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
 * 
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class XmlSourceBuilder implements SourceBuilder {
    
    /**
     * 
     * @return Source instance for XML files
     * @see org.kualigan.kfs.module.live.businessobject.Source
     * @see org.kualigan.kfs.module.live.businessobject.SourceBuilder
     */
    public Source newInstance(final String objectId, final String path) {
        final Source retval = new Source();
        source.setId(objectId);
        source.setPath(path);
        source.setType("xml");
        source.setExtension("xml");
        return retval;
    }
}