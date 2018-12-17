package org.jahia.modules.dxstatistics;

public class Statistics {

    private static long staticFileCalls = 0;
    private static long pageViewCount = 0;
    private static long apiCallCount = 0;


    public static long getStaticFileCalls () {
        return staticFileCalls;
    }

    public static void setStaticFileCalls(long staticFileCalls) {
        Statistics.staticFileCalls = staticFileCalls;
    }

    public static long getPageViewCount() {
        return pageViewCount;
    }

    public static void setPageViewCount(long pageViewCount) {
        Statistics.pageViewCount = pageViewCount;
    }

    public static long getApiCallCount() {
        return apiCallCount;
    }

    public static void setApiCallCount(long apiCallCount) {
        Statistics.apiCallCount = apiCallCount;
    }

    public static void addPageView () {
        pageViewCount++;
    }

    public static void addApiCall () {
        apiCallCount++;
    }

    public static void addFileCall () {
        staticFileCalls++;
    }
}
