package org.jahia.modules.dxstatistics.filters;

import org.jahia.modules.dxstatistics.Statistics;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;

public class PageStatisticsFilter extends AbstractFilter {
    @Override
    public String prepare(RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {
        Statistics.addPageView();
        return super.prepare(renderContext, resource, chain);
    }}
