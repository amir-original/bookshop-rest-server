package com.ws.bookshoprestserver.domain;

import jakarta.ws.rs.core.Link;

import java.net.URI;

public class LinkResource {
    private String rel;
    private String type;
    private URI uri;

    public LinkResource(Link link) {
        this.rel = link.getRel();
        this.type = link.getType();
        this.uri = link.getUri();
    }

    public LinkResource() {
    }


    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
