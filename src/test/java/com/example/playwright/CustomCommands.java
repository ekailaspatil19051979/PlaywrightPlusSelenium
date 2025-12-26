package com.example.playwright;

import com.microsoft.playwright.Page;

public class CustomCommands {
    public static void logout(Page page) {
        page.click("#logoutBtn");
    }

    public static void search(Page page, String query) {
        page.fill("#searchBox", query);
        page.click("#searchBtn");
    }
}
