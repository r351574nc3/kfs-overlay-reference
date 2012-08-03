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
package org.kualigan.kfs.module.live.service.impl;

import java.util.Map;

import org.kualigan.kfs.module.live.businessobject.SourceBuilder;

/**
 * 
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class SourceBuilderFactory {
    
    private Map<String,SourceBuilder> builders;
    
    public SourceBuilder getInstance(final String extension) throws Exception {
        if (!builders.containsKey(extension)) {
            throw new Exception("SourceBuilder Not Found: Could not find source builder for " + extension);
        }
        return builders.get(extension);
    }
    
    public void setBuilders(final Map<String, SourceBuilder> builders) {
        this.builders = builders;
    }
    
    public Map<String, SourceBuilder> getBuilders() {
        return this.builders;
    }
}
