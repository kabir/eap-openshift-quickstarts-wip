/*
 * JBoss, Home of Professional Open Source.
 *  Copyright 2022 Red Hat, Inc., and individual contributors
 *  as indicated by the @author tags.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.jboss.as.quickstarts.remote.mdb;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Queue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author <a href="mailto:kabir.khan@jboss.com">Kabir Khan</a>
 */
@Path("/")
/*
 * Definition of the JMS queue used by the quickstart.
 */
@JMSDestinationDefinition(
        name = "java:/queue/testqueue",
        interfaceName = "javax.jms.Queue",
        destinationName = "testqueue",
        properties = {"enable-amq1-prefix=false"
        }
)
public class RemoteBrokerEndpoint {
    @Inject
    RemoteBrokerBean bean;

    @Resource(lookup = "java:/queue/testqueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    @POST
    @Path("{value}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response store(@PathParam("value") String value) {
        context.createProducer().send(queue, value);
        return Response.ok().build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<String> getAllValues() {
        return bean.getAllValues();
    }
}
