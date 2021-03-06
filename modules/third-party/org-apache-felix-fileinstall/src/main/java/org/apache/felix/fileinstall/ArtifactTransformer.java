/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.felix.fileinstall;

import java.io.File;

/**
 * Objects implementing this interface are able to convert certain
 * kind of artifacts to OSGi bundles.
 */
public interface ArtifactTransformer extends ArtifactListener {

	/**
	 * Process the given file (canHandle returned true previously)
	 * Can return <null> or a pointer to a transformed file.
	 */
	public File transform(File artifact, File tmpDir) throws Exception;

}
/* @generated */