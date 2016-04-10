/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurron.example

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Custom configuration properties that are driven by Spring Boot and its application.yml file.
 */
@ConfigurationProperties( value = 'producer', ignoreUnknownFields = false )
class ApplicationProperties {

    /**
     * This property controls...
     */
    String foo

    /**
     * Identifies this type of service. Used in logging.
     */
    String serviceCode

    /**
     * Identifies this instance of the service. Used in logging.
     */
    String serviceInstance

    /**
     * Logically groups a collection of services. Used in logging.
     */
    String realm

    /**
     * If true, the REST service will fail 50% of the time.
     */
    boolean fail
}
