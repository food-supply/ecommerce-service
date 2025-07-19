package com.se.ecommerce_service.helper;

import java.io.File;

public class ProjectUtils {
    public static String getProjectName() {
        try {
            String projectDir = System.getProperty("user.dir");
            return new File(projectDir).getName();
        } catch (Exception e) {
            return "default-project";
        }
    }
}
