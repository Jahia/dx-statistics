package org.jahia.modules.dxstatistics.filters;

import org.jahia.modules.dxstatistics.Statistics;
import org.jahia.modules.dxstatistics.services.DxStatisticsService;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;

import java.sql.Timestamp;
import java.util.*;

public class StatisticSettingFilter extends AbstractFilter {

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    @Override
    public String prepare(RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {


        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date d1 = c.getTime();

        Timestamp dayStart = new Timestamp(d1.getTime());
        c.add(Calendar.HOUR, 24);
        Date d2 = c.getTime();
        Timestamp dayEnd = new Timestamp(d2.getTime());

        renderContext.getRequest().setAttribute("pageViewCount", format(DxStatisticsService.getAggregatedMetric("pageViews", "5mn", dayStart, dayEnd) + Statistics.getPageViewCount()));
        renderContext.getRequest().setAttribute("apiCallCount", format(DxStatisticsService.getAggregatedMetric("apiCalls", "5mn", dayStart, dayEnd) + Statistics.getApiCallCount()));
        renderContext.getRequest().setAttribute("staticFileCount", format(DxStatisticsService.getAggregatedMetric("fileCalls", "5mn", dayStart, dayEnd) + Statistics.getStaticFileCalls()));

        renderContext.getRequest().setAttribute("pagePerMinute", ((double)DxStatisticsService.getLastMetric("pageViews", "5mn")) / 5);
        renderContext.getRequest().setAttribute("apiPerMinute", ((double)DxStatisticsService.getLastMetric("apiCalls", "5mn")) / 5);
        renderContext.getRequest().setAttribute("filePerMinute", ((double)DxStatisticsService.getLastMetric("fileCalls", "5mn")) / 5);


        return super.prepare(renderContext, resource, chain);
    }
}
