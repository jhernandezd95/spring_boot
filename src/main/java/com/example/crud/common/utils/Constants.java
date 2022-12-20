package com.example.crud.common.utils;

public final class Constants {

    public static final String[] WHITE_LIST = {
			"/v1/auth/**",
			"/v1/api-docs",
			"/swagger-ui.html",
			"/swagger-ui/**",
			"/swagger-ui/index.html",
			"/v3/api-docs/**",
	};

    public static final String PIN_FORGOT = "forgot";
    public static final String PIN_ACTIVE_ACCOUNT = "active-account";

    private Constants() {
    }
}
