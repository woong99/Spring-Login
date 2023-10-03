package com.example.formloginwithoutsecurity.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.ui.ModelMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtil {

    public static String createMessage(String message, String retUrl, ModelMap model) {
        model.addAttribute("message", message);
        model.addAttribute("retUrl", retUrl);
        return "message";
    }
}
