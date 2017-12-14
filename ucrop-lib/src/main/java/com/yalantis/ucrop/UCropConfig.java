package com.yalantis.ucrop;

/**
 * Created by Yefeng on 2017/4/15.
 * Modified by xxx
 */

public class UCropConfig {


   private static Config config =  new Config();

    public static Config getConfig() {
          return config;
    }

    public static class Config {

        private int title_bar_bg_color;
        private String bar_title;
        private String bar_right_text;
        private int title_text_color;
        private int title_text_size;
        private int right_btn_text_size;
        private int title_bar_right_btn_color;
        private int title_bar_right_btn_text_color;
        private int title_bar_back_btn_image;
        private int title_bar_height;


        /** 标题栏背景颜色 */
        public Config setTitleBarBgColor(int color) {
            this.title_bar_bg_color = color;
            return this;
        }
        public int getTitleBarBgColor() {
            return this.title_bar_bg_color;
        }

        /** 标题栏的title */
        public Config setBarTitle(String title) {
            this.bar_title = title;
            return this;
        }

        public String getBarTitle() {
            return this.bar_title;
        }

        /** 标题栏右边的文字 */
        public Config setBarRightText(String text) {
            this.bar_right_text = text;
            return this;
        }

        public String getBarRightText() {
            return this.bar_right_text;
        }

        /** 标题栏 标题 文本颜色 */
        public Config setTitleTextColor(int color) {
            this.title_text_color = color;
            return this;
        }

        public int getTitleTextColor() {
            return this.title_text_color;
        }


        /** 标题栏 高度 */
        public Config setTitleBarHeight(int color) {
            this.title_bar_height = color;
            return this;
        }

        public int getTitleBarHeight() {
            return this.title_bar_height;
        }

        /** 标题栏 标题 文本大小 */
        public Config setTitleTextSize(int size) {
            this.title_text_size = size;
            return this;
        }

        public int getTitleTextSize() {
            return this.title_text_size;
        }


        /** 右边btn的文字 */
        public Config setRightBtnTextSize(int size) {
            this.right_btn_text_size = size;
            return this;
        }

        public int getRightBtnTextSize() {
            return this.right_btn_text_size;
        }

        /** 标题栏 右边按钮 文本颜色 */
        public Config setTitleBarRightBtnBg(int color) {
            this.title_bar_right_btn_color = color;
            return this;
        }

        public int getTitleBarRightBtnColor() {
            return this.title_bar_right_btn_color;
        }

        /** 标题栏 右边按钮 文字颜色 */
        public Config setTitleBarRightBtnTextColor(int color) {
            this.title_bar_right_btn_text_color = color;
            return this;
        }

        public int getTitleBarRightBtnTextColor() {
            return this.title_bar_right_btn_text_color;
        }

        /** 标题栏 返回按钮 */
        public Config setTitleBarBackBtnImage(int res) {
            this.title_bar_back_btn_image = res;
            return this;
        }

        public int getTitleBarBackBtnImage() {
            return this.title_bar_back_btn_image;
        }
    }
}
