package com.thoughtmechanix.organization.services;

import brave.Span;
import brave.Tracer;
import com.thoughtmechanix.organization.events.source.SimpleSourceBean;
import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.sleuth.Span;
//import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    private Tracer tracer;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    public Organization getOrg(String organizationId) {
        Span newSpan = tracer.nextSpan().name("getOrgDBCall");

        logger.debug("In the organizationService.getOrg() call");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())){
            return orgRepository.findById(organizationId).get();
        } finally {
          newSpan.tag("peer.service", "postgres");
//          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          newSpan.finish();
        }
    }

    public void saveOrg(Organization org){
        org.setId( UUID.randomUUID().toString());

        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }

    public void updateOrg(Organization org){
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());

    }

    public void deleteOrg(String orgId){
        orgRepository.deleteById( orgId );
        simpleSourceBean.publishOrgChange("DELETE", orgId);
    }
}
