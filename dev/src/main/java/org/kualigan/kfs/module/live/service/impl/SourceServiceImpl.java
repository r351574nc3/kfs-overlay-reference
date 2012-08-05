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

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.text.DecimalFormat;

import java.util.HashSet;
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
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
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
    private static final String[] tensNames = {
        "",
        " ten",
        " twenty",
        " thirty",
        " forty",
        " fifty",
        " sixty",
        " seventy",
        " eighty",
        " ninety"
    };
    
    private static final String[] numNames = {
        "",
        " one",
        " two",
        " three",
        " four",
        " five",
        " six",
        " seven",
        " eight",
        " nine",
        " ten",
        " eleven",
        " twelve",
        " thirteen",
        " fourteen",
        " fifteen",
        " sixteen",
        " seventeen",
        " eighteen",
        " nineteen"
    };

    private SourceBuilderFactory sourceBuilderFactory;
    private List<Source> sources;
    private List<String> sourcePaths;
    
    protected String convertLessThanOneThousand(int number) {
        String soFar;
        
        if (number % 100 < 20){
            soFar = numNames[number % 100];
            number /= 100;
        }
        else {
            soFar = numNames[number % 10];
            number /= 10;
            
            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }
    
    
    public String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) { return "zero"; }
        
        String snumber = Long.toString(number);
        
        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);
        
        // XXXnnnnnnnnn 
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6)); 
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6,9)); 
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));    
        
        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1 :
                tradBillions = convertLessThanOneThousand(billions) 
                + " billion ";
                break;
            default :
                tradBillions = convertLessThanOneThousand(billions) 
                + " billion ";
        }
        String result =  tradBillions;
        
        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1 :
                tradMillions = convertLessThanOneThousand(millions) 
                + " million ";
                break;
            default :
                tradMillions = convertLessThanOneThousand(millions) 
                + " million ";
        }
        result =  result + tradMillions;
        
        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1 :
                tradHundredThousands = "one thousand ";
                break;
            default :
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) 
                + " thousand ";
        }
        result =  result + tradHundredThousands;
        
        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result =  result + tradThousand;
        
        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    /**
     * @see org.kualigan.kfs.module.live.businessobject.Source;
     * @see org.kualigan.kfs.module.live.service.SourceService;
     */
    public List<Source> listSources() throws Exception {
        if (sources != null) {
            return sources;
        }
        
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
        sources = retval;
        
        return sources;
    }
    
    protected List<String> listSourcePaths() throws Exception {
        if (sourcePaths != null) {
            return sourcePaths;
        }
        
        final List<String> retval = new LinkedList<String>();
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
                retval.add(walk.getPathString());
            }
            catch (Exception e) {
                warnf(e.getMessage());
            }
		}
        sourcePaths = retval;
        return sourcePaths;
    }

    /**
     * 
     * 
     * @return {@link Set} of {@link Source} instances directly in the current path.
     */
    public Set<Source> sources(final String pathStr) throws Exception {
        final List<String> paths  = listSourcePaths();
        final Set<Source> retval = new HashSet<Source>();
        infof("Listing sources for %s", pathStr);
        
        for (final String p : paths) {
            if (p.startsWith(pathStr)) {
                final int offset = pathStr.endsWith(File.separator) ? 0 : 1;
                final String relative = p.substring(pathStr.length() + offset);
                if (relative.indexOf(File.separator) == -1 
                    && relative.indexOf(".") > -1) { // non-directory
                    final ObjectId id = getObjectId(relative);
                    if (id != null) { // probably is a dir otherwise
                        retval.add(newSource(id.name(), relative));
                    }
                }
                else {
                    final String dirName = relative.substring(0, relative.indexOf(File.separator));
                    infof("Adding dir %s", dirName);
                    retval.add(newSource("directory" + convert(retval.size()), dirName + File.separator));
                }
            }
            else if (pathStr.equals("") || pathStr.equals(".")) {
                if (p.indexOf(File.separator) > -1) {
                    final String dirName = p.substring(0, p.indexOf(File.separator)) + File.separator;
                    infof("Adding dir %s", dirName);
                    retval.add(newSource("directory" + convert(retval.size()), dirName));
                }
            }
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
        final FileRepositoryBuilder builder = new FileRepositoryBuilder();
        final Repository repository = builder.setGitDir(new File(repodir + File.separator + ".git"))
              .readEnvironment() 
              .findGitDir() 
              .build();
        final ObjectId lastCommitId = repository.resolve(HEAD);
        
        final RevTree tree = new RevWalk(repository).parseCommit(lastCommitId).getTree();

        infof("Getting object id for %s", path);
        return TreeWalk.forPath(repository, path, tree) == null ? null : TreeWalk.forPath(repository, path, tree).getObjectId(0);
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
 