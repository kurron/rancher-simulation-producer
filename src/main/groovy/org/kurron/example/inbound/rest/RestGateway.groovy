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

package org.kurron.example.inbound.rest

import java.time.Instant
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import org.kurron.example.ApplicationProperties
import org.kurron.example.MessagingContext
import org.kurron.example.core.EchoComponent
import org.kurron.feedback.AbstractFeedbackAware
import org.kurron.stereotype.InboundRestGateway
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.HandlerMapping

/**
 * Inbound HTTP gateway that supports the echo resource.
 **/
@InboundRestGateway
@RequestMapping( path = '/echo',
                 consumes = [HypermediaControl.MIME_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE],
                 produces = [HypermediaControl.MIME_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE] )
@ExposesResourceFor( HypermediaControl )
class RestGateway extends AbstractFeedbackAware implements GenerationAbility {

    /**
     * Knows how to process the message.
     */
    private final EchoComponent theComponent

    private final ApplicationProperties theConfiguration

    @Autowired
    RestGateway( final EchoComponent aComponent,
                 final ApplicationProperties aConfiguration ) {
        theComponent = aComponent
        theConfiguration = aConfiguration
    }

    @RequestMapping( method = [RequestMethod.PUT] )
    ResponseEntity<HypermediaControl> echoMessage( @RequestBody @Valid HypermediaControl upload,
                                                   HttpServletRequest request,
                                                   Errors errors ) {
        final ResponseEntity<HypermediaControl> response
        def control = defaultControl( request )
        control.add( ControllerLinkBuilder.linkTo( RestGateway ).withSelfRel() )

        if ( theConfiguration.fail && randomBoolean() ) {
            throw new ArrayIndexOutOfBoundsException( 'Oops!' )
        }

        if ( errors.hasErrors() ) {
            addErrors( errors, control )
            response = ResponseEntity.badRequest().body( control )
        }
        else {
            control.message = theComponent.processMessage( upload.message )
            response = ResponseEntity.ok( control )
        }
        response
    }

    private static HypermediaControl defaultControl( HttpServletRequest request ) {
        def path = request.getAttribute( HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE ) as String
        new HypermediaControl( status: HttpStatus.OK.value(), timestamp: Instant.now().toString(), path: path )
    }

    private static HypermediaControl addErrors( Errors errors, HypermediaControl toAugment ) {
        def validationErrors = errors.fieldErrors.collect { new ValidationError( field: it.field, reason: it.defaultMessage ) }
        toAugment.errorBlock = new ErrorBlock( code: MessagingContext.VALIDATION_ERROR.code,
                                               message: 'The uploaded descriptor is invalid.  Please correct the issues and try again.',
                                               developerMessage: 'Certain properties in the payload are invalid.',
                                               validationErrors: validationErrors )
        toAugment
    }
}
