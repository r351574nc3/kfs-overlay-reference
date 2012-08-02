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

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.kualigan.kfs.module.live.businessobject.Source;
import org.kualigan.kfs.module.live.businessobject.SourceBuilder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

import static org.eclipse.jgit.lib.Constants.*;
import static org.kualigan.kfs.logging.SimpleLogger.*;

/**
 * 
 * @author Leo Przybylski (leo [at] rsmart.com)
 */
public class SourceServiceImpl implements org.kualigan.kfs.module.live.service.SourceService {
    
    private SourceBuilderFactory sourceBuilderFactory;

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
        
        final TreeWalk walk = new TreeWalk(repository);
		walk.setRecursive(true);
		walk.addTree(new FileTreeIterator(repository));
        
		while (walk.next()) {
            if (untrackedFiles.contains(walk.getPathString())) {
                continue;
            }
			final FileMode mode = walk.getFileMode(0);
            try {
                retval.add(newSource(walk.getObjectId(0).name(), walk.getPathString()));
            }
            catch (Exception e) {
                warnf(e.getMessage());
            }
		}
        return retval;
    }

    /**
     * 
     * 
     * @return List of {@link Source} instances directly in the current path.
     */
    public List<Source> listSources(final String path) throws Exception {
        final List<Source> retval = new LinkedList<Source>();
        final FileSystem fs = FileSystems.getDefault();

        for (final Path dir : fs.getRootDirectories()) {
            final String dirName = dir.toString();
            retval.add(newSource(getObjectId(dirName).name(), dirName + File.separator));
        }

        return retval;
    }

    /**
     * Lookup the GIT {@link ObjectId} for a given file by its path.
     * 
     * @param path of a file to lookup 
     * @return ObjectId
     */
    protected ObjectId getObjectId(final String path) throws Exception {
        final String repodir = System.getProperty("user.dir"); 
        infof("Creating repository at %s", repodir);
        final FileRepositoryBuilder builder = new FileRepositoryBuilder();
        final Repository repository = builder.setGitDir(new File(repodir + File.separator + ".git"))
              .readEnvironment() 
              .findGitDir() 
              .build();
        final ObjectId lastCommitId = repository.resolve(HEAD);
        final TreeWalk tree = TreeWalk.forPath(repository, path, lastCommitId);
        
        return tree.getObjectId(0);
    }
    
    protected Set<String> getUntrackedFiles(final Repository repo) throws Exception {
        final Git git = new Git(repo);
        
        return git.status().call().getUntracked();
    }

    public void commit(final Source source) {
    }

    public void push(final Source source) {
    }
    
    public Source newSource(final String objectId, final String path) throws Exception {
        final String extension = path.lastIndexOf(".") > -1 ? path.substring(path.lastIndexOf(".") + 1) : "/";
        final SourceBuilder builder = getSourceBuilderFactory().getInstance(extension);
        return builder.newInstance(objectId, path);
    }
    
    public SourceBuilderFactory getSourceBuilderFactory() {
        return this.sourceBuilderFactory;
    }
    
    public void setSourceBuilderFactory(final SourceBuilderFactory sourceBuilderFactory) {
        this.sourceBuilderFactory = sourceBuilderFactory;
    }
}
 