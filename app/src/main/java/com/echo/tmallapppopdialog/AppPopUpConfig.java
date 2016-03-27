package com.echo.tmallapppopdialog;

import java.util.List;

/**
 * Created by jiangecho on 16/3/27.
 */
public class AppPopUpConfig {

    /**
     * uri : com.echo.tmallapppopup.MainActivity
     * url : http://www.tmall.com/
     * modalThreshold : 0.5
     * appear : true
     * startTime : 1459073996
     * endTime : 1459160396
     * times : 0
     */

    private List<ActivityPopupConfig> configs;

    public List<ActivityPopupConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ActivityPopupConfig> configs) {
        this.configs = configs;
    }

    public static class ActivityPopupConfig {
        private String uri;
        private String url;
        private double modalThreshold;
        private boolean appear;
        private long startTime; // unix timestamp, not java timestamp
        private long endTime;
        private int times;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public double getModalThreshold() {
            return modalThreshold;
        }

        public void setModalThreshold(double modalThreshold) {
            this.modalThreshold = modalThreshold;
        }

        /**
         * true, just popup; false, check start time and end time
         * @return
         */
        public boolean isAppear() {
            return appear;
        }

        public void setAppear(boolean appear) {
            this.appear = appear;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }
}
