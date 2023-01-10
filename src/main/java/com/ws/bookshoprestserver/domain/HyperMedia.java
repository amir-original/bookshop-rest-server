package com.ws.bookshoprestserver.domain;

import java.util.LinkedList;
import java.util.List;

public class HyperMedia {

    List<LinkResource> links = new LinkedList<>();

    public void addLink(LinkResource linkResource){
        links.add(linkResource);
    }

    public List<LinkResource> getLinks() {
        return links;
    }

    public void setLinks(List<LinkResource> links) {
        this.links = links;
    }
}
