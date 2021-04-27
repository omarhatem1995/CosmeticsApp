package com.example.blaumtask.ui;

public class SliderItem {
        private int image;
        private String title;
        private String message;

        public SliderItem(int image, String title, String message) {
            this.image = image;
            this.title = title;
            this.message = message;
        }

        public int getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }
}
