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

import java.util.List;

import org.kualigan.kfs.module.live.businessobject.Source;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
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
    public List<Source> listSources() {
        final String repodir = System.getProperty("user.dir"); 
        final FileRepositoryBuilder builder = new FileRepositoryBuilder();
        final Repository repository = builder.setGitDir(new File(repodir))
              .readEnvironment() 
              .findGitDir() 
              .build();
        final TreeWalk walk = new TreeWalk(repository);
/*
		walk.setRecursive(recursive);
		walk.addTree(tree);
		while (walk.next()) {
			final FileMode mode = walk.getFileMode(0);
			if (mode == FileMode.TREE)
				out.print('0');
			out.print(mode);
			out.print(' ');
			out.print(Constants.typeString(mode.getObjectType()));

			out.print(' ');
			out.print(walk.getObjectId(0).name());

			out.print('\t');
			out.print(walk.getPathString());
			out.println();
		}
*/
        return null;
    }

    public void commit(final Source source) {
    }

    public void push(final Source source) {
    }
}
 