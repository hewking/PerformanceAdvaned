package com.hewking.buildsrc

import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * @author: jianhao
 * @create: 2020/7/28
 * @description:
 */
class AsmPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("==================================")
        println("hello gradle plugin")
        println("==================================")
    }

}
