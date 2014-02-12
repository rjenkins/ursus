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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.*;
import java.util.Set;

/**
 * Factory for parsing yaml and generating a UrsusJerseyApplicationConfiguration
 *
 * @param <T> our type of UrsusJerseyApplicationConfiguration
 */
public class UrsusConfigurationFactory<T extends UrsusConfiguration> {

    private final String configurationFile;
    private final Class<T> clazz;
    private YAMLFactory yamlFactory = new YAMLFactory();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    public UrsusConfigurationFactory(String configurationFile, Class<T> clazz) {
        this.configurationFile = configurationFile;
        this.clazz = clazz;
    }

    public T getConfiguration() {

        ObjectMapper mapper = new ObjectMapper(yamlFactory);
        try {

            T ursusHttpServerConfig =
                    mapper.readValue(open(configurationFile), clazz);

            return validate(ursusHttpServerConfig);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing: " + configurationFile, e);
        }
    }

    private T validate(T ursusHttpServerConfig) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(ursusHttpServerConfig);
        if (!violationSet.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : violationSet) {
                stringBuilder.append(constraintViolation.getPropertyPath());
                stringBuilder.append(" ");
                stringBuilder.append(constraintViolation.getMessage());
                stringBuilder.append(System.lineSeparator());
            }
            throw new RuntimeException(stringBuilder.substring(0, stringBuilder.length() - 1));
        }

        return ursusHttpServerConfig;
    }

    private InputStream open(String configurationFile) throws IOException {
        final File file = new File(configurationFile);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file + " not found");
        }

        return new FileInputStream(file);
    }
}
