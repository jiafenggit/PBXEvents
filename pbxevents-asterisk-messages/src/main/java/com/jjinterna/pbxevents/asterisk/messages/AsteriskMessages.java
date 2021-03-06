package com.jjinterna.pbxevents.asterisk.messages;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.scr.AbstractCamelRunner;
import org.apache.camel.spi.ComponentResolver;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferencePolicyOption;
import org.apache.felix.scr.annotations.References;

import com.jjinterna.pbxevents.asterisk.messages.internal.AsteriskMessagesRoute;
import com.jjinterna.pbxevents.routes.EventMediator;


@Component(description = AsteriskMessages.COMPONENT_DESCRIPTION, immediate = true, metatype = true, policy = ConfigurationPolicy.REQUIRE)
@Properties({
    @Property(name = "camelContextId", value = "pbxevents-asterisk-messages"),
    @Property(name = "camelRouteId", value = "default"),
    @Property(name = "active", value = "true"),
    @Property(name= "fileName")
})
@References({
    @Reference(name = "camelComponent",referenceInterface = ComponentResolver.class,
        cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC,
        policyOption = ReferencePolicyOption.GREEDY, bind = "gotCamelComponent", unbind = "lostCamelComponent")
})
public class AsteriskMessages extends AbstractCamelRunner {

	public static final String COMPONENT_DESCRIPTION = "PBXEvents Asterisk PBX mesages";

    @Reference
    private EventMediator mediator;
    
    @Override
    protected List<RoutesBuilder>getRouteBuilders() {
        List<RoutesBuilder>routesBuilders = new ArrayList<>();
        routesBuilders.add(new AsteriskMessagesRoute());        
        routesBuilders.add(mediator.publisher());      
        return routesBuilders;
    }
    
}