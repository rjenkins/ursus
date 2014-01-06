/**

 Copyright 2013 Ray Jenkins ray@memoization.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

package com.aceevo.ursus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.*;

/**
 * Factory for parsing yaml and generating a UrsusApplicationConfiguration
 *
 * @param <T> our type of UrsusApplicationConfiguration
 */
public class UrsusConfigurationFactory<T extends UrsusApplicationConfiguration> {

    private final String configurationFile;
    private final Class<T> clazz;
    private YAMLFactory yamlFactory = new YAMLFactory();

    public UrsusConfigurationFactory(String configurationFile, Class<T> clazz) {
        this.configurationFile = configurationFile;
        this.clazz = clazz;
    }

    public T getConfiguration() {

        ObjectMapper mapper = new ObjectMapper(yamlFactory);
        try {

            T ursusHttpServerConfig =
                    mapper.readValue(open(configurationFile), clazz);
            return ursusHttpServerConfig;
        } catch (IOException e) {
            throw new RuntimeException("Error parsing: " + configurationFile, e);
        }
    }

    private InputStream open(String configurationFile) throws IOException {
        final File file = new File(configurationFile);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file + " not found");
        }

        return new FileInputStream(file);
    }
}
