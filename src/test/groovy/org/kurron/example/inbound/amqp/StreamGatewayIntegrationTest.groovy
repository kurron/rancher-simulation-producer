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

package org.kurron.example.inbound.amqp

import org.junit.experimental.categories.Category
import org.kurron.categories.InboundIntegrationTest
import org.kurron.example.Application
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An integration-level test of the StreamGateway object.
 **/
@Category( InboundIntegrationTest )
@IntegrationTest
@ContextConfiguration( classes = [Application,TestConfiguration], loader = SpringApplicationContextLoader )
class StreamGatewayIntegrationTest extends Specification {

    @Autowired
    RabbitAdmin administrator

    @Autowired
    StreamProducer producer

    def setup() {
        // clear the queue before each test
        assert administrator
        // administrator.purgeQueue( configuration.queueName, false )
    }

    def 'exercise publishing happy path'() {
        given: 'a proper testing environment'
        assert producer

        when: 'message is sent'
        1000.times {
            producer.send()
        }

        then:
        Thread.sleep( 1000 )
    }

}
