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

import java.io.File;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.kualigan.kfs.module.live.businessobject.Source;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

import static org.kualigan.kfs.logging.SimpleLogger.*;

/**
 * 
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class SourceServiceImpl implements org.kualigan.kfs.module.live.service.SourceService {

    /**
     * @see org.kualigan.kfs.module.live.businessobject.Source;
     * @see org.kualigan.kfs.module.live.service.SourceService;
     */
    public List<Source> listSources() throws Exception {        
        final List<Source> retval = new LinkedList<Source>();
        final String repodir = System.getProperty("user.dir"); 
        infof("Creating repository at %s", repodir);
        final FileRepositoryBuilder builder = new FileRepositoryBuilder();
        final Repository repository = builder.setGitDir(new File(repodir + File.separator + ".git"))
              .readEnvironment() 
              .findGitDir() 
              .build();
        final Set<String> untrackedFiles = getUntrackedFiles(repository);
        
        infof("Got untracked files %s", untrackedFiles);
        final TreeWalk walk = new TreeWalk(repository);
		walk.setRecursive(true);
		walk.addTree(new FileTreeIterator(repository));
        
		while (walk.next()) {
            if (untrackedFiles.contains(walk.getPathString())) {
                continue;
            }
			final FileMode mode = walk.getFileMode(0);
            retval.add(newSource(walk.getObjectId(0).name(), walk.getPathString()));
		}
        return retval;
    }
    
    protected Set<String> getUntrackedFiles(final Repository repo) throws Exception {
        final Git git = new Git(repo);
        
        return git.status().call().getUntracked();
    }

    public void commit(final Source source) {
    }

    public void push(final Source source) {
    }
    
    public Source newSource(final String objectId, final String path) {
        final SourceBuilder builder = getSourceBuilderFactory()
            .getInstance(path.substring(path.getLastIndexOf(".") + 1));
        return builder.newInstance(objectId, path);
    }
    
    public SourceBuilderFactory getSourceBuilderFactory() {
        return this.sourceBuilderFactory;
    }
    
    public void setSourceBuilderFactory(final SourceBuilderFactory sourceBuilderFactory) {
        this.sourceBuilderFactory = sourceBuilderFactory;
    }
}
 